package tresp122.coordinator;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import tresp122.action.ActionsComparator;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.bthreads.BThreadsController;

public abstract class LeveliCoordinator implements Coordinator {

	protected Lock							mLock;
	protected AviBatelRobot					mRobot;
	protected boolean						mDontStop;
	protected PriorityQueue<BThreadAction>	mActions;
	
	protected BThreadsController			mBThreadsController;
	
	public LeveliCoordinator(AviBatelRobot pRobot,
			BThreadsController pBThreadsController) {

		mDontStop = true;
		mRobot = pRobot;
		mLock = new ReentrantLock(true);
		mActions = new PriorityQueue<BThreadAction>(20, new ActionsComparator());

		mBThreadsController = pBThreadsController;
		
		setMyselfAsTheCoordinatorOfBThreadsInMyLevel();
	}

	public abstract void setMyselfAsTheCoordinatorOfBThreadsInMyLevel();

	@Override
	public void run() {
		
		while (mDontStop) {

			decideWhatToDo();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		mDontStop = false;
	}

	@Override
	public void decideWhatToDo() {

		mLock.lock();

		if (!mActions.isEmpty()) {

			BThreadAction action = mActions.poll();

			removeSimilarActions(action.getType());
			
			mLock.unlock();

			performAction(action);
		}

		else
			mLock.unlock();
	}
	
	public abstract void performAction(BThreadAction pAction);

	public void removeSimilarActions(BThreadActionType pType) {

		Set<BThreadAction> toRemove = new HashSet<BThreadAction>();
		
		for (BThreadAction action : mActions)
			if (action.getType().equals(pType))
				toRemove.add(action);
		
		mActions.removeAll(toRemove);
	}

	@Override
	public void addAction(BThreadAction pAction) {

		try {
			
			mLock.lock();
			mActions.add(pAction);
			mLock.unlock();
		}
		catch (Exception e) {}
	}
}
