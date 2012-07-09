package tresp122team.utilities;

import robocode.ScannedRobotEvent;
import tresp122team.bthreads.FightBThread;

public class StoppableNotifierThread implements Runnable{

	private FightBThread		mThread;
	private ScannedRobotEvent	mEvent;
	private boolean 			mToStop;

	public StoppableNotifierThread(FightBThread pThread, ScannedRobotEvent pEvent) {

		mThread = pThread;
		mEvent = pEvent;
		mToStop = false;
	}
	
	@Override
	public void run() {
		
		mThread.addToBThreadsWhichNeedToBeKilled(this);

		while(!mToStop && !mThread.notifyAboutScannedRobot(mEvent))
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		mThread.removeFromBThreadsWhichNeedToBeKilled(this);
	}
	
	public void stop() {
		mToStop = true;
	}
}
