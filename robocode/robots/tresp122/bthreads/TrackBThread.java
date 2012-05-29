package tresp122.bthreads;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import robocode.BulletHitBulletEvent;
import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import tresp122.coordinator.AviBatelRobot;
import tresp122.event.BThreadEvent;
import tresp122.event.BThreadEventType;

public class TrackBThread extends BThread {

	protected Queue<HitByBulletEvent>	mHitByBullet;
	protected Queue<ScannedRobotEvent>	mScannedRobots;
	protected Queue<HitRobotEvent>		mHitRobots;
	protected Queue<BulletHitBulletEvent>	mBulletHitBullets;
	
	protected int mNumOfHitByBullet;
	protected long mTime;

	public TrackBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitByBullet = new LinkedList<HitByBulletEvent>();
		mScannedRobots = new LinkedList<ScannedRobotEvent>();
		mHitRobots = new LinkedList<HitRobotEvent>();
		mBulletHitBullets = new LinkedList<BulletHitBulletEvent>();
		
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
				
				if (mNumOfHitByBullet > 2){
					
					long time = event.getTime() - mTime;
					
					if (mNumOfHitByBullet / (time/1000) > 1){
						
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

			if (!mBulletHitBullets.isEmpty()) {

				BulletHitBulletEvent event = mBulletHitBullets.poll();

				mLock.unlock();
				
				double bulletPower = event.getHitBullet().getPower();
				
				double damage = 4 * bulletPower + 2 * Math.max(bulletPower - 1, 0);
				
				if (damage > 6)
					super.notifyToMailingList(new BThreadEvent(
						BThreadEventType.WE_MADE_DAMAGE_TO_ENEMY));
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
		
		if (pEvent instanceof BulletHitBulletEvent) {

			mLock.lock();

			mBulletHitBullets.add((BulletHitBulletEvent) pEvent);

			mLock.unlock();

		}
	}
}
