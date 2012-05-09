package tresp122.bthreads;

import java.util.Set;

import robocode.Event;
import robocode.ScannedRobotEvent;

import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;
import tresp122.event.BThreadEvent;
import tresp122.event.BThreadEventType;

public class KillBThread extends BThread {

	private BThreadEvent mWeAreStrongEvents;
	private BThreadEvent mEnemeyIsWeakEvents;
	private BThreadEvent mWeMadeDamageToEnemyEvents;

	public KillBThread(AviBatelRobot pRobot, Set<BThread> pBThreadsToRegister) {

		super(pRobot, pBThreadsToRegister);

		mWeAreStrongEvents = null;
		mEnemeyIsWeakEvents = null;
		mWeMadeDamageToEnemyEvents = null;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mWeAreStrongEvents) {

				mWeAreStrongEvents = null;

				mLock.unlock();

				// TODO: maybe need to check the current priority..

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_FIGHT_PRIORITY, mPriority,
						10));
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (null != mEnemeyIsWeakEvents) {

				mEnemeyIsWeakEvents = null;

				mLock.unlock();

				// TODO: maybe need to check the current fire power..
				
				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_FIRE_POWER, mPriority,
						0.5));
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (null != mWeMadeDamageToEnemyEvents) {

				mWeMadeDamageToEnemyEvents = null;

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

				mWeAreStrongEvents = event;

				mLock.unlock();
			}

			else if (event.getType() == BThreadEventType.ENEMY_IS_WEAK) {

				mLock.lock();

				mEnemeyIsWeakEvents = event;

				mLock.unlock();
			}

			else if (event.getType() == BThreadEventType.WE_MADE_DAMAGE_TO_ENEMY) {

				mLock.lock();

				mWeMadeDamageToEnemyEvents = event;

				mLock.unlock();
			}
		}
	}

}
