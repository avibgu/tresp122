package tresp122.bthreads;

import java.util.Set;

public class BThreadsController {

	protected MoveBThread				mMoveBThread;
	protected FightBThread				mFightBThread;
	protected AvoidBulletsBThread		mAvoidBulletsBThread;
	protected AvoidCollisionsBThread	mAvoidCollisionsBThread;
	protected KeepEnergyBThread			mKeepEnergyBThread;
	protected TrackBThread				mTrackBThread;
	
	protected Set<BThread> mAllBThreads;
	
	public BThreadsController() {
		// TODO Auto-generated constructor stub
	}
}
