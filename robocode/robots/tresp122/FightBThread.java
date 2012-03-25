package tresp122;

import robocode.HitWallEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class FightBThread extends BThread {
	
	public FightBThread(AviBatelRobot pRobot) {
		super(pRobot);
	}

	public void decideWhatToDo() {

		 if (mLock.tryLock()) {

			 try {

				 if (!mScannedRobots.isEmpty()){
	      			
					 ScannedRobotEvent event = mScannedRobots.poll();
	  					
					 if (mRobot.getGunHeat() == 0) {
	  					
						 double degree = mRobot.getHeading() - mRobot.getGunHeading() + event.getBearing();
	  					
						 mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_GUN_RIGHT, 11, degree));
						 mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.FIRE, 10, Rules.MAX_BULLET_POWER));
					 }
				 }
			 }
			 finally {
				 
				 mLock.unlock();
			 }
		 }
	}

	@Override
	public BThreadID getID() {
		return BThreadID.FIGHT;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {

		mLock.lock();
		
		mScannedRobots.add(event);
		
		mLock.unlock();

	}

	
	// ignored events:
	
	
	@Override
	public void onHitWall(HitWallEvent event) {}
}
