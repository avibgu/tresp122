package digmi.avi;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

public class MyFirstRobot extends Robot {
	
	public boolean isFighting = false;
	
	public void run() {

		
		
		
		
		setColors(Color.black, Color.red, Color.green); // body,gun,radar

		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);		
		setAdjustRadarForGunTurn(true);
		
		while(true) {

			turnRadarRight(45);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		scan();
		
		turnRadarLeft(45);
		
		double delta = getGunHeading() - getRadarHeading();
		
		// if negative - go right..
		if (delta < 0)
			turnGunRight(delta);
		
		else turnGunLeft(delta);

		fire(1);
	}


	public void onHitByBullet(HitByBulletEvent e) {
		scan();
	}

	public void onHitWall(HitWallEvent e) {
		turnRight(90);
	}	
}
