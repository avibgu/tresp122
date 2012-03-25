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
		
		while(mDontStop) {
			
			mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.AHEAD, 0, 100));
			mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_RIGHT, 0, 90));
			
			waitSomeTime();
		}
	}

	protected void waitSomeTime() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
