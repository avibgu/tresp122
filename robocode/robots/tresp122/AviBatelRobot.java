package tresp122;

import robocode.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AviBatelRobot - a robot by (your name here)
 */
public class AviBatelRobot extends AdvancedRobot {
	
	protected PriorityQueue<BThreadEvent> mEvents;
	
	protected MoveBThread mMoveBThread;
	protected FightBThread mFightBThread;
	
	protected Set<BThread> mOnScannedRobot;
	
	public AviBatelRobot() {
		
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

		while(!mEvents.isEmpty()){

			synchronized(mEvents){
				
				BThreadEvent event = mEvents.poll();
				
				switch (event.getType()) {
					
					case FIRE:
						setFire(event.getValue());
						break;
						
					case AHEAD:
						setAhead(event.getValue());
						break;
						
					case BACK:
						setBack(event.getValue());
						break;
						
					case TURN_LEFT:
						setTurnLeft(event.getValue());
						break;
						
					case TURN_RIGHT:
						setTurnRight(event.getValue());
						break;
						
					case TURN_GUN_LEFT:
						setTurnGunLeft(event.getValue());
						break;
						
					case TURN_GUN_RIGHT:
						setTurnGunRight(event.getValue());
						break;
						
					case TURN_RADAR_LEFT:
						setTurnRadarLeft(event.getValue());
						break;
						
					case TURN_RADAR_RIGHT:
						setTurnRadarRight(event.getValue());
						break;
				}
			}
			
			execute();
		}
	}

	
	// Events:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		for (BThread bThread : mOnScannedRobot)
			bThread.onScannedRobot(event);
	}
	
	
	// Basic Moves:
	

	public void addEvent(BThreadID id, BThreadEvent event) {
		synchronized(mEvents){
			mEvents.add(event);
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
		mMoveBThread.stop();
		mFightBThread.stop();
	}
}
