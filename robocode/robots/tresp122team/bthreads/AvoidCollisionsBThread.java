package tresp122team.bthreads;

import robocode.Event;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import tresp122team.action.BThreadAction;
import tresp122team.action.BThreadActionType;
import tresp122team.coordinator.AviBatelRobot;

public class AvoidCollisionsBThread extends BThread {

	protected HitWallEvent mHitWall;
	protected HitRobotEvent mHitRobot;

	public AvoidCollisionsBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitWall= null;
		mHitRobot = null;
		
		mPriority = 30;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mHitWall) {

//				double degree = mHitWall.getBearing() - 100;
				double degree = mHitWall.getBearing() - 180;
				
				mHitWall = null;
				
				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_RIGHT, mPriority + 1, degree));
				mCoordinator.addAction(new BThreadAction(BThreadActionType.AHEAD, mPriority, 150));
			}
			
			else if (null != mHitRobot) {
				
//				double degree = mRobot.getHeading() - mRobot.getGunHeading() + mHitRobot.getBearing();
				double degree = mHitRobot.getBearing() - 180;
				 
				mHitRobot = null;
				
				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_RIGHT, mPriority + 1, degree));
				mCoordinator.addAction(new BThreadAction(BThreadActionType.BACK, mPriority, 250));
			}
			
			else
				mLock.unlock();
		}		
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof HitWallEvent) {

			mLock.lock();

			mHitWall = (HitWallEvent) pEvent;

			mLock.unlock();
		}
		
		else if (pEvent instanceof HitRobotEvent) {

			mLock.lock();

			mHitRobot = (HitRobotEvent) pEvent;

			mLock.unlock();
		}
	}
}
