package tresp122Advanced.coordinator;

import tresp122Advanced.action.BThreadAction;
import tresp122Advanced.bthreads.BThreadsController;

public class Level0Coordinator extends LeveliCoordinator {

	public Level0Coordinator(AviBatelRobot pRobot,
			BThreadsController pBThreadsController) {
		super(pRobot, pBThreadsController);
	}

	@Override
	public void setMyselfAsTheCoordinatorOfBThreadsInMyLevel() {

		mBThreadsController.getSuccessBThread().setCoordinator(this);
	}

	@Override
	public void performAction(BThreadAction action) {

		switch (action.getType()) {

		case INCREASE_KILL_PRIORITY:
			mBThreadsController.getKillBThread().increasePriority(
					(int) action.getValue());
			break;

		case DECREASE_KILL_PRIORITY:
			mBThreadsController.getKillBThread().decreasePriority(
					(int) action.getValue());
			break;

		case INCREASE_STAY_ALIVE_PRIORITY:
			mBThreadsController.getStayAliveBThread().increasePriority(
					(int) action.getValue());
			break;

		case DECREASE_STAY_ALIVE_PRIORITY:
			mBThreadsController.getStayAliveBThread().decreasePriority(
					(int) action.getValue());
			break;
		}
	}
}
