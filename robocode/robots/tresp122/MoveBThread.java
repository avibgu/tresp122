package tresp122;

import robocode.ScannedRobotEvent;

public class MoveBThread implements BThread {

	protected AviBatelRobot mRobot;
	protected boolean mDontStop;
	
	public MoveBThread(AviBatelRobot pRobot) {

		mRobot = pRobot;
		mDontStop = true;
	}
	
	@Override
	public void run() {
		
//		while(mDontStop) {
			mRobot.ahead(getID(), 100);
			mRobot.turnRight(getID(), 90);
//		}
	}

	public void stop() {
		mDontStop = false;
	}

	@Override
	public BThreadID getID() {
		return BThreadID.MOVE;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		// TODO Auto-generated method stub
	}
}
