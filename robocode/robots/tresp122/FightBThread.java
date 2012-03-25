package tresp122;

import java.util.LinkedList;
import java.util.Queue;

import robocode.Rules;
import robocode.ScannedRobotEvent;

public class FightBThread implements BThread {

	protected AviBatelRobot mRobot;
	protected boolean mDontStop;
	
	Queue<ScannedRobotEvent> mScannedRobots;
	
	public FightBThread(AviBatelRobot pRobot) {

		mRobot = pRobot;
		mDontStop = true;
		
		mScannedRobots = new LinkedList<ScannedRobotEvent>();
	}

	@Override
	public void run() {
		
		while(mDontStop){

			decideWhatToDo();
		}
	}

	private void decideWhatToDo() {

		synchronized (mScannedRobots) {
			
			while (!mScannedRobots.isEmpty()){
			
				ScannedRobotEvent event = mScannedRobots.poll();
					
				if (mRobot.getGunHeat() == 0) {
					
					double degree = mRobot.getHeading() - mRobot.getGunHeading() + event.getBearing();
					
					mRobot.turnGunRight(getID(), degree);
					
					mRobot.fire(getID(), Rules.MAX_BULLET_POWER);
				}
			}
		}
	}

	@Override
	public BThreadID getID() {
		return BThreadID.FIGHT;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		synchronized (mScannedRobots) {
			mScannedRobots.add(event);
		}
	}

	public void stop() {
		mDontStop = false;
	}

}
