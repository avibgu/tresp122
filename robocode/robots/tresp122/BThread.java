package tresp122;

import robocode.ScannedRobotEvent;

public abstract class BThread implements Runnable {

	protected AviBatelRobot mRobot;
	protected boolean mDontStop;
	
	public BThread(AviBatelRobot pRobot) {

		mRobot = pRobot;
		mDontStop = true;
	}
	
	@Override
	public void run() {
		
		while(mDontStop){
			
			decideWhatToDo();
		}
	}
	
	public void stop() {
		mDontStop = false;
	}
	
	public abstract void decideWhatToDo();

	public abstract BThreadID getID();

	public abstract void onScannedRobot(ScannedRobotEvent event);
}
