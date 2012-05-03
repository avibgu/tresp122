package tresp122.bthreads;

import java.util.LinkedList;
import java.util.Queue;

import robocode.Event;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import tresp122.action.BThreadAction;
import tresp122.action.BThreadActionType;
import tresp122.coordinator.AviBatelRobot;

public class AvoidCollisionsBThread extends BThread {

	protected Queue<HitWallEvent> mHitWall;
	protected Queue<HitRobotEvent> mHitRobot;

	public AvoidCollisionsBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mHitWall= new LinkedList<HitWallEvent>();
		mHitRobot = new LinkedList<HitRobotEvent>();
		
		mPriority = 50;
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mLock.tryLock()) {

			if (!mHitWall.isEmpty()) {
				
				//TODO: test it..
				
				double degree = mHitWall.poll().getBearing() - 100;
				
				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_RIGHT, mPriority + 1, degree));
				mCoordinator.addAction(new BThreadAction(BThreadActionType.AHEAD, mPriority, 150));
			}
			
			else if (!mHitRobot.isEmpty()) {
				
				//TODO: test it..
				
				double degree = mRobot.getHeading() - mRobot.getGunHeading() + mHitRobot.poll().getBearing();
				
				mLock.unlock();

				mCoordinator.addAction(new BThreadAction(BThreadActionType.TURN_RIGHT, mPriority + 1, degree));
				mCoordinator.addAction(new BThreadAction(BThreadActionType.BACK, mPriority, 250));
			}
			
			else
				mLock.unlock();
		}		
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof HitWallEvent) {

			mLock.lock();

			mHitWall.add((HitWallEvent) pEvent);

			mLock.unlock();
		}
		
		else if (pEvent instanceof HitRobotEvent) {

			mLock.lock();

			mHitRobot.add((HitRobotEvent) pEvent);

			mLock.unlock();
		}
	}
}
