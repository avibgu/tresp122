package tresp122;

import robocode.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AviBatelRobot - a robot by (your name here)
 */
public class AviBatelRobot extends AdvancedRobot {
	
	protected Lock mLock;
	
	protected PriorityQueue<BThreadEvent> mEvents;
	
	protected MoveBThread mMoveBThread;
	protected FightBThread mFightBThread;
	
	protected Set<BThread> mOnScannedRobot;
	
	public AviBatelRobot() {
		
		mLock = new ReentrantLock(true);
		
		mEvents = new PriorityQueue<BThreadEvent>(20, new EventsComparator());
		
		mMoveBThread = new MoveBThread(this);
		mFightBThread = new FightBThread(this);
		
		mOnScannedRobot = new HashSet<BThread>();
		
		mOnScannedRobot.add(mMoveBThread);
		mOnScannedRobot.add(mFightBThread);
	}

	public void run() {

		setColors(Color.black, Color.red, Color.green); // body,gun,radar
		
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		
		setTurnRadarLeft(Double.MAX_VALUE);
		
		new Thread(mMoveBThread).start();
		new Thread(mFightBThread).start();
		
		while(true){
			
			decideWhatToDo();
		}
	}

	private void decideWhatToDo() {

		mLock.lock();
		
		if(!mEvents.isEmpty()){

			BThreadEvent event = mEvents.poll();
			
			switch (event.getType()) {
				
				case FIRE:
					setFire(event.getValue());
//					fire(event.getValue());
					break;
					
				case AHEAD:
//					setAhead(event.getValue());
					ahead(event.getValue());
					break;
					
				case BACK:
//					setBack(event.getValue());
					back(event.getValue());
					break;
					
				case TURN_LEFT:
//					setTurnLeft(event.getValue());
					turnLeft(event.getValue());
					break;
					
				case TURN_RIGHT:
//					setTurnRight(event.getValue());
					turnRight(event.getValue());
					break;
					
				case TURN_GUN_LEFT:
//					setTurnGunLeft(event.getValue());
					turnGunLeft(event.getValue());
					break;
					
				case TURN_GUN_RIGHT:
//					setTurnGunRight(event.getValue());
					turnGunRight(event.getValue());
					break;
					
				case TURN_RADAR_LEFT:
//					setTurnRadarLeft(event.getValue());
					turnRadarLeft(event.getValue());
					break;
					
				case TURN_RADAR_RIGHT:
//					setTurnRadarRight(event.getValue());
					turnRadarRight(event.getValue());
					break;
			}

			execute();
		}
		else {
			doNothing();
		}
		
		mLock.unlock();
	}

	
	// Events:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		for (BThread bThread : mOnScannedRobot)
			bThread.onScannedRobot(event);
	}
	
	
	// Basic Moves:
	

	public void addEvent(BThreadID id, BThreadEvent event) {

		mLock.tryLock();
	          
			try {
				mEvents.add(event);
			}
			finally {
				mLock.unlock();
			}

	}

	
	// Handle Threads:
	
	
	@Override
	public void onWin(WinEvent event) {
		super.onWin(event);
		stopAllThreads();		
	}
	
	@Override
	public void onDeath(DeathEvent event) {
		super.onDeath(event);
		stopAllThreads();		
	}
	
	@Override
	public void onBattleEnded(BattleEndedEvent event) {
		super.onBattleEnded(event);
		stopAllThreads();
	}

	private void stopAllThreads() {
		mLock.unlock();
		mMoveBThread.stop();
		mFightBThread.stop();
	}
}
