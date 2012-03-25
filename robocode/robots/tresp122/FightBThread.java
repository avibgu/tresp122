package tresp122;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import robocode.Rules;
import robocode.ScannedRobotEvent;

public class FightBThread extends BThread {
	
	protected Lock mLock;
	
	protected Queue<ScannedRobotEvent> mScannedRobots;
	
	public FightBThread(AviBatelRobot pRobot) {

		super(pRobot);
		
		mLock = new ReentrantLock(true);
		
		mScannedRobots = new LinkedList<ScannedRobotEvent>();
	}

	public void decideWhatToDo() {

//		 if (mLock.tryLock()) {
//
//			 try {

				 if (!mScannedRobots.isEmpty()){
	      			
					 ScannedRobotEvent event = mScannedRobots.poll();
	  					
					 if (mRobot.getGunHeat() == 0) {
	  					
						 double degree = mRobot.getHeading() - mRobot.getGunHeading() + event.getBearing();
	  					
						 mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_GUN_RIGHT, 11, degree));
						 mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.FIRE, 10, Rules.MAX_BULLET_POWER));
					 }
				 }
//			 }
//			 finally {
//				 
//				 mLock.unlock();
//			 }
//		 }
	}

	@Override
	public BThreadID getID() {
		return BThreadID.FIGHT;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {

//		 if (mLock.tryLock()) {
//		
//			 try{
				 mScannedRobots.add(event);
//			 }
//			 finally{
//				 mLock.unlock();
//			 }
//		 }
	}
	
	@Override
	public void stop() {
//		mLock.unlock();
		super.stop();
	}
}
