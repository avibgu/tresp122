package tresp122.bthreads;


import robocode.Event;
import tresp122.coordinator.AviBatelRobot;

public class KeepEnergyBThread extends BThread {

	public KeepEnergyBThread(AviBatelRobot pRobot) {
		super(pRobot);
		mPriority = 17;
	}

	public void decideWhichActionToPerform() {

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
