package tresp122.bthreads;

import java.util.LinkedList;
import java.util.Queue;

import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import tresp122.coordinator.AviBatelRobot;

public class TrackBThread extends BThread {

	protected Queue<HitByBulletEvent>	mHitByBullet;
	protected Queue<ScannedRobotEvent>	mScannedRobots;
	protected Queue<HitRobotEvent>		mHitRobot;

	public TrackBThread(AviBatelRobot pRobot) { 
		
		super(pRobot);
		
		mHitByBullet = new LinkedList<HitByBulletEvent>();
		mScannedRobots = new LinkedList<ScannedRobotEvent>();
		mHitRobot = new LinkedList<HitRobotEvent>();
	}

	@Override
	public void decideWhatActionToPerform() {

		if (mLock.tryLock()) {

			if (!mHitByBullet.isEmpty()) {

				HitByBulletEvent event = mHitByBullet.poll();

				mLock.unlock();

				// TODO: ..
			}
			
			else
				mLock.unlock();
			
			if (!mScannedRobots.isEmpty()) {

				ScannedRobotEvent event = mScannedRobots.poll();

				mLock.unlock();

				// TODO: ..
			}
			
			else
				mLock.unlock();
			
			if (!mHitRobot.isEmpty()) {

				HitRobotEvent event = mHitRobot.poll();

				mLock.unlock();

				// TODO: ..
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
		
		if (pEvent instanceof ScannedRobotEvent) {

			mLock.lock();

			mScannedRobots.add((ScannedRobotEvent) pEvent);

			mLock.unlock();

		}
		
		if (pEvent instanceof HitRobotEvent) {

			mLock.lock();

			mHitRobot.add((HitRobotEvent) pEvent);

			mLock.unlock();

		}
	}

}
