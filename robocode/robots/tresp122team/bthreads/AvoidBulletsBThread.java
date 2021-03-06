package tresp122team.bthreads;

import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import tresp122team.action.BThreadAction;
import tresp122team.action.BThreadActionType;
import tresp122team.coordinator.AviBatelRobot;

/**
 * The strategy of this BThread has been taken mainly from:
 * http://www.ibm.com/developerworks/java/library/j-dodge/
 */
public class AvoidBulletsBThread extends BThread {

	protected HitByBulletEvent mHitByBullet;
	protected ScannedRobotEvent mScannedRobots;

	protected double mPreviousEnemyEnergy;
	protected int mMovementDirection;

	public AvoidBulletsBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitByBullet = null;
		mScannedRobots = null;

		mPriority = 20;

		mPreviousEnemyEnergy = 100;
		mMovementDirection = 1;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mHitByBullet) {

				HitByBulletEvent event = mHitByBullet;

				mHitByBullet = null;

				mLock.unlock();

				if (Math.random() > 0.5)

					mCoordinator.addAction(new BThreadAction(
							BThreadActionType.TURN_RIGHT, 20, event
									.getBearing() + 45));

				else
					mCoordinator.addAction(new BThreadAction(
							BThreadActionType.TURN_RIGHT, 20, event
									.getBearing() - 45));

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.BACK, 19, 200));
			}

			else if (null != mScannedRobots) {

				ScannedRobotEvent event = mScannedRobots;

				mScannedRobots = null;

				mLock.unlock();

				// Stay at right angles to the opponent

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.TURN_RIGHT, mPriority + 1, event
								.getBearing() + 90 - 30 * mMovementDirection));

				// If the bot has small energy drop, assume it fired
				double changeInEnergy = mPreviousEnemyEnergy
						- event.getEnergy();

				if (changeInEnergy > 0 && changeInEnergy <= 3) {

					// Dodge!
					// mMovementDirection = -mMovementDirection;

					double ahead = (event.getDistance() / 4 + 25)
							* mMovementDirection;

					mCoordinator.addAction(new BThreadAction(
							BThreadActionType.AHEAD, mPriority, ahead));
				}

				// Track the energy level
				mPreviousEnemyEnergy = event.getEnergy();
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
