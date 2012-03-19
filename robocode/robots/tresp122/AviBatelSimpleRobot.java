package tresp122;

import java.awt.Color;

import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class AviBatelSimpleRobot extends Robot {
	
	private boolean fighting;
	private boolean coliiding;

	public AviBatelSimpleRobot() {

		fighting = false;
		coliiding = false;
	}
	
	@Override
	public void run() {

		setColors(Color.red, Color.black, Color.green); // body,gun,radar
		
		while(true){
			
			if (!fighting && !coliiding){
				
				turnLeft(360);
				ahead(100);
			}
		}
	}
	
	
	// Attack Strategy:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		
		fighting = true;
		
		turnRight(event.getBearing());
		
		fire(3);
		
		fighting = false;
	}
	
	
	// Defense Strategy:
	
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		runAway();
	}
	
	
	// Avoid Obstacles Strategy:
	
	
	@Override
	public void onHitRobot(HitRobotEvent event) {
		if (!fighting) runAway();
	}
	
	
	public void runAway() {
		
		coliiding = true;
		
		turnLeft(60);
		back(250);
		
		coliiding = false;
	}
}
