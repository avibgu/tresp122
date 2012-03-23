package dw;
import robocode.*;
import java.util.Vector;
import java.util.Iterator;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class Flock extends Vector {
 public Flock() {
 }

 public Duck getNextRoaster(double x,double y) {
   Iterator it = this.iterator();
   double curMin = 9999.0;
   Duck curDuck = null;
   while (it.hasNext())  {
     Duck tpDuck = (Duck) it.next();
     double tpDist = tpDuck.distanceToDuck(x,y);
     // check for dead ducks
     if ((tpDuck.isAlive ()  && (tpDist < curMin))) {
        curMin   = tpDist;
        curDuck = tpDuck;
     } // of if
   } // of while
   //  if (curDuck != null)
   //   System.out.println("next roaster is " + curDuck.getName());
   return curDuck;
   }
}