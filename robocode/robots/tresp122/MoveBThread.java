package tresp122;

import robocode.HitWallEvent;
import robocode.MoveCompleteCondition;
import robocode.ScannedRobotEvent;

public class MoveBThread extends BThread {

	public MoveBThread(AviBatelRobot pRobot) {
		super(pRobot);
	}

	@Override
	public void decideWhatToDo() {

		if (mLock.tryLock()) {

			try {
				
				if (mHitWalls.isEmpty()){
					
					mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.AHEAD, 0, 200));
					
					if (Math.random() > 0.5)
						mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_RIGHT, 0, 90));
					
					else
						mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_LEFT, 0, 90));
				}
				else{
					
					double degree = mHitWalls.poll().getBearing() - 100;
					
					mRobot.addEvent(getID(), new BThreadEvent(BThreadEventType.TURN_RIGHT, 5, degree));
				}
				
				waitUntilTheRobotIsNotMoving();
			}
			finally {
				mLock.unlock();
			}
		}
	}

	protected void waitUntilTheRobotIsNotMoving() {
		
		while(!new MoveCompleteCondition(mRobot).test() && mDontStop){
			
			try {
				
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {

		mLock.lock();
		
		mHitWalls.add(event);
		
		mLock.unlock();
	}
	
	@Override
	public BThreadID getID() {
		return BThreadID.MOVE;
	}

	
	// ignored events:
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {}

}
