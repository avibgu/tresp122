package tresp122;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.Rules;
import robocode.ScannedRobotEvent;

//API help : http://robocode.sourceforge.net/docs/robocode
//			 http://robowiki.net/wiki/Robocode/Running_from_Eclipse

public class AviBatelSimpleRobot extends Robot {
	
	private boolean fighting;
	private boolean coliding;

	public AviBatelSimpleRobot() {

		fighting = false;
		coliding = false;
	}
	
	@Override
	public void run() {

		// body, gun, radar
		setColors(Color.red, Color.black, Color.green);
		
		setAdjustRadarForRobotTurn(true);
		
		while(true){
			
			if (!fighting && !coliding){
				
				turnRadarLeft(360);
				ahead(100);
			}
		}
	}
	
	
	// Attack Strategy:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		
		if (getGunHeat() == 0 && !coliding) {
			
			fighting = true;
			
			turnRight(event.getBearing());
			
			fire(Rules.MAX_BULLET_POWER);

			fighting = false;
		}
	}
	
	
	// Defense Strategy:
	
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {

		if (!fighting && !coliding){
			
			coliding = true;
			
			if (Math.random() > 0.5)
				turnRight(event.getBearing() + 45);
			else
				turnRight(event.getBearing() - 45);
			
			back(200);
			
			coliding = false;
		}
	}
	
	
	// Avoid Obstacles Strategy:
	
	
	@Override
	public void onHitRobot(HitRobotEvent event) {

		if (!fighting && !coliding){
			
			coliding = true;
			
			turnRight(event.getBearing());
			back(300);
			
			coliding = false;
		}
	}
}
