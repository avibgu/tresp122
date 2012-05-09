package tresp122.coordinator;

import java.util.Set;

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
