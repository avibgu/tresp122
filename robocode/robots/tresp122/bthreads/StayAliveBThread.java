package tresp122.bthreads;

import java.util.Set;

import robocode.Event;
import tresp122.coordinator.AviBatelRobot;

public class StayAliveBThread extends BThread {

	public StayAliveBThread(AviBatelRobot pRobot, Set<BThread> pBThreadsToRegister) {
		super(pRobot, pBThreadsToRegister);
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
