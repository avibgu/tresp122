package tresp122.coordinator;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import tresp122.action.ActionsComparator;
import tresp122.action.BThreadAction;
import tresp122.bthreads.BThread;
import tresp122.bthreads.KillBThread;
import tresp122.bthreads.StayAliveBThread;

public class Level1Coordinator implements Coordinator {

	protected Lock							mLock;
	protected AviBatelRobot					mRobot;
	protected boolean						mDontStop;
	protected PriorityQueue<BThreadAction>	mActions;
	
	protected Set<BThread>					mAllBThreads;
	
	protected KillBThread					mKillBThread;
	protected StayAliveBThread				mStayAliveBThread;
	
	public Level1Coordinator(AviBatelRobot pRobot, Set<BThread> pAllBThreads) {

		mDontStop = true;
		mRobot = pRobot;
		mLock = new ReentrantLock(true);
		mActions = new PriorityQueue<BThreadAction>(20, new ActionsComparator());

		mAllBThreads = pAllBThreads;
		
		initializeTheBThreadsOfThisLevel();
	}

	@Override
	public void initializeTheBThreadsOfThisLevel() {

		mKillBThread = new KillBThread(mRobot, this, mAllBThreads);
		mStayAliveBThread = new StayAliveBThread(mRobot, this, mAllBThreads);

		mAllBThreads.add(mKillBThread);
		mAllBThreads.add(mStayAliveBThread);
	}

	@Override
	public void run() {

		startBThreads();
		
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
	public void startBThreads() {

		new Thread(mKillBThread).start();
		new Thread(mStayAliveBThread).start();
	}

	@Override
	public void decideWhatToDo() {

		mLock.lock();
		
		if(!mActions.isEmpty()){

			BThreadAction action = mActions.poll();
			
			mLock.unlock();
			
			switch (action.getType()) {
				
				case DECREASE_FIGHT_PRIORITY:
//					action.getValue();
					break;
					
				case INCREASE_FIGHT_PRIORITY:
					// TODO:..
					break;
					
				case DECREASE_AVOID_BULLETS_PRIORITY:
					// TODO:..
					break;
					
				case INCREASE_AVOID_BULLETS_PRIORITY:
					// TODO:..
					break;
					
				case DECREASE_AVOID_COLLISIONS_PRIORITY:
					// TODO:..
					break;
					
				case INCREASE_AVOID_COLLISIONS_PRIORITY:
					// TODO:..
					break;
					
				case INCREASE_KEEP_ENERGY_PRIORITY:
					// TODO:..
					break;
					
				case DECREASE_KEEP_ENERGY_PRIORITY:
					// TODO:..
					break;
					
				case INCREASE_TRACK_PRIORITY:
					// TODO:..
					break;
					
				case DECREASE_TRACK_PRIORITY:
					// TODO:..
					break;
			}
		}
		else {
			mLock.unlock();
		}
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
