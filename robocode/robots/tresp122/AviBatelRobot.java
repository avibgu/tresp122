package tresp122;
import robocode.*;

import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AviBatelRobot - a robot by (your name here)
 */
public class AviBatelRobot extends Robot {

	protected MoveBThread mMoveBThread;
	
	public AviBatelRobot() {

		mMoveBThread = new MoveBThread(this);
		
		// add Threads to the mailing list of each event
	}
	
	public void run() {

		setColors(Color.red, Color.black, Color.green); // body,gun,radar

		new Thread(mMoveBThread).start();
		
		while(true) {

			doNothing();
		}
		
//		mMoveBThread.stop();
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}


	public void onHitByBullet(HitByBulletEvent e) {
		turnRadarLeft(360);
	}

	public void onHitWall(HitWallEvent e) {
		turnLeft(90);
	}
	
	@Override
	public void onBulletHit(BulletHitEvent event) {
		// TODO Auto-generated method stub
		super.onBulletHit(event);
	}
	
	@Override
	public void onBulletMissed(BulletMissedEvent event) {
		// TODO Auto-generated method stub
		super.onBulletMissed(event);
	}
}
