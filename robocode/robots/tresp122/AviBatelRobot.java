package tresp122;

import robocode.*;

import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AviBatelRobot - a robot by (your name here)
 */
public class AviBatelRobot extends AdvancedRobot {

	protected MoveBThread mMoveBThread;

	public AviBatelRobot() {

		mMoveBThread = new MoveBThread(this);

		// add Threads to the mailing list of each event
	}

	public void run() {

		setColors(Color.red, Color.black, Color.green); // body,gun,radar

	//	setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
		
		new Thread(mMoveBThread).start();
	}

//	public void onScannedRobot(ScannedRobotEvent e) {
//		setFire(1);
//	}
//
//	public void onHitByBullet(HitByBulletEvent e) {
//		setTurnLeft(90);
//	}
//
//	public void onHitWall(HitWallEvent e) {
//		setTurnLeft(90);
//	}
	
	
	// basic moves: (should be removed..)
	
	
	@Override
	public void ahead(double distance) {
		synchronized (this) {
			super.ahead(distance);
		}
	}
	
	@Override
	public void turnLeft(double degrees) {
		synchronized (this) {
			super.turnLeft(degrees);
		}
	}

	@Override
	public void fire(double power) {
		synchronized (this) {
			super.fire(power);
		}
	}
	
	
	
	// Handle multi-threading:
	
	
	@Override
	public void onWin(WinEvent event) {
		stopAllThreads();
		super.onWin(event);
	}
	
	@Override
	public void onDeath(DeathEvent event) {
		stopAllThreads();
		super.onDeath(event);
	}
	
	@Override
	public void onBattleEnded(BattleEndedEvent event) {

		stopAllThreads();
		super.onBattleEnded(event);
	}

	private void stopAllThreads() {
		mMoveBThread.stop();
	}
}
