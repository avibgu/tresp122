package tresp122;

import robocode.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AviBatelRobot - a robot by (your name here)
 */
public class AviBatelRobot extends AdvancedRobot {

	protected Map<BThreadID,Double> mAhead;
	protected Map<BThreadID,Double> mTurnRight;
	protected Map<BThreadID,Double> mTurnGunRight;
	protected Map<BThreadID,Double> mFire;
	
	protected MoveBThread mMoveBThread;
	protected FightBThread mFightBThread;
	
	protected Set<BThread> mOnScannedRobot;
	
	public AviBatelRobot() {

		mAhead = new HashMap<BThreadID, Double>();
		mTurnRight = new HashMap<BThreadID, Double>();
		mTurnGunRight = new HashMap<BThreadID, Double>();
		mFire = new HashMap<BThreadID, Double>();
		
		mMoveBThread = new MoveBThread(this);
		mFightBThread = new FightBThread(this);
		
		mOnScannedRobot = new HashSet<BThread>();
		
		mOnScannedRobot.add(mMoveBThread);
		mOnScannedRobot.add(mFightBThread);
	}

	public void run() {

		setColors(Color.black, Color.red, Color.green); // body,gun,radar
		
		setAdjustRadarForRobotTurn(true);
		
		setTurnRadarLeft(Double.MAX_VALUE);
		
		new Thread(mMoveBThread).start();
		new Thread(mFightBThread).start();
		
		while(true){
			
			decideWhatToDo();
		}
	}

	private void decideWhatToDo() {
		
		synchronized (mAhead) {
			
			for (BThreadID id : mAhead.keySet())
				super.ahead(mAhead.get(id));
			
			mAhead.clear();
		}
		
		execute();
		
		synchronized (mTurnRight) {
			
			for (BThreadID id : mTurnRight.keySet())
				super.turnRight(mTurnRight.get(id));
			
			mTurnRight.clear();
		}
		
		execute();
		
		synchronized (mTurnGunRight) {
			
			for (BThreadID id : mTurnGunRight.keySet())
				super.turnGunRight(mTurnGunRight.get(id));
			
			mTurnGunRight.clear();
		}
		
		execute();
		
		synchronized (mFire) {
			
			for (BThreadID id : mFire.keySet())
				super.fire(mFire.get(id));
			
			mFire.clear();
		}
		
		execute();
	}

	
	// Events:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		for (BThread bThread : mOnScannedRobot)
			bThread.onScannedRobot(event);
	}
	
	
	// Basic Moves:
	

	public void ahead(BThreadID id, double distance) {
		synchronized (mAhead) {
			mAhead.put(id, distance);
		}
	}

	public void turnRight(BThreadID id, double degrees) {
		synchronized (mTurnRight) {
			mTurnRight.put(id, degrees);
		}
	}
	
	public void turnGunRight(BThreadID id, double degrees) {
		synchronized (mTurnGunRight) {
			mTurnGunRight.put(id, degrees);
		}
	}
	
	public void fire(BThreadID id, double power) {	
		synchronized (mFire) {
			mFire.put(id, power);
		}
	}
	
	// Handle Threads:
	
	
	@Override
	public void onWin(WinEvent event) {
		stopAllThreads();
		super.onWin(event);
	}
	
	@Override
	public void onDeath(DeathEvent event) {
		stopAllThreads();
		super.onDeath(event);
	}
	
	@Override
	public void onBattleEnded(BattleEndedEvent event) {

		stopAllThreads();
		super.onBattleEnded(event);
	}

	private void stopAllThreads() {
		mMoveBThread.stop();
		mFightBThread.stop();
	}
}
