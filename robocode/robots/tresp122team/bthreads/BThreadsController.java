package tresp122team.bthreads;

import java.util.HashSet;
import java.util.Set;

import robocode.Event;
import tresp122team.coordinator.AviBatelRobot;
import tresp122team.utilities.NotifierThread;

public class BThreadsController {

	protected MoveBThread mMoveBThread;
	protected FightBThread mFightBThread;
	protected AvoidBulletsBThread mAvoidBulletsBThread;
	protected AvoidCollisionsBThread mAvoidCollisionsBThread;
	protected KeepEnergyBThread mKeepEnergyBThread;
	protected TrackBThread mTrackBThread;
	protected CommunicationBThread mCommunicationBThread;

	protected KillBThread mKillBThread;
	protected StayAliveBThread mStayAliveBThread;

	protected SuccessBThread mSuccessBThread;

	protected Set<BThread> mAllBThreads;

	public BThreadsController(AviBatelRobot pRobot) {

		mAllBThreads = new HashSet<BThread>();

		mMoveBThread = new MoveBThread(pRobot);
		mFightBThread = new FightBThread(pRobot);
		mAvoidBulletsBThread = new AvoidBulletsBThread(pRobot);
		mAvoidCollisionsBThread = new AvoidCollisionsBThread(pRobot);
		mKeepEnergyBThread = new KeepEnergyBThread(pRobot);
		mTrackBThread = new TrackBThread(pRobot);
		mCommunicationBThread = new CommunicationBThread(pRobot);

		mAllBThreads.add(mMoveBThread);
		mAllBThreads.add(mFightBThread);
		mAllBThreads.add(mAvoidBulletsBThread);
		mAllBThreads.add(mAvoidCollisionsBThread);
		mAllBThreads.add(mKeepEnergyBThread);
		mAllBThreads.add(mTrackBThread);
		mAllBThreads.add(mCommunicationBThread);

		mKillBThread = new KillBThread(pRobot, getAllBThreads());
		mStayAliveBThread = new StayAliveBThread(pRobot, getAllBThreads());

		mAllBThreads.add(mKillBThread);
		mAllBThreads.add(mStayAliveBThread);

		mSuccessBThread = new SuccessBThread(pRobot, getAllBThreads());

		mAllBThreads.add(mSuccessBThread);
	}

	public void startBThreads() {
		for (BThread bThread : mAllBThreads)
			new Thread(bThread).start();
	}

	public void stopBThreads(Event event) {
		new Thread(new NotifierThread(mAllBThreads, event)).start();
	}

	public MoveBThread getMoveBThread() {
		return mMoveBThread;
	}

	public void setMoveBThread(MoveBThread pMoveBThread) {
		mMoveBThread = pMoveBThread;
	}

	public FightBThread getFightBThread() {
		return mFightBThread;
	}

	public void setFightBThread(FightBThread pFightBThread) {
		mFightBThread = pFightBThread;
	}

	public CommunicationBThread getCommunicationBThread() {
		return mCommunicationBThread;
	}

	public void setCommunicationBThread(
			CommunicationBThread pCommunicationBThread) {
		mCommunicationBThread = pCommunicationBThread;
	}

	public AvoidBulletsBThread getAvoidBulletsBThread() {
		return mAvoidBulletsBThread;
	}

	public void setAvoidBulletsBThread(AvoidBulletsBThread pAvoidBulletsBThread) {
		mAvoidBulletsBThread = pAvoidBulletsBThread;
	}

	public AvoidCollisionsBThread getAvoidCollisionsBThread() {
		return mAvoidCollisionsBThread;
	}

	public void setAvoidCollisionsBThread(
			AvoidCollisionsBThread pAvoidCollisionsBThread) {
		mAvoidCollisionsBThread = pAvoidCollisionsBThread;
	}

	public KeepEnergyBThread getKeepEnergyBThread() {
		return mKeepEnergyBThread;
	}

	public void setKeepEnergyBThread(KeepEnergyBThread pKeepEnergyBThread) {
		mKeepEnergyBThread = pKeepEnergyBThread;
	}

	public TrackBThread getTrackBThread() {
		return mTrackBThread;
	}

	public void setTrackBThread(TrackBThread pTrackBThread) {
		mTrackBThread = pTrackBThread;
	}

	public KillBThread getKillBThread() {
		return mKillBThread;
	}

	public void setKillBThread(KillBThread pKillBThread) {
		mKillBThread = pKillBThread;
	}

	public StayAliveBThread getStayAliveBThread() {
		return mStayAliveBThread;
	}

	public void setStayAliveBThread(StayAliveBThread pStayAliveBThread) {
		mStayAliveBThread = pStayAliveBThread;
	}

	public SuccessBThread getSuccessBThread() {
		return mSuccessBThread;
	}

	public void setSuccessBThread(SuccessBThread pSuccessBThread) {
		mSuccessBThread = pSuccessBThread;
	}

	public Set<BThread> getAllBThreads() {
		return mAllBThreads;
	}

	public void setAllBThreads(Set<BThread> pAllBThreads) {
		mAllBThreads = pAllBThreads;
	}
}
