package tresp122team.utilities;

import java.util.Set;

import tresp122team.coordinator.Coordinator;

public class StopperThread implements Runnable {

	private Set<Coordinator> mCoordinators;
	
	public StopperThread(Set<Coordinator> pCoordinators) {
		mCoordinators = pCoordinators;
	}

	@Override
	public void run() {
		for (Coordinator coordinator : mCoordinators)
			coordinator.stop();
	}

}
