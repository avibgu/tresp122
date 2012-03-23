package dwteam;

import robocode.*;
import dw.DuckConstants;
/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */



public class dwTeamRobot extends TeamRobot implements DuckConstants, TeamCommands {

     protected   TeamDuck curTarget = null;

     protected double minimizeTurning(double in) {
        if (in > halfTurn) {
         return  -1 * (fullTurn - in);
        }
        else
          return in;

      }
         protected void pickRandAvoidance()  {
           double tpRnd = Math.random() * 10;
           int rndInt = (int) Math.ceil(tpRnd);
           tpRnd = tpRnd % 3;
           switch (rndInt) {
             case 0:  back(100);
                      break;
             case 1:  back(10);
                      turnRight(90);
                      ahead(50);
                      break;
             case 2: back(10);
                     turnLeft(90);
                     ahead(50);
           }
        }

                public void onHitRobot(HitRobotEvent e) {

                   pickRandAvoidance();
                        if (curTarget != null)
                           curTarget.setLocked(false);

        }

        public void onBulletHit(BulletHitEvent evt) {
        if (isTeammate(evt.getName()))   // don't fire twice at team mate
         {
          back(50);
          curTarget.setLocked(false);
          }

        }

	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90 - e.getBearing());
                ahead(100);
                if (curTarget != null)
                  curTarget.setLocked(false);
	}

  public dwTeamRobot() {
  }
}