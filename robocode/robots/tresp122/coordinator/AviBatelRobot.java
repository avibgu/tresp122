package tresp122.coordinator;

import robocode.*;
import tresp122.action.ActionsComparator;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.bthreads.AvoidBulletsBThread;
import tresp122.bthreads.AvoidCollisionsBThread;
import tresp122.bthreads.BThread;
import tresp122.bthreads.FightBThread;
import tresp122.bthreads.KeepEnergyBThread;
import tresp122.bthreads.MoveBThread;
import tresp122.bthreads.TrackBThread;
import tresp122.utilities.NotifierThread;

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
public class AviBatelRobot extends AdvancedRobot implements Coordinator{
	
	protected Lock mLock;
	protected boolean dontStop;
	
	protected PriorityQueue<BThreadAction> mActions;
	
	protected MoveBThread				mMoveBThread;
	protected FightBThread				mFightBThread;
	protected AvoidBulletsBThread		mAvoidBulletsBThread;
	protected AvoidCollisionsBThread	mAvoidCollisionsBThread;
	protected KeepEnergyBThread			mKeepEnergyBThread;
	protected TrackBThread				mTrackBThread;
	
	protected Set<BThread> mAllThreads;
	protected Set<BThread> mOnScannedRobot;
	protected Set<BThread> mOnHitByBullet;
	protected Set<BThread> mOnHitWall;
	protected Set<BThread> mOnHitRobot;
	
	public AviBatelRobot() {
		
		dontStop = true;
		
		mLock = new ReentrantLock(true);
		
		mActions = new PriorityQueue<BThreadAction>(20, new ActionsComparator());
		
		mMoveBThread = new MoveBThread(this);
		mFightBThread = new FightBThread(this);
		mAvoidBulletsBThread = new AvoidBulletsBThread(this);
		mAvoidCollisionsBThread = new AvoidCollisionsBThread(this);
		mKeepEnergyBThread = new KeepEnergyBThread(this);
		mTrackBThread = new TrackBThread(this);
		
		mAllThreads = new HashSet<BThread>();
		mOnScannedRobot = new HashSet<BThread>();		
		mOnHitByBullet = new HashSet<BThread>();
		mOnHitWall = new HashSet<BThread>();
		mOnHitRobot = new HashSet<BThread>();
		
		mAllThreads.add(mMoveBThread);		
		
		mAllThreads.add(mFightBThread);
		mOnScannedRobot.add(mFightBThread);
		
		mAllThreads.add(mAvoidBulletsBThread);
		mOnScannedRobot.add(mAvoidBulletsBThread);
		mOnHitByBullet.add(mAvoidBulletsBThread);
		
		mAllThreads.add(mAvoidCollisionsBThread);
		mOnHitWall.add(mAvoidCollisionsBThread);
		mOnHitRobot.add(mAvoidCollisionsBThread);
		
		mAllThreads.add(mKeepEnergyBThread);
		
		mAllThreads.add(mTrackBThread);
		mOnHitByBullet.add(mTrackBThread);
		mOnScannedRobot.add(mTrackBThread);
		mOnHitRobot.add(mTrackBThread);
	}

	public void run() {

		setColors(Color.black, Color.red, Color.green); // body,gun,radar
		setBulletColor(Color.yellow);
		
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		
		setTurnRadarLeft(Double.MAX_VALUE);

		new Thread(mMoveBThread).start();
		new Thread(mFightBThread).start();
		new Thread(mAvoidBulletsBThread).start();
		new Thread(mAvoidCollisionsBThread).start();
		new Thread(mKeepEnergyBThread).start();
		new Thread(mTrackBThread).start();
	
		while(dontStop){
			
			decideWhatToDo();
		}
	}

	@Override
	public void decideWhatToDo() {

		mLock.lock();
		
		if(!mActions.isEmpty()){

			BThreadAction event = mActions.poll();
			
			removeSimilarActions(event.getType());
			
			mLock.unlock();
			
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

			execute();
		}
		else {
			mLock.unlock();
			doNothing();
		}
		
		//TODO: clean the queue...
	}

	
	// Events from the Battlefield:
	
	
	private void removeSimilarActions(BThreadActionType pType) {

		Set<BThreadAction> toRemove = new HashSet<BThreadAction>();
		
		for (BThreadAction action : mActions)
			if (action.getType().equals(pType))
				toRemove.add(action);
		
		mActions.removeAll(toRemove);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		new Thread(new NotifierThread(mOnScannedRobot, event)).start();
	}

	@Override
	public void onHitWall(HitWallEvent event) {
		new Thread(new NotifierThread(mOnHitWall, event)).start();
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent pEvent) {
		new Thread(new NotifierThread(mOnHitByBullet, pEvent)).start();
	}
	
	@Override
	public void onHitRobot(HitRobotEvent pEvent) {
		new Thread(new NotifierThread(mOnHitRobot, pEvent)).start();
	}
	
	// Coordinator:
	

	@Override
	public void addAction(BThreadAction action) {

		try {
			
			mLock.lock();
			mActions.add(action);
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
		
		new Thread(new NotifierThread(mAllThreads, event)).start();
	}
}