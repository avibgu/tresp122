package tresp122;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import robocode.Event;

public abstract class BThread implements Runnable {

	protected Lock						mLock;
	protected AviBatelRobot				mRobot;
	protected Coordinator				mCoordinator;
	protected boolean					mDontStop;
	
	public BThread(AviBatelRobot pRobot) {

		mLock = new ReentrantLock(true);
		mRobot = pRobot;
		mCoordinator = pRobot;
		mDontStop = true;
	}
	
	@Override
	public void run() {
		while(mDontStop){
			decideWhatActionToPerform();
		}
	}
	
	public void stop() {
		mDontStop = false;
	}
	
	public abstract void decideWhatActionToPerform();

	public abstract void notifyAboutEvent(Event pEvent);
}
