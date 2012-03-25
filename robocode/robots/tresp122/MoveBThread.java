package tresp122;

import robocode.MoveCompleteCondition;
import robocode.ScannedRobotEvent;

public class MoveBThread extends BThread {

	public MoveBThread(AviBatelRobot pRobot) {
		super(pRobot);
	}

	@Override
	public void decideWhatToDo() {

		mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.AHEAD, 0, 300));
		mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_RIGHT, 0, 90));
		
		while(!new MoveCompleteCondition(mRobot).test() && mDontStop){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public BThreadID getID() {
		return BThreadID.MOVE;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {}
}
