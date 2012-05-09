package tresp122.bthreads;

import java.util.Set;

import robocode.Event;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;
import tresp122.event.BThreadEvent;
import tresp122.event.BThreadEventType;

public class SuccessBThread extends BThread {

	protected BThreadEvent mWeAreGoingToWinEvent;
	protected BThreadEvent mWeAreGoingToDieEvent;

	public SuccessBThread(AviBatelRobot pRobot, Set<BThread> pBThreadsToRegister) {
		
		super(pRobot, pBThreadsToRegister);
		
		mWeAreGoingToWinEvent = null;
		mWeAreGoingToDieEvent = null;
	}

	@Override
	public void decideWhichActionToPerform() {
		
		if (mLock.tryLock()) {

			if (null != mWeAreGoingToWinEvent) {

				mWeAreGoingToWinEvent = null;

				mLock.unlock();

				// TODO: maybe need to check the current priority..

				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_KILL_PRIORITY, mPriority,
						10));
				
				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.DECREASE_STAY_ALIVE_PRIORITY, mPriority,
						10));
			}

			else
				mLock.unlock();
		}
		
		if (mLock.tryLock()) {

			if (null != mWeAreGoingToDieEvent) {

				mWeAreGoingToDieEvent = null;

				mLock.unlock();

				// TODO: maybe need to check the current priority..
				
				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.DECREASE_KILL_PRIORITY, mPriority,
						10));
				
				mCoordinator.addAction(new BThreadAction(
						BThreadActionType.INCREASE_STAY_ALIVE_PRIORITY, mPriority,
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

			if (event.getType() == BThreadEventType.WE_ARE_GOING_TO_WIN) {

				mLock.lock();

				mWeAreGoingToWinEvent = event;

				mLock.unlock();
			}

			else if (event.getType() == BThreadEventType.WE_ARE_GOING_TO_DIE) {

				mLock.lock();

				mWeAreGoingToDieEvent = event;

				mLock.unlock();
			}
		}
	}
}