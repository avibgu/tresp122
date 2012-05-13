package tresp122.bthreads;

import java.util.Set;

import robocode.Event;

import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;
import tresp122.event.BThreadEvent;
import tresp122.event.BThreadEventType;

public class KillBThread extends BThread {

	private BThreadEvent mWeAreStrongEvent;
	private BThreadEvent mEnemeyIsWeakEvent;
	private BThreadEvent mWeMadeDamageToEnemyEvent;

	public KillBThread(AviBatelRobot pRobot, Set<BThread> pBThreadsToRegister) {

		super(pRobot, pBThreadsToRegister);

		mPriority = 20;
		
		mWeAreStrongEvent = null;
		mEnemeyIsWeakEvent = null;
		mWeMadeDamageToEnemyEvent = null;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mWeAreStrongEvent) {

				mWeAreStrongEvent = null;

				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_FIGHT_PRIORITY, mPriority,
						10));

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.DECREASE_AVOID_BULLETS_PRIORITY,
						mPriority, 10));

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.DECREASE_AVOID_COLLISIONS_PRIORITY,
						mPriority, 10));
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (null != mEnemeyIsWeakEvent) {

				mEnemeyIsWeakEvent = null;

				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_FIRE_POWER, mPriority, 1));

				if (mRobot.getEnergy() > 50)
					super.notifyToMailingList(new BThreadEvent(
							BThreadEventType.WE_ARE_GOING_TO_WIN));
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (null != mWeMadeDamageToEnemyEvent) {

				mWeMadeDamageToEnemyEvent = null;

				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_FIGHT_PRIORITY, mPriority,
						10));
			}

			else
				mLock.unlock();
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof BThreadEvent) {

			BThreadEvent event = (BThreadEvent) pEvent;

			if (event.getType() == BThreadEventType.WE_ARE_STRONG) {

				mLock.lock();

				mWeAreStrongEvent = event;

				mLock.unlock();
			}

			else if (event.getType() == BThreadEventType.ENEMY_IS_WEAK) {

				mLock.lock();

				mEnemeyIsWeakEvent = event;

				mLock.unlock();
			}

			else if (event.getType() == BThreadEventType.WE_MADE_DAMAGE_TO_ENEMY) {

				mLock.lock();

				mWeMadeDamageToEnemyEvent = event;

				mLock.unlock();
			}
		}
	}

}
