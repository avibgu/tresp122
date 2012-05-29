package tresp122team.utilities;

import java.util.Set;

import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.Event;
import robocode.WinEvent;
import tresp122team.bthreads.BThread;

public class NotifierThread implements Runnable {

	private Set<BThread>	mThreads;
	private Event			mEvent;

	public NotifierThread(Set<BThread> pThreads, Event pEvent) {

		mThreads = pThreads;
		mEvent = pEvent;
	}

	@Override
	public void run() {
		
		for (BThread bThread : mThreads){
						
			if (mEvent instanceof WinEvent) 
				bThread.stop();
			
			else if (mEvent instanceof DeathEvent) 
				bThread.stop();
			
			else if (mEvent instanceof BattleEndedEvent) 
				bThread.stop();
			
			else
				bThread.notifyAboutEvent(mEvent);
		}
	}
}
