package tresp122;

import robocode.ScannedRobotEvent;

public class FightBThread implements BThread {

	protected AviBatelRobot mRobot;
	protected boolean mDontStop;
	
	public FightBThread(AviBatelRobot pRobot) {

		mRobot = pRobot;
		mDontStop = true;
	}

	@Override
	public void run() {
		
		while(mDontStop){
		
//			mRobot.doNothing();
		}
	}

	@Override
	public BThreadID getID() {
		return BThreadID.FIGHT;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		mRobot.fire(getID(),1);
	}

}
