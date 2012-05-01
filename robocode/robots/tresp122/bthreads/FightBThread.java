package tresp122.bthreads;

import java.util.Stack;

import robocode.Event;
import robocode.Rules;
import robocode.ScannedRobotEvent;

import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class FightBThread extends BThread {
	
//	protected Queue<ScannedRobotEvent> mScannedRobots;
	
	protected Stack<ScannedRobotEvent>	mScannedRobots;
	protected double					mBulletPower;
	protected int						mFirePriority;

	public FightBThread(AviBatelRobot pRobot) {
		
		super(pRobot);
		
		mScannedRobots = new Stack<ScannedRobotEvent>();
		mBulletPower = Rules.MAX_BULLET_POWER;
		mFirePriority = 10;
	}

	public void decideWhatActionToPerform() {

		 if (mLock.tryLock()) {
			 
			 if (!mScannedRobots.isEmpty()){
      			
				 ScannedRobotEvent event = mScannedRobots.pop();

				 mLock.unlock();
				 
				 if (mRobot.getGunHeat() == 0) {
  					
					 double degree = mRobot.getHeading() - mRobot.getGunHeading() + event.getBearing();
  					
					 mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_GUN_RIGHT, mFirePriority + 1, degree));
					 mCoordinator.addAction(new BThreadAction(BThreadActionType.FIRE, mFirePriority, mBulletPower));
				 }
			 }
			 else
				 mLock.unlock();
		 }
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
		
		if (pEvent instanceof ScannedRobotEvent){

			mLock.lock();
			
			mScannedRobots.push((ScannedRobotEvent) pEvent);
			
			mLock.unlock();
		}
	}
}
