package tresp122team.coordinator;

import robocode.*;
import tresp122team.action.ActionsComparator;
import tresp122team.action.BThreadAction;
import tresp122team.action.BThreadActionType;
import tresp122team.bthreads.BThread;
import tresp122team.bthreads.BThreadsController;
import tresp122team.utilities.NotifierThread;
import tresp122team.utilities.StopperThread;

import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AviBatelRobot - a robot by (your name here)
 */
public class AviBatelRobot extends TeamRobot implements Coordinator{

	protected Lock							mLock;
	protected boolean						dontStop;

	protected PriorityQueue<BThreadAction>	mActions;

	protected BThreadsController			mBThreadsController;

	protected Set<BThread> 					mOnScannedRobot;
	protected Set<BThread> 					mOnHitByBullet;
	protected Set<BThread> 					mOnHitWall;
	protected Set<BThread> 					mOnHitRobot;
	protected Set<BThread>					mOnBulletHit;
	protected Set<BThread>					mOnMessageReceived;
	protected Set<BThread>					mRobotDeathEvent;

	protected Set<Coordinator>				mCoordinators;

	protected boolean						mIsLeader;


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
		mOnBulletHit = new HashSet<BThread>();
		mOnMessageReceived = new HashSet<BThread>();
		mRobotDeathEvent = new HashSet<BThread>();

		mOnScannedRobot.add(mBThreadsController.getFightBThread());

		mOnScannedRobot.add(mBThreadsController.getAvoidBulletsBThread());
		mOnHitByBullet.add(mBThreadsController.getAvoidBulletsBThread());

		mOnHitWall.add(mBThreadsController.getAvoidCollisionsBThread());
		mOnHitRobot.add(mBThreadsController.getAvoidCollisionsBThread());

		mOnHitByBullet.add(mBThreadsController.getTrackBThread());
		mOnScannedRobot.add(mBThreadsController.getTrackBThread());
		mOnHitRobot.add(mBThreadsController.getTrackBThread());
		mRobotDeathEvent.add(mBThreadsController.getTrackBThread());

		mOnBulletHit.add(mBThreadsController.getTrackBThread());

		mOnMessageReceived.add(mBThreadsController.getCommunicationBThread());
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

		checkIfLeader();

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

				case SEND_MESSAGE:
					try {
						broadcastMessage(action.getMessage());
					} catch (IOException e) {
						e.printStackTrace();
					}
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

		// Teammate
		if (isTeammate(event.getName()))
			return;

		// Enemy
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
	public void onBulletHit(BulletHitEvent pEvent) {
		new Thread(new NotifierThread(mOnBulletHit, pEvent)).start();
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

	// Team

	public int getBotNumber(String name) {

		String n = "0";

		int low = name.indexOf("(") + 1;

		int hi = name.lastIndexOf(")");

		if (low >= 0 && hi >= 0)
			n = name.substring(low, hi);

		return Integer.parseInt(n);
	}

	public void checkIfLeader() {
		mIsLeader = (getBotNumber(getName()) == 1);
	}

	public boolean isLeader() {
		return mIsLeader;
	}

	@Override
	public void onMessageReceived(MessageEvent pEvent) {
		
		if(pEvent.getSender().equals(getName()))
			return;
		
		new Thread(new NotifierThread(mOnMessageReceived, pEvent)).start();
	}

	@Override
	public void onRobotDeath(RobotDeathEvent pEvent) {
		
		// Enemy		
		if (!isTeammate(((RobotDeathEvent) pEvent).getName()))
			new Thread(new NotifierThread(mRobotDeathEvent, pEvent)).start();
	}
}
