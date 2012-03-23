package dw;
import robocode.*;
import java.awt.Color;
/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class FlockRoasterBot extends AdvancedRobot implements DuckConstants
{

        double curShortest = veryFar;

        boolean targetLocked = false;
        Flock curFlock = new Flock();
        Duck curTarget = null;
        boolean flockGone = false;

	public void run() {


		setColors(Color.red,Color.green,Color.red);

                turnRadarRight(oneRev);

		while(true) {



                  if (curTarget != null) {
                        if (!(curTarget.isAlive()))  {
                           curTarget = curFlock.getNextRoaster(getX(),getY());
                           if (curTarget == null)
                             flockGone = true;
                           }
                        else {  // alive
                          if (!(curTarget.isLocked())) {
                             double tpBear = curTarget.bearingToDuck(getX(), getY());
                             //out.println("bearing to target is " + tpBear);
                             // turnLeft(getHeading());
                             turnRight(minimizeTurning(tpBear - getHeading()));
                             double tpDist = curTarget.distanceToDuck(getX(), getY());
                             if (tpDist > safeDistance)
                               ahead(tpDist - safeDistance);

                              curTarget.setLocked(true);
                              }
                          else  //curTarget.isLocked()
                             fire(3);
                          }  // is Locked
                      } // alive
                    else  // curTarget != null
                     {
                        if (!flockGone)
                           curTarget = curFlock.getNextRoaster(getX(),getY());
                        else {  //  flourish
                           setTurnRight(fullTurn);
                           setTurnGunLeft(halfTurn);
                           setTurnRadarRight(fullTurn);
                           waitFor(new TurnCompleteCondition(this));
                           setTurnLeft(fullTurn);
                           setTurnGunRight(halfTurn);
                           setTurnRadarLeft(fullTurn);
                           waitFor(new TurnCompleteCondition(this));



                        }

                      }


		} //while
	}
      private double minimizeTurning(double in) {
        if (in > halfTurn) {
         return  -1 * (fullTurn - in);
        }
        else
          return in;

      }
       public void onScannedRobot(ScannedRobotEvent evt) {
        Duck nuDuck = new Duck(evt, getTime(), getX(), getY(), getHeading(),
        getVelocity(), true, false);

        if (!curFlock.contains(nuDuck))  {
           curFlock.add(nuDuck);
        //   out.println("added a duck... " + evt.getName());
            }
       }

       public void onRobotDeath(RobotDeathEvent evt) {
         if (evt.getName().equals(curTarget.getName()))
            curTarget.setAlive(false);
       }





}
