package tresp122team.bthreads;

import robocode.Event;

import tresp122team.coordinator.AviBatelRobot;
import tresp122team.event.BThreadEvent;
import tresp122team.event.BThreadEventType;

public class KeepEnergyBThread extends BThread {

	private double mEnergy;

	public KeepEnergyBThread(AviBatelRobot pRobot) {
		super(pRobot);
		mEnergy = -1;
	}

	public void decideWhichActionToPerform() {

		double energy = mRobot.getEnergy();

		// energy is going up
		if (energy > mEnergy) {

			if (energy > 80)
				super.notifyToMailingList(new BThreadEvent(
						BThreadEventType.WE_ARE_STRONG));
		}

		// energy is going down
		else {

			if (energy < 30)
				super.notifyToMailingList(new BThreadEvent(
						BThreadEventType.WE_ARE_WEAK));
		}

		mEnergy = energy;
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
	}
}
