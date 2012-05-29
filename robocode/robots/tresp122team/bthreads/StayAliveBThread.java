package tresp122team.bthreads;

import java.util.Set;

import robocode.Event;
import tresp122team.action.BThreadAction;
import tresp122team.action.BThreadActionType;
import tresp122team.coordinator.AviBatelRobot;
import tresp122team.event.BThreadEvent;
import tresp122team.event.BThreadEventType;

public class StayAliveBThread extends BThread {

	private BThreadEvent mWeAreWeakEvent;
	private BThreadEvent mWeAreUnderAttackEvent;

	public StayAliveBThread(AviBatelRobot pRobot,
			Set<BThread> pBThreadsToRegister) {

		super(pRobot, pBThreadsToRegister);

		mPriority = 10;
		
		mWeAreWeakEvent = null;
		mWeAreUnderAttackEvent = null;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (null != mWeAreWeakEvent) {

				mWeAreWeakEvent = null;

				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_AVOID_BULLETS_PRIORITY,
						mPriority, 10));

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_AVOID_COLLISIONS_PRIORITY,
						mPriority, 10));

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.DECREASE_FIRE_POWER, mPriority, 1));

				super.notifyToMailingList(new BThreadEvent(
						BThreadEventType.WE_ARE_GOING_TO_DIE));
			}

			else
				mLock.unlock();
		}

		if (mLock.tryLock()) {

			if (null != mWeAreUnderAttackEvent) {

				mWeAreUnderAttackEvent = null;

				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_AVOID_BULLETS_PRIORITY,
						mPriority, 10));

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_AVOID_COLLISIONS_PRIORITY,
						mPriority, 10));
			}

			else
				mLock.unlock();
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof BThreadEvent) {

			BThreadEvent event = (BThreadEvent) pEvent;

			if (event.getType() == BThreadEventType.WE_ARE_WEAK) {

				mLock.lock();

				mWeAreWeakEvent = event;

				mLock.unlock();
			}

			else if (event.getType() == BThreadEventType.WE_ARE_UNDER_ATTACK) {

				mLock.lock();

				mWeAreUnderAttackEvent = event;

				mLock.unlock();
			}
		}
	}

}
