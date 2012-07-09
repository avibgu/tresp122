package tresp122team.bthreads;

import java.awt.geom.Point2D;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import robocode.Event;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

import tresp122team.action.BThreadAction;
import tresp122team.action.BThreadActionType;
import tresp122team.coordinator.AviBatelRobot;
import tresp122team.utilities.AdvancedEnemyBot;
import tresp122team.utilities.StoppableNotifierThread;

/**
 * The strategy of this BThread has been taken from:
 * http://mark.random-article.com/weber/java/robocode/lesson4.html
 */
public class FightBThread extends BThread {

	protected Stack<ScannedRobotEvent> mScannedRobots;
	protected AdvancedEnemyBot mEnemy;
	protected double mFirePower;
	protected double mDegree;

	protected String mEnemyName;
	protected Set<String> deadRobotsSet;

	private Set<StoppableNotifierThread> mBThreadsWhichNeedToBeKilled;

	public FightBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mScannedRobots = new Stack<ScannedRobotEvent>();
		mPriority = 10;

		mEnemy = new AdvancedEnemyBot();
		mFirePower = 3.0;
		mDegree = 0.0;

		mEnemyName = "";
		deadRobotsSet = new HashSet<String>();

		mBThreadsWhichNeedToBeKilled = new HashSet<StoppableNotifierThread>();
	}

	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (!mScannedRobots.isEmpty()) {

				ScannedRobotEvent event = mScannedRobots.pop();

				mScannedRobots.clear();

				mLock.unlock();

				if (deadRobotsSet.contains(event.getName()))
					return;

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

		// mFirePower = Math.min(500 / event.getDistance(), 3);

		double bulletSpeed = 20 - mFirePower * 3;

		if (mEnemy.none() || event.getDistance() < mEnemy.getDistance() - 70
				|| event.getName().equals(mEnemy.getName())) {

			mEnemy.update(event, mRobot);
		}

		long time = (long) (mEnemy.getDistance() / bulletSpeed);

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
		
		if (pEvent instanceof RobotDeathEvent){
			
			setTarget("");
			addDeadRobot(((RobotDeathEvent) pEvent).getName());
		}
	}

	public void increaseFirePower() {
		mFirePower = 3;
	}

	public void decreaseFirePower() {
		mFirePower = 2;
	}

	public void setTarget(String pMessage) {

		mLock.lock();

		mEnemyName = pMessage;

		mLock.unlock();
	}

	public void addDeadRobot(String pMessage) {

		mLock.lock();

		deadRobotsSet.add(pMessage);

		mEnemy.reset();
		
		mLock.unlock();

		synchronized (mBThreadsWhichNeedToBeKilled) {
			for (StoppableNotifierThread pStoppableNotifierThread : mBThreadsWhichNeedToBeKilled)
				pStoppableNotifierThread.stop();
		}
	}

	public boolean notifyAboutScannedRobot(ScannedRobotEvent pEvent) {

		if (mLock.tryLock()) {

			String enemyName = ((ScannedRobotEvent) pEvent).getName();

			if (mEnemyName.equals("")) {

				mScannedRobots.push((ScannedRobotEvent) pEvent);

				mEnemyName = enemyName;

				mLock.unlock();

				if (mRobot.isLeader()) {

					mCoordinator.addAction(new BThreadAction(
							BThreadActionType.SEND_MESSAGE, mPriority + 100,
							enemyName));
				}

				return true;
			}

			else if (mEnemyName.equals(enemyName))
				mScannedRobots.push((ScannedRobotEvent) pEvent);

			mLock.unlock();

			return true;
		}

		return false;
	}

	public void addToBThreadsWhichNeedToBeKilled(
			StoppableNotifierThread pStoppableNotifierThread) {
		synchronized (mBThreadsWhichNeedToBeKilled) {
			mBThreadsWhichNeedToBeKilled.add(pStoppableNotifierThread);
		}
	}

	public void removeFromBThreadsWhichNeedToBeKilled(
			StoppableNotifierThread pStoppableNotifierThread) {
		synchronized (mBThreadsWhichNeedToBeKilled) {
			mBThreadsWhichNeedToBeKilled.remove(pStoppableNotifierThread);
		}
	}
}
