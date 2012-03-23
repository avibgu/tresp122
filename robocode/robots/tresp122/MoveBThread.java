package tresp122;

import robocode.AdvancedRobot;
import robocode.Condition;
import robocode.TurnCompleteCondition;

public class MoveBThread implements BThread {

	protected AdvancedRobot mRobot;
	protected boolean mDontStop;
	
	public MoveBThread(AdvancedRobot pRobot) {

		mRobot = pRobot;
		mDontStop = true;
	}
	
	@Override
	public void run() {
		
		while(mDontStop) {
			
			waitFor(new TurnCompleteCondition(mRobot));
			
			mRobot.setTurnRadarRight(360);
			mRobot.setAhead(100);
		}
	}

	private void waitFor(Condition condition) {
		while(!condition.test()) continue;
	}

	public void stop() {
		mDontStop = false;
	}
}
