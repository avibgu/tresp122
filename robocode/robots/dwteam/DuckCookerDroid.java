package dwteam;
import robocode.*;
import dw.*;
import java.io.*;
import java.awt.Color;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class DuckCookerDroid extends dwTeamRobot implements Droid
{

       protected boolean targetLocked = false;
       protected TeamFlock curFlock = new TeamFlock();
       protected  boolean flockGone = false;
       protected  boolean onAssignment = false;
       protected String ourLeaderTheGreat = null;


	public void run() {

		System.out.println("DuckCookerDroid ready.");
                setColors(Color.blue,Color.blue,Color.blue);

		while(true) {

                if (onAssignment) {
                      if (!(curTarget.isAlive()))  {

                           }
                        else {  // alive
                          if (!(curTarget.isLocked())) {
                           curTarget.setLocked(true); // order important
                             double tpBear = curTarget.bearingToDuck(getX(), getY());
                             //out.println("bearing to target is " + tpBear);
                             // turnLeft(getHeading());
                             turnRight(minimizeTurning(tpBear - getHeading()));
                             double tpDist = curTarget.distanceToDuck(getX(), getY());
                             if (tpDist > safeDistance)
                               ahead(tpDist - safeDistance);


                              }
                          else  //curTarget.isLocked()
                             fire(3);
                          }  // is Locked
                      } // alive
                    else  // curTarget != null
                     {
                        if (flockGone)
                           {  //  flourish
                           setTurnRight(fullTurn);
                           setTurnGunLeft(halfTurn);
                           waitFor(new TurnCompleteCondition(this));
                           setTurnLeft(fullTurn);
                           setTurnGunRight(halfTurn);
                           waitFor(new TurnCompleteCondition(this));



                        }

                      }
                  execute();

		} //while




	}

	/**
	 * onMessageReceived: What to do when our leader sends a message
	 */
	public void onMessageReceived(MessageEvent e)
	  {
            Object msg = e.getMessage();
            if (msg instanceof String) {
                if (((String) msg).equals(REPORT_POSITION))
                 try {
   System.out.println(getName() + "got a report position request");
                  ourLeaderTheGreat = e.getSender();
                  sendMessage(e.getSender(), new DroidPosition(getX(), getY()));
                  }
                  catch (IOException ex) {
                   ex.printStackTrace();
                  }
                if (((String) msg).equals(FLOCK_GONE))
                  flockGone = true;
              } // if instanceof String



		if (msg instanceof TeamDuck)
		{
                 onAssignment = true;
                 curTarget = (TeamDuck) msg;

                    out.println("told of a duck to grill: " +
                            curTarget.getName());

		}
	}
       public void onRobotDeath(RobotDeathEvent evt) {
         if (evt.getName().equals(curTarget.getName()))  {
            curTarget.setAlive(false);
            try {
             sendMessage(ourLeaderTheGreat, new DuckBagged(evt.getName()));
             onAssignment = false;
             if (!flockGone) {
               sendMessage(ourLeaderTheGreat, new DroidPosition(getX(), getY()));
             }
            }  catch (IOException ex) {
             ex.printStackTrace();
            }

            }
       }


}