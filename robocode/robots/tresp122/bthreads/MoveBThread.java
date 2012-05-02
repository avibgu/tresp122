package tresp122.bthreads;

import robocode.Event;
import robocode.MoveCompleteCondition;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class MoveBThread extends BThread {

	public MoveBThread(AviBatelRobot pRobot) {
		super(pRobot);
	}

	@Override
	public void decideWhatActionToPerform() {

		if (Math.random() > 0.5)
			mCoordinator.addAction(new BThreadAction(
					BThreadActionType.TURN_RIGHT, 0, 90));

		else
			mCoordinator.addAction(new BThreadAction(
					BThreadActionType.TURN_LEFT, 0, 90));

		mCoordinator.addAction(new BThreadAction(BThreadActionType.AHEAD, 0,
				150));

//		waitUntilTheRobotIsNotMoving();
	}

	protected void waitUntilTheRobotIsNotMoving() {

		try {

			while (!new MoveCompleteCondition(mRobot).test() && mDontStop) {

				try {

					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
	}
}
