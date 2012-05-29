package tresp122.bthreads;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import robocode.Event;

import tresp122.coordinator.AviBatelRobot;
import tresp122.coordinator.Coordinator;
import tresp122.utilities.NotifierThread;

public abstract class BThread implements Runnable {

	protected Lock			mLock;
	protected AviBatelRobot	mRobot;
	protected Coordinator	mCoordinator;
	protected boolean		mDontStop;
	protected int			mPriority;
	protected Set<BThread>	mMailingList;
	
	public BThread(AviBatelRobot pRobot) {
		this(pRobot, pRobot, new HashSet<BThread>());
	}
	
	public BThread(AviBatelRobot pRobot, Set<BThread> pBThreadsToRegister) {
		this(pRobot, null, new HashSet<BThread>());
	}

	public BThread(AviBatelRobot pRobot, Coordinator pCoordinator,
			Set<BThread> pBThreadsToRegister) {

		mLock = new ReentrantLock(true);
		mRobot = pRobot;
		mCoordinator = pCoordinator;
		mDontStop = true;
		mPriority = 0;
		mMailingList = new HashSet<BThread>();
		
		for (BThread bThread : pBThreadsToRegister)
			bThread.addToMailingList(this);
	}

	@Override
	public void run() {
		
		while(mDontStop){
			
			decideWhichActionToPerform();
			
			try {
				Thread.sleep(100);
			}
			
			catch (InterruptedException e) {}
		}
	}
	
	public void stop() {
		mDontStop = false;
	}
	
	public abstract void decideWhichActionToPerform();

	public abstract void notifyAboutEvent(Event pEvent);
	
	public void addToMailingList(BThread pBThread) {
		
		mLock.lock();
		mMailingList.add(pBThread);
		mLock.unlock();
	}
	
	public void notifyToMailingList(Event pEvent) {
		new Thread(new NotifierThread(mMailingList, pEvent)).start();
	}
	
	public void increasePriority(int pAmount) {
		mPriority += pAmount;
	}

	public void decreasePriority(int pAmount) {
		mPriority -= pAmount;
	}
	
	public void setCoordinator( Coordinator pCoordinator){
		mCoordinator = pCoordinator;
	}
}
