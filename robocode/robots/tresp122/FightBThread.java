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
		
			synchronized (mScannedRobots) {
				try {
					mScannedRobots.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			decideWhatToDo();
		}
	}

	private void decideWhatToDo() {

		synchronized (mScannedRobots) {
			
			while (!mScannedRobots.isEmpty()){
			
				ScannedRobotEvent event = mScannedRobots.poll();
					
				if (mRobot.getGunHeat() == 0) {
					
					mRobot.turnGunRight(getID(), event.getBearing());
					
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
			mScannedRobots.notifyAll();
		}
	}

	public void stop() {
		mDontStop = false;
	}

}
