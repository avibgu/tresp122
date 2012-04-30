package tresp122.bthreads;

import java.util.LinkedList;
import java.util.Queue;

import robocode.Event;
import robocode.HitWallEvent;
import robocode.MoveCompleteCondition;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class MoveBThread extends BThread {

	protected Queue<HitWallEvent> mHitWalls;
	
	public MoveBThread(AviBatelRobot pRobot) {
		
		super(pRobot);
		
		mHitWalls = new LinkedList<HitWallEvent>();
	}

	@Override
	public void decideWhatActionToPerform() {

		if (mLock.tryLock()) {

			if (mHitWalls.isEmpty()){
				
				mLock.unlock();
				
				if (Math.random() > 0.5)
					mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_RIGHT, 0, 90));
				
				else
					mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_LEFT, 0, 90));
				
				mCoordinator.addAction(new BThreadAction(BThreadActionType.AHEAD, 0, 150));
			}
			else{

				double degree = mHitWalls.poll().getBearing() - 100;
				
				mLock.unlock();
				
				mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_RIGHT, 6, degree));
				mCoordinator.addAction(new BThreadAction(BThreadActionType.AHEAD, 5, 150));
			}
		}
		
		waitUntilTheRobotIsNotMoving();
	}

	protected void waitUntilTheRobotIsNotMoving() {
		
		try {
			
			while(!new MoveCompleteCondition(mRobot).test() && mDontStop){
				
				try {
					
					Thread.sleep(200);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		catch(Exception e){}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof HitWallEvent){
		
			mLock.lock();
			
			mHitWalls.add((HitWallEvent) pEvent);
			
			mLock.unlock();
		}
	}
}
