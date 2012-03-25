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
			
		for (int i = 0; i < 17; i ++){
		
			mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.AHEAD, 0, 100));
			mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_RIGHT, 0, 90));
		}
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
