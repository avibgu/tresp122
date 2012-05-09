package tresp122.bthreads;

import robocode.Event;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class AvoidCollisionsBThread extends BThread {

	protected HitWallEvent mHitWall;
	protected HitRobotEvent mHitRobot;

	public AvoidCollisionsBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitWall= null;
		mHitRobot = null;
		
		mPriority = 50;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mHitWall) {

				double degree = mHitWall.getBearing() - 100;
				
				mHitWall = null;
				
				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_RIGHT, mPriority + 1, degree));
				mCoordinator.addAction(new BThreadAction(BThreadActionType.AHEAD, mPriority, 150));
			}
			
			else if (null != mHitRobot) {
				
				double degree = mRobot.getHeading() - mRobot.getGunHeading() + mHitRobot.getBearing();
				
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
