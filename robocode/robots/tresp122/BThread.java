package tresp122;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public abstract class BThread implements Runnable {

	protected Lock						mLock;
	protected AviBatelRobot				mRobot;
	protected Coordinator				mCoordinator;
	protected boolean					mDontStop;
	
	protected Queue<ScannedRobotEvent>	mScannedRobots;
	protected Queue<HitWallEvent>		mHitWalls;
	
	public BThread(AviBatelRobot pRobot) {

		mLock = new ReentrantLock(true);
		mRobot = pRobot;
		mCoordinator = pRobot;
		mDontStop = true;
		
		mScannedRobots = new LinkedList<ScannedRobotEvent>();
		mHitWalls = new LinkedList<HitWallEvent>();
	}
	
	@Override
	public void run() {
		while(mDontStop){
			decideWhatActionToPerform();
		}
	}
	
	public void stop() {
		mDontStop = false;
	}
	
	public abstract void decideWhatActionToPerform();

	public abstract BThreadID getID();

	public abstract void onScannedRobot(ScannedRobotEvent event);
	
	public abstract void onHitWall(HitWallEvent event);
}
