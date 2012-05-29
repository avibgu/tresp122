package tresp122team.coordinator;

import tresp122team.action.BThreadAction;
import tresp122team.bthreads.BThreadsController;

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

		case INCREASE_FIRE_POWER:
			mBThreadsController.getFightBThread().increaseFirePower();
			break;

		case DECREASE_FIRE_POWER:
			mBThreadsController.getFightBThread().decreaseFirePower();
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
		}
	}
}
