package tresp122;

import robocode.Rules;
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
		
		if (mRobot.getGunHeat() == 0) {
			
			mRobot.turnRight(getID(), event.getBearing());
			
			mRobot.fire(getID(), Rules.MAX_BULLET_POWER);
		}
	}

}
