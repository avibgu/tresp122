package tresp122;

import robocode.Robot;

public class MoveBThread implements BThread {

	protected Robot mRobot;
	protected boolean mDontStop;
	
	public MoveBThread(Robot pRobot) {

		mRobot = pRobot;
		mDontStop = true;
	}
	
	@Override
	public void run() {

		while(mDontStop) {
			
			mRobot.ahead(100);
		}
	}

	public void stop() {
		mDontStop = false;
	}
}
