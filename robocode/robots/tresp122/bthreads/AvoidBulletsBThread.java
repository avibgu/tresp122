package tresp122.bthreads;

import java.util.LinkedList;
import java.util.Queue;

import robocode.Event;
import robocode.HitByBulletEvent;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class AvoidBulletsBThread extends BThread {

	protected Queue<HitByBulletEvent> mHitByBullet;

	public AvoidBulletsBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitByBullet = new LinkedList<HitByBulletEvent>();
	}

	@Override
	public void decideWhatActionToPerform() {

		if (mLock.tryLock()) {

			if (!mHitByBullet.isEmpty()) {

				HitByBulletEvent event = mHitByBullet.poll();

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
	}

}
