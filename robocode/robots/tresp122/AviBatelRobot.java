package tresp122;

import robocode.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AviBatelRobot - a robot by (your name here)
 */
public class AviBatelRobot extends AdvancedRobot {
	
	protected Lock mLock;
	protected boolean dontStop;
	
	protected PriorityQueue<BThreadEvent> mEvents;
	
	protected MoveBThread mMoveBThread;
	protected FightBThread mFightBThread;
	
	protected Set<BThread> mAllThreads;
	protected Set<BThread> mOnScannedRobot;
	protected Set<BThread> mOnHitWall;

	protected ExecutorService mExecutor;
	
	public AviBatelRobot() {
		
		dontStop = true;
		
		mLock = new ReentrantLock(true);
		
		mEvents = new PriorityQueue<BThreadEvent>(20, new EventsComparator());
		
		mMoveBThread = new MoveBThread(this);
		mFightBThread = new FightBThread(this);
		
		mAllThreads = new HashSet<BThread>();
		mOnScannedRobot = new HashSet<BThread>();
		mOnHitWall = new HashSet<BThread>();
		
		mAllThreads.add(mMoveBThread);
		mOnScannedRobot.add(mMoveBThread);
		mOnHitWall.add(mMoveBThread);
		
		mAllThreads.add(mFightBThread);
		mOnScannedRobot.add(mFightBThread);
		mOnHitWall.add(mFightBThread);
		
		mExecutor = Executors.newSingleThreadExecutor();
	}

	public void run() {

		setColors(Color.black, Color.red, Color.green); // body,gun,radar
		
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		
		setTurnRadarLeft(Double.MAX_VALUE);

		new Thread(mMoveBThread).start();
		new Thread(mFightBThread).start();
	
		while(dontStop){
			
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
					setAhead(event.getValue());
//					ahead(event.getValue());
					break;
					
				case BACK:
					setBack(event.getValue());
//					back(event.getValue());
					break;
					
				case TURN_LEFT:
					setTurnLeft(event.getValue());
//					turnLeft(event.getValue());
					break;
					
				case TURN_RIGHT:
					setTurnRight(event.getValue());
//					turnRight(event.getValue());
					break;
					
				case TURN_GUN_LEFT:
					setTurnGunLeft(event.getValue());
//					turnGunLeft(event.getValue());
					break;
					
				case TURN_GUN_RIGHT:
					setTurnGunRight(event.getValue());
//					turnGunRight(event.getValue());
					break;
					
				case TURN_RADAR_LEFT:
					setTurnRadarLeft(event.getValue());
//					turnRadarLeft(event.getValue());
					break;
					
				case TURN_RADAR_RIGHT:
					setTurnRadarRight(event.getValue());
//					turnRadarRight(event.getValue());
					break;
			}

			execute();
		}
		else {
			doNothing();
		}
		
		mLock.unlock();
	}

	
	// Events from the Battlefield:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		mExecutor.execute(new NotifierThread(mOnScannedRobot, event));
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		mExecutor.execute(new NotifierThread(mOnHitWall, event));
	}
	
	
	// Basic Moves:
	

	public void addEvent(BThreadID id, BThreadEvent event) {

		try {
			
			mLock.lock();
			mEvents.add(event);
			mLock.unlock();
		}
		catch (Exception e) {}
	}

	
	// Handle Threads:
	
	
	@Override
	public void onWin(WinEvent event) {
		stopAllThreads(event);
	}
	
	@Override
	public void onDeath(DeathEvent event) {
		stopAllThreads(event);
	}
	
	@Override
	public void onBattleEnded(BattleEndedEvent event) {
		stopAllThreads(event);
	}

	private void stopAllThreads(Event event) {
		
		dontStop = false;
		
		setStop();
		clearAllEvents();
		
		mExecutor.shutdown();
		
		new Thread(new NotifierThread(mAllThreads, event)).start();
	}
}
