package dw;
import robocode.*;
import java.awt.Color;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class DuckSeekerBot extends AdvancedRobot implements DuckConstants
{
        boolean targetLocked = false;
        Target curTarget = null;

	public void run() {


		setColors(Color.red,Color.green,Color.red);
                setTurnRadarRight(oneRev);

		while(true) {
                    if (curTarget != null) {
                     if (curTarget.isAlive() && curTarget.isLocked()) {
                       fire(2);
                      }
                    }
                     execute();
		}
	}
       public void onScannedRobot(ScannedRobotEvent evt) {

         stop();
         turnRight(evt.getBearing());
         if (evt.getDistance() > safeDistance)
           ahead(evt.getDistance() - safeDistance);
         if(curTarget != null) {
                     if (curTarget.getName().equals(evt.getName()))
                          return;
         }
         curTarget = new Target(evt.getName(), true, true);

       }

       public void onRobotDeath(RobotDeathEvent evt) {
         if (evt.getName().equals(curTarget.getName()))
            curTarget.setAlive(false);
         setTurnRadarRight(oneRev);
       }

       public void onWinEvent (WinEvent evt) {
              setTurnRight(veryFar);
              setTurnRadarLeft(veryFar);
         }

class Target {
  private String name = null;
  private  boolean locked = false;
  private  boolean alive = false;
  public Target(String inname, boolean inalive, boolean inlocked) {
     setName(inname);
     setAlive(inalive);
     setLocked(inlocked);
  }
  public boolean isAlive() {
     return alive;
  }
  public boolean isLocked() {
     return locked;
  }
  public void setAlive( boolean inalive) {
     alive = inalive;
     }
     public void setLocked( boolean inlocked) {
      locked = inlocked;
      }
      public String getName() {
      return name;
      }
      public void setName(String inName) {
      name = inName;
      }
      }

}
