package tresp122.bthreads;

import java.util.Set;

import robocode.Event;
import tresp122.coordinator.AviBatelRobot;
import tresp122.event.BThreadEvent;
import tresp122.event.BThreadEventType;

public class StayAliveBThread extends BThread {

	public StayAliveBThread(AviBatelRobot pRobot,
			Set<BThread> pBThreadsToRegister) {
		super(pRobot, pBThreadsToRegister);
	}

	@Override
	public void decideWhichActionToPerform() {
		// TODO Auto-generated method stub

//		INCREASE_AVOID_BULLETS_PRIORITY,
//		DECREASE_AVOID_BULLETS_PRIORITY,
//		INCREASE_AVOID_COLLISIONS_PRIORITY,
//		DECREASE_AVOID_COLLISIONS_PRIORITY,
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof BThreadEvent) {

			BThreadEvent event = (BThreadEvent) pEvent;

			if (event.getType() == BThreadEventType.WE_ARE_WEAK) {

				mLock.lock();

				// ...

				mLock.unlock();
			}

			else if (event.getType() == BThreadEventType.WE_ARE_UNDER_ATTACK) {

				mLock.lock();

				// ...

				mLock.unlock();
			}
		}
	}

}
