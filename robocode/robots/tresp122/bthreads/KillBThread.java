package tresp122.bthreads;

import java.util.Set;

import robocode.Event;

import tresp122.coordinator.AviBatelRobot;
import tresp122.event.BThreadEvent;
import tresp122.event.BThreadEventType;

public class KillBThread extends BThread {

	public KillBThread(AviBatelRobot pRobot, Set<BThread> pBThreadsToRegister) {
		super(pRobot, pBThreadsToRegister);
	}

	@Override
	public void decideWhichActionToPerform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAboutEvent(Event pEvent) {
		
		if (pEvent instanceof BThreadEvent) {

			BThreadEvent event = (BThreadEvent)pEvent;
			
			if (event.getType() == BThreadEventType.WE_ARE_STRONG){
			
				mLock.lock();
	
				//...
	
				mLock.unlock();
			}
			
			else if (event.getType() == BThreadEventType.WE_ARE_WEAK){
				
				mLock.lock();
	
				//...
	
				mLock.unlock();
			}
		}
	}

}
