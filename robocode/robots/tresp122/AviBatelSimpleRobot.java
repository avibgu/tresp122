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
				
				ahead(100);
				turnLeft(360);
			}
		}
	}
	
	
	// Attack Strategy:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {

		fighting = true;

		double delta = event.getBearing();
		
		if (delta < 0)
			turnLeft(delta);
		
		else turnRight(delta);
		
		fire(3);
		
		fighting = false;
	}

	@Override
	public void onBulletHit(BulletHitEvent event) {
		back(150);
	}
	
	@Override
	public void onBulletMissed(BulletMissedEvent event) {
		// TODO Auto-generated method stub
		super.onBulletMissed(event);
	}
	
	
	// Defense Strategy:
	
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		// TODO Auto-generated method stub
		super.onHitByBullet(event);
	}
	
	
	// Avoid Obstacles Strategy:
	
	
	@Override
	public void onHitRobot(HitRobotEvent event) {
		
		coliiding = true;
		
		back(100);
		
		coliiding = false;
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {

		coliiding = true;
		
		turnLeft(90);
		
		coliiding = false;
	}
}
