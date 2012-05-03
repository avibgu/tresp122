package tresp122.utilities;

import robocode.ScannedRobotEvent;

public class EnemyBot {

	private double mBearing;
	private double mDistance;
	private double mEnergy;
	private double mHeading;
	private String mName;
	private double mVelocity;

	public EnemyBot() {
		reset();
	}

	public void reset() {
		update(0.0, 0.0, 0.0, 0.0, "", 0.0);
	}

	public void update(ScannedRobotEvent pEvent) {
		update(pEvent.getBearing(), pEvent.getDistance(), pEvent.getEnergy(),
				pEvent.getHeading(), pEvent.getName(), pEvent.getVelocity());
	}
	
	private void update(double pBearing, double pDistance, double pEnergy,
			double pHeading, String pName, double pVelocity) {

		mBearing = pBearing;
		mDistance = pDistance;
		mEnergy = pEnergy;
		mHeading = pHeading;
		mName = pName;
		mVelocity = pVelocity;
	}

	public boolean none(){
		return mName.equals("");
	}
	
	public double getBearing() {
		return mBearing;
	}

	public void setBearing(double pBearing) {
		mBearing = pBearing;
	}

	public double getDistance() {
		return mDistance;
	}

	public void setDistance(double pDistance) {
		mDistance = pDistance;
	}

	public double getEnergy() {
		return mEnergy;
	}

	public void setEnergy(double pEnergy) {
		mEnergy = pEnergy;
	}

	public double getHeading() {
		return mHeading;
	}

	public void setHeading(double pHeading) {
		mHeading = pHeading;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public double getVelocity() {
		return mVelocity;
	}

	public void setVelocity(double pVelocity) {
		mVelocity = pVelocity;
	}
}
