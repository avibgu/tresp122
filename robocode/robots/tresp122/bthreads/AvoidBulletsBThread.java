package tresp122.bthreads;

import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

/**
 * The strategy of this BThread has been taken mainly from:
 * http://www.ibm.com/developerworks/java/library/j-dodge/
 */
public class AvoidBulletsBThread extends BThread {

	protected HitByBulletEvent mHitByBullet;
	protected ScannedRobotEvent mScannedRobots;

	protected int mPriority;

	protected double previousEnergy;
	protected int movementDirection;
	protected int gunDirection;

	public AvoidBulletsBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitByBullet = null;
		mScannedRobots = null;

		mPriority = 20;

		previousEnergy = 100;
		movementDirection = 1;
		gunDirection = 1;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

//			if (null != mHitByBullet) {
//
//				HitByBulletEvent event = mHitByBullet;
//
//				mHitByBullet = null;
//
//				mLock.unlock();
//
//				if (Math.random() > 0.5)
//
//					mCoordinator.addAction(new BThreadAction(
//							BThreadActionType.TURN_RIGHT, 20, event
//									.getBearing() + 45));
//
//				else
//					mCoordinator.addAction(new BThreadAction(
//							BThreadActionType.TURN_RIGHT, 20, event
//									.getBearing() - 45));
//
//				mCoordinator.addAction(new BThreadAction(
//						BThreadActionType.BACK, 19, 200));
//			}
//
//			else
				if (null != mScannedRobots) {

				ScannedRobotEvent event = mScannedRobots;

				mScannedRobots = null;

				mLock.unlock();

				// Stay at right angles to the opponent

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.TURN_RIGHT, mPriority, event
								.getBearing() + 90 - 30 * movementDirection));

				// If the bot has small energy drop, assume it fired
				double changeInEnergy = previousEnergy - event.getEnergy();

				if (changeInEnergy > 0 && changeInEnergy <= 3) {

					// Dodge!
					movementDirection = -movementDirection;

					double ahead = (event.getDistance() / 4 + 25)
							* movementDirection;

					mCoordinator.addAction(new BThreadAction(
							BThreadActionType.AHEAD, mPriority, ahead));
				}

				// Track the energy level
				previousEnergy = event.getEnergy();
			}

			else
				mLock.unlock();
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof HitByBulletEvent) {

			mLock.lock();

			mHitByBullet = (HitByBulletEvent) pEvent;

			mLock.unlock();
		}

		else if (pEvent instanceof ScannedRobotEvent) {

			mLock.lock();

			mScannedRobots = (ScannedRobotEvent) pEvent;

			mLock.unlock();
		}
	}

}
