package dwteam;
import robocode.*;
import java.util.Vector;
import java.util.Iterator;
/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */


public class TeamFlock extends Vector {
 public TeamFlock() {
 }

 public boolean isFlockGone() {
   boolean isgone = true;
   Iterator it = this.iterator();

   while (it.hasNext())  {
     TeamDuck tpDuck = (TeamDuck) it.next();
     if (tpDuck.isAlive()) {
       isgone = false;
       break;
       }
   }
   return isgone;

 }
 public TeamDuck findDuckWithName(String inname) {
  Iterator it = this.iterator();
   TeamDuck curDuck = null;
   while (it.hasNext())  {
     TeamDuck tpDuck = (TeamDuck) it.next();
     if (tpDuck.getName().equals(inname)) {
       curDuck = tpDuck;
       break;
       }
  }
  return curDuck;
 }
 public synchronized TeamDuck getNextUnclaimedRoaster(double x,double y) {
   Iterator it = this.iterator();
   double curMin = 9999.0;
   TeamDuck curDuck = null;
   while (it.hasNext())  {
     TeamDuck tpDuck = (TeamDuck) it.next();
     double tpDist = tpDuck.distanceToDuck(x,y);
     // check for dead ducks
     if ((tpDuck.isAlive ()  && (tpDist < curMin)  && (!tpDuck.isClaimed()) )) {
        curMin   = tpDist;
        curDuck = tpDuck;
        break;
     } // of if
   } // of while
   //  if (curDuck != null)
   //   System.out.println("next roaster is " + curDuck.getName());
   return curDuck;
   }
}