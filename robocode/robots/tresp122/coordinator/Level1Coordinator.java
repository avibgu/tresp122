package tresp122.coordinator;

import tresp122.action.BThreadAction;
import tresp122.bthreads.BThreadsController;

public class Level1Coordinator extends LeveliCoordinator {

	public Level1Coordinator(AviBatelRobot pRobot,
			BThreadsController pBThreadsController) {
		super(pRobot, pBThreadsController);
	}

	@Override
	public void setMyselfAsTheCoordinatorOfBThreadsInMyLevel() {

		mBThreadsController.getKillBThread().setCoordinator(this);
		mBThreadsController.getStayAliveBThread().setCoordinator(this);
	}

	@Override
	public void performAction(BThreadAction action) {

		switch (action.getType()) {

		case DECREASE_FIGHT_PRIORITY:
			mBThreadsController.getFightBThread().decreasePriority(
					(int) action.getValue());
			break;

		case INCREASE_FIGHT_PRIORITY:
			mBThreadsController.getFightBThread().increasePriority(
					(int) action.getValue());
			break;

		case DECREASE_AVOID_BULLETS_PRIORITY:
			mBThreadsController.getAvoidBulletsBThread().decreasePriority(
					(int) action.getValue());
			break;

		case INCREASE_AVOID_BULLETS_PRIORITY:
			mBThreadsController.getAvoidBulletsBThread().increasePriority(
					(int) action.getValue());
			break;

		case DECREASE_AVOID_COLLISIONS_PRIORITY:
			mBThreadsController.getAvoidCollisionsBThread().decreasePriority(
					(int) action.getValue());
			break;

		case INCREASE_AVOID_COLLISIONS_PRIORITY:
			mBThreadsController.getAvoidCollisionsBThread().increasePriority(
					(int) action.getValue());
			break;

		case DECREASE_KEEP_ENERGY_PRIORITY:
			mBThreadsController.getKeepEnergyBThread().decreasePriority(
					(int) action.getValue());
			break;

		case INCREASE_KEEP_ENERGY_PRIORITY:
			mBThreadsController.getKeepEnergyBThread().increasePriority(
					(int) action.getValue());
			break;

		case DECREASE_TRACK_PRIORITY:
			mBThreadsController.getTrackBThread().decreasePriority(
					(int) action.getValue());
			break;

		case INCREASE_TRACK_PRIORITY:
			mBThreadsController.getTrackBThread().increasePriority(
					(int) action.getValue());
			break;
		}
	}
}