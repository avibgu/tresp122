package tresp122;

import java.awt.Color;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.Rules;
import robocode.ScannedRobotEvent;

//API help : http://robocode.sourceforge.net/docs/robocode
//			 http://robowiki.net/wiki/Robocode/Running_from_Eclipse

public class AviBatelSimpleRobot extends Robot {

	private Lock lock;
	
	public AviBatelSimpleRobot() {		
		lock = new ReentrantLock(true);
	}
	
	@Override
	public void run() {

		// body, gun, radar
		setColors(Color.red, Color.black, Color.green);
		
		setAdjustRadarForRobotTurn(true);
		
		while(true){

			if (lock.tryLock()) {
				
				try {

					turnRadarLeft(360);
					ahead(100);
				}
				finally {

					lock.unlock();
				}
			}
		}
	}
	
	
	// Attack Strategy:
	
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		
		if (getGunHeat() == 0) {
			
			lock.lock();
			
			turnRight(event.getBearing());
			
			fire(Rules.MAX_BULLET_POWER);

			lock.unlock();
		}
	}
	
	
	// Defense Strategy:
	
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {

		if (lock.tryLock()) {
			
			try {

				if (Math.random() > 0.5)
					turnRight(event.getBearing() + 45);
				else
					turnRight(event.getBearing() - 45);
				
				back(200);
			}
			finally {

				lock.unlock();
			}
		}
	}
	
	
	// Avoid Obstacles Strategy:
	
	
	@Override
	public void onHitRobot(HitRobotEvent event) {

		if (lock.tryLock()) {
			
			try {

				turnRight(event.getBearing());
				back(300);
			}
			finally {

				lock.unlock();
			}
		}
	}
}
