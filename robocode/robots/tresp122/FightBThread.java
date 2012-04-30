package tresp122;

import java.util.LinkedList;
import java.util.Queue;

import robocode.Event;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class FightBThread extends BThread {
	
	protected Queue<ScannedRobotEvent>	mScannedRobots;

	public FightBThread(AviBatelRobot pRobot) {
		
		super(pRobot);
		
		mScannedRobots = new LinkedList<ScannedRobotEvent>();
	}

	public void decideWhatActionToPerform() {

		 if (mLock.tryLock()) {
			 
			 if (!mScannedRobots.isEmpty()){
      			
				 ScannedRobotEvent event = mScannedRobots.poll();

				 mLock.unlock();
				 
				 if (mRobot.getGunHeat() == 0) {
  					
					 double degree = mRobot.getHeading() - mRobot.getGunHeading() + event.getBearing();
  					
					 mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_GUN_RIGHT, 11, degree));
					 mCoordinator.addAction(new BThreadAction(BThreadActionType.FIRE, 10, Rules.MAX_BULLET_POWER));
				 }
			 }
		 }
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
		
		if (pEvent instanceof ScannedRobotEvent){

			mLock.lock();
			
			mScannedRobots.add((ScannedRobotEvent) pEvent);
			
			mLock.unlock();

		}
	}
}
