package tresp122.bthreads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import robocode.Event;
import tresp122.coordinator.AviBatelRobot;
import tresp122.coordinator.Coordinator;

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
			
			decideWhichActionToPerform();
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		mDontStop = false;
	}
	
	public abstract void decideWhichActionToPerform();

	public abstract void notifyAboutEvent(Event pEvent);
}
