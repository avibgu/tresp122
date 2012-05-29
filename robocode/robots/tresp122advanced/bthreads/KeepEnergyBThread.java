package tresp122.bthreads;


import robocode.Event;
import tresp122.coordinator.AviBatelRobot;

public class KeepEnergyBThread extends BThread {

	public KeepEnergyBThread(AviBatelRobot pRobot) {
		super(pRobot);
	}

	public void decideWhatActionToPerform() {

		// TODO: ..
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
	}
}
