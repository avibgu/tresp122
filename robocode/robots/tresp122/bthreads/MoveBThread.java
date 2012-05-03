package tresp122.bthreads;

import robocode.Event;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class MoveBThread extends BThread {

	public MoveBThread(AviBatelRobot pRobot) {
		super(pRobot);
		mPriority = 0;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (Math.random() > 0.5)
			mCoordinator.addAction(new BThreadAction(
					BThreadActionType.TURN_RIGHT, mPriority, 90));

		else
			mCoordinator.addAction(new BThreadAction(
					BThreadActionType.TURN_LEFT, mPriority, 90));

		mCoordinator.addAction(new BThreadAction(BThreadActionType.AHEAD, 0,
				150));
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
	}
}
