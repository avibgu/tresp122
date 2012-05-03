package tresp122.bthreads;

import java.awt.geom.Point2D;

import robocode.Event;
import robocode.ScannedRobotEvent;

import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;
import tresp122.utilities.AdvancedEnemyBot;


/**
 * The strategy of this BThread has been taken from:
 * http://mark.random-article.com/weber/java/robocode/lesson4.html
 */
public class FightBThread extends BThread {

	protected ScannedRobotEvent mScannedRobots;
	protected AdvancedEnemyBot mEnemy;
	protected double mFirePower;
	protected double mDegree;

	public FightBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mScannedRobots = null;
		mPriority = 10;

		mEnemy = new AdvancedEnemyBot();
		mFirePower = 0.0;
		mDegree = 0.0;
	}

	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mScannedRobots) {

				ScannedRobotEvent event = mScannedRobots;

				mScannedRobots = null;

				mLock.unlock();

				calcDegreeAndFirePower(event);

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.TURN_GUN_RIGHT, mPriority + 1,
						mDegree));

				if (mRobot.getGunHeat() == 0
						&& Math.abs(mRobot.getGunTurnRemaining()) < 10) {

					mCoordinator.addAction(new BThreadAction(
							BThreadActionType.FIRE, mPriority, mFirePower));
				}
			}

			else
				mLock.unlock();
		}
	}

	protected void calcDegreeAndFirePower(ScannedRobotEvent event) {

		mFirePower = Math.min(500 / event.getDistance(), 3);

		double bulletSpeed = 20 - mFirePower * 3;

		long time = (long) (mEnemy.getDistance() / bulletSpeed);

		if (mEnemy.none() || event.getDistance() < mEnemy.getDistance() - 70
				|| event.getName().equals(mEnemy.getName())) {

			mEnemy.update(event, mRobot);
		}

		double futureX = mEnemy.getFutureX(time);
		double futureY = mEnemy.getFutureY(time);
		double absDeg = absoluteBearing(mRobot.getX(), mRobot.getY(), futureX,
				futureY);

		mDegree = normalizeBearing(absDeg - mRobot.getGunHeading());
	}

	double normalizeBearing(double angle) {

		while (angle > 180)
			angle -= 360;

		while (angle < -180)
			angle += 360;

		return angle;
	}

	double absoluteBearing(double x1, double y1, double x2, double y2) {

		double xo = x2 - x1;
		double yo = y2 - y1;
		double hyp = Point2D.distance(x1, y1, x2, y2);
		double arcSin = Math.toDegrees(Math.asin(xo / hyp));
		double bearing = 0;

		if (xo > 0 && yo > 0)
			bearing = arcSin;

		else if (xo < 0 && yo > 0)
			bearing = 360 + arcSin;

		else if (xo > 0 && yo < 0)
			bearing = 180 - arcSin;

		else if (xo < 0 && yo < 0)
			bearing = 180 - arcSin;

		return bearing;
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
