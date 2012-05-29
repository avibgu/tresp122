package tresp122Advanced.coordinator;

import robocode.*;
import tresp122Advanced.action.ActionsComparator;
import tresp122Advanced.action.BThreadAction;
import tresp122Advanced.action.BThreadActionType;
import tresp122Advanced.bthreads.BThread;
import tresp122Advanced.bthreads.BThreadsController;
import tresp122Advanced.utilities.NotifierThread;
import tresp122Advanced.utilities.StopperThread;

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
	
	protected Lock							mLock;
	protected boolean						dontStop;
	
	protected PriorityQueue<BThreadAction>	mActions;
	
	protected BThreadsController			mBThreadsController;
	
	protected Set<BThread> 					mOnScannedRobot;
	protected Set<BThread> 					mOnHitByBullet;
	protected Set<BThread> 					mOnHitWall;
	protected Set<BThread> 					mOnHitRobot;
	protected Set<BThread>					mOnBulletHitBullet;
	
	protected Set<Coordinator>				mCoordinators;
	
	
	public AviBatelRobot() {
		
		dontStop = true;
		
		mLock = new ReentrantLock(true);
		
		mActions = new PriorityQueue<BThreadAction>(20, new ActionsComparator());
		
		mBThreadsController = new BThreadsController(this);
		
		initializeMailingLists();
		
		initializeCoordinators();
	}

	private void initializeMailingLists() {
		
		mOnScannedRobot = new HashSet<BThread>();		
		mOnHitByBullet = new HashSet<BThread>();
		mOnHitWall = new HashSet<BThread>();
		mOnHitRobot = new HashSet<BThread>();
		mOnBulletHitBullet = new HashSet<BThread>();
		
		mOnScannedRobot.add(mBThreadsController.getFightBThread());

		mOnScannedRobot.add(mBThreadsController.getAvoidBulletsBThread());
		mOnHitByBullet.add(mBThreadsController.getAvoidBulletsBThread());
		
		mOnHitWall.add(mBThreadsController.getAvoidCollisionsBThread());
		mOnHitRobot.add(mBThreadsController.getAvoidCollisionsBThread());
		
		mOnHitByBullet.add(mBThreadsController.getTrackBThread());
		mOnScannedRobot.add(mBThreadsController.getTrackBThread());
		mOnHitRobot.add(mBThreadsController.getTrackBThread());
		
		mOnBulletHitBullet.add(mBThreadsController.getTrackBThread());
	}

	public void initializeCoordinators() {
		
		mCoordinators = new HashSet<Coordinator>();
		
		mCoordinators.add(new Level1Coordinator(this, mBThreadsController));
		mCoordinators.add(new Level0Coordinator(this, mBThreadsController));
		
		for (Coordinator coordinator : mCoordinators)
			new Thread(coordinator).start();
	}

	public void run() {

		setColors(Color.DARK_GRAY, Color.BLACK, Color.ORANGE); // body,gun,radar
		setBulletColor(Color.getHSBColor(17, 17, 71));
		
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		
		setTurnRadarLeft(Double.MAX_VALUE);
		
		mBThreadsController.startBThreads();
		
		while(dontStop){
			
			decideWhatToDo();
		}
	}

	@Override
	public void decideWhatToDo() {

		mLock.lock();
		
		if(!mActions.isEmpty()){

			BThreadAction action = mActions.poll();
			
			removeSimilarActions(action.getType());
			
			mLock.unlock();
			
			switch (action.getType()) {
				
				case FIRE:
					setFire(action.getValue());
					break;
					
				case AHEAD:
					setAhead(action.getValue());
					break;
					
				case BACK:
					setBack(action.getValue());
					break;
					
				case TURN_LEFT:
					setTurnLeft(action.getValue());
					break;
					
				case TURN_RIGHT:
					setTurnRight(action.getValue());
					break;
					
				case TURN_GUN_LEFT:
					setTurnGunLeft(action.getValue());
					break;
					
				case TURN_GUN_RIGHT:
					setTurnGunRight(action.getValue());
					break;
					
				case TURN_RADAR_LEFT:
					setTurnRadarLeft(action.getValue());
					break;
					
				case TURN_RADAR_RIGHT:
					setTurnRadarRight(action.getValue());
					break;
			}

			execute();
		}
		else {
			mLock.unlock();
			doNothing();
		}
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
	
	@Override
	public void onBulletHitBullet(BulletHitBulletEvent pEvent) {
		new Thread(new NotifierThread(mOnBulletHitBullet, pEvent)).start();
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

		mBThreadsController.stopBThreads(event);
		
		new Thread(new StopperThread(mCoordinators)).start();
	}
}
