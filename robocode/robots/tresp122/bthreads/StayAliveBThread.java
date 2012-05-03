package tresp122.bthreads;

import java.util.Set;

import robocode.Event;
import tresp122.coordinator.AviBatelRobot;
import tresp122.coordinator.Coordinator;

public class StayAliveBThread extends BThread {

	public StayAliveBThread(AviBatelRobot pRobot, Coordinator pCoordinator,
			Set<BThread> pBThreadsToRegister) {
		super(pRobot, pCoordinator, pBThreadsToRegister);
	}

	@Override
	public void decideWhichActionToPerform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
		// TODO Auto-generated method stub

	}

}
