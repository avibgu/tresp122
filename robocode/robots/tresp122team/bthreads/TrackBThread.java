package tresp122team.bthreads;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import robocode.BulletHitEvent;
import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import tresp122team.action.BThreadAction;
import tresp122team.action.BThreadActionType;
import tresp122team.coordinator.AviBatelRobot;
import tresp122team.event.BThreadEvent;
import tresp122team.event.BThreadEventType;

public class TrackBThread extends BThread {

	protected Queue<HitByBulletEvent> mHitByBullet;
	protected Queue<ScannedRobotEvent> mScannedRobots;
	protected Queue<HitRobotEvent> mHitRobots;
	protected Queue<BulletHitEvent> mBulletHits;

	protected int mNumOfHitByBullet;
	protected long mTime;

	public TrackBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitByBullet = new LinkedList<HitByBulletEvent>();
		mScannedRobots = new LinkedList<ScannedRobotEvent>();
		mHitRobots = new LinkedList<HitRobotEvent>();
		mBulletHits = new LinkedList<BulletHitEvent>();

		mPriority = 7;

		mNumOfHitByBullet = 0;
		mTime = new Date().getTime();
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (!mHitByBullet.isEmpty()) {

				HitByBulletEvent event = mHitByBullet.poll();

				mLock.unlock();

				mNumOfHitByBullet++;

				if (mNumOfHitByBullet > 2) {

					long time = event.getTime() - mTime;

					if (mNumOfHitByBullet / (time / 1000) > 1) {

						super.notifyToMailingList(new BThreadEvent(
								BThreadEventType.WE_ARE_UNDER_ATTACK));

						mTime = new Date().getTime();
						mNumOfHitByBullet = 0;
					}
				}
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (!mScannedRobots.isEmpty()) {

				ScannedRobotEvent event = mScannedRobots.poll();

				mLock.unlock();

				if (event.getEnergy() < 30)
					super.notifyToMailingList(new BThreadEvent(
							BThreadEventType.ENEMY_IS_WEAK));
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (!mHitRobots.isEmpty()) {

				HitRobotEvent event = mHitRobots.poll();

				mLock.unlock();

				if (event.getEnergy() < 30)
					super.notifyToMailingList(new BThreadEvent(
							BThreadEventType.ENEMY_IS_WEAK));
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (!mBulletHits.isEmpty()) {

				BulletHitEvent event = mBulletHits.poll();

				mLock.unlock();

				double bulletPower = event.getBullet().getPower();

				double damage = 4 * bulletPower + 2
						* Math.max(bulletPower - 1, 0);

				if (damage > 6)
					super.notifyToMailingList(new BThreadEvent(
							BThreadEventType.WE_MADE_DAMAGE_TO_ENEMY));

				if (event.getEnergy() == 0) {

					super.notifyToMailingList(new BThreadEvent(
							BThreadEventType.ENEMY_IS_DEAD));

					mCoordinator.addAction(new BThreadAction(
							BThreadActionType.SEND_MESSAGE, mPriority + 100,
							"ENEMY_IS_DEAD"));
				}

				else if (event.getEnergy() < 30)
					super.notifyToMailingList(new BThreadEvent(
							BThreadEventType.ENEMY_IS_WEAK));
			}

			else
				mLock.unlock();
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof HitByBulletEvent) {

			mLock.lock();

			mHitByBullet.add((HitByBulletEvent) pEvent);

			mLock.unlock();

		}

		else if (pEvent instanceof ScannedRobotEvent) {

			mLock.lock();

			mScannedRobots.add((ScannedRobotEvent) pEvent);

			mLock.unlock();

		}

		else if (pEvent instanceof HitRobotEvent) {

			mLock.lock();

			mHitRobots.add((HitRobotEvent) pEvent);

			mLock.unlock();

		}

		if (pEvent instanceof BulletHitEvent) {

			mLock.lock();

			mBulletHits.add((BulletHitEvent) pEvent);

			mLock.unlock();

		}
	}
}
