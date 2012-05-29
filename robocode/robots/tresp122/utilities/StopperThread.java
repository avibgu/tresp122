package tresp122.utilities;

import java.util.Set;

import tresp122.coordinator.Coordinator;

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
