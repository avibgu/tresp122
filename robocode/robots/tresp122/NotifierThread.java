package tresp122;

import java.util.Set;

import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.Event;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class NotifierThread implements Runnable {

	private Set<BThread> mThreads;
	private Event mEvent;

	public NotifierThread(Set<BThread> pThreads, Event pEvent) {

		mThreads = pThreads;
		mEvent = pEvent;
	}

	@Override
	public void run() {
		
		for (BThread bThread : mThreads){

			if (mEvent instanceof ScannedRobotEvent) 
				bThread.onScannedRobot((ScannedRobotEvent)mEvent);
			
			if (mEvent instanceof HitWallEvent) 
				bThread.onHitWall((HitWallEvent)mEvent);
			
			
			//TODO: add other events..
			
			
			if (mEvent instanceof WinEvent) 
				bThread.stop();
			
			if (mEvent instanceof DeathEvent) 
				bThread.stop();
			
			if (mEvent instanceof BattleEndedEvent) 
				bThread.stop();
		}
	}
}
