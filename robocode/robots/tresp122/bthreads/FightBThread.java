package tresp122.bthreads;

import robocode.Event;
import robocode.Rules;
import robocode.ScannedRobotEvent;

import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class FightBThread extends BThread {

	// protected Queue<ScannedRobotEvent> mScannedRobots;

	protected ScannedRobotEvent mScannedRobots;
	protected int mFirePriority;

	public FightBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mScannedRobots = null;
		mFirePriority = 10;
	}

	public void decideWhatActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mScannedRobots) {

				ScannedRobotEvent event = mScannedRobots;

				mScannedRobots = null;

				mLock.unlock();

				double degree = mRobot.getHeading() - mRobot.getGunHeading()
						+ event.getBearing();

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.TURN_GUN_RIGHT, mFirePriority + 1,
						degree));
				
				double bulletPower = Math.min(400 / event.getDistance(), 3);

				if (mRobot.getGunHeat() == 0
						&& Math.abs(mRobot.getGunTurnRemaining()) < 10) {

					mCoordinator
							.addAction(new BThreadAction(
									BThreadActionType.FIRE, mFirePriority,
									bulletPower));
				}
			}

			else
				mLock.unlock();
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof ScannedRobotEvent) {

			mLock.lock();

			mScannedRobots = (ScannedRobotEvent) pEvent;

			mLock.unlock();
		}
	}
}
