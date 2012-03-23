package dwteam;

import robocode.*;
import java.awt.Color;
import java.io.*;
import dw.*;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class FlockSweepLeader extends dwTeamRobot
{

    protected   double curShortest = veryFar;
    protected    boolean targetLocked = false;
    protected    TeamFlock curFlock = new TeamFlock();
    protected    boolean flockGone = false;
    protected     boolean onAssignment = false;


	public void run() {
		setColors(Color.blue,Color.blue,Color.blue);


                turnRadarRight(oneRev);
                assignMission();

		while(true) {


               if(onAssignment) {
                        if (!(curTarget.isAlive()))  {





                           }
                        else {  // alive
                          if (!(curTarget.isLocked())) {
                             curTarget.setLocked(true);  // order very important
                                                          // event servicing may
                                                           // change this
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
                    else  // on Assignment
                     {
                        if (flockGone)
                           {  //  flourish
                           setTurnRight(fullTurn);
                           setTurnGunLeft(halfTurn);
                           setTurnRadarRight(fullTurn);
                           waitFor(new TurnCompleteCondition(this));
                           setTurnLeft(fullTurn);
                           setTurnGunRight(halfTurn);
                           setTurnRadarLeft(fullTurn);
                           waitFor(new TurnCompleteCondition(this));
                        }
                        else {
                          if (curTarget == null)  {
                          curTarget = curFlock.getNextUnclaimedRoaster(getX(),getY());

                          if(curTarget != null) {
                          curTarget.setClaimed(true);  // claim duck
                          curTarget.setClaimedBy(getName());
                          curTarget.setClaimedTime(getTime());



                              onAssignment = true;
                              }
                          else  {
                            if (curFlock.isFlockGone())
                              try {
                             broadcastMessage( FLOCK_GONE);
                             flockGone=true;
                                 }  catch (IOException ex) {
                                   ex.printStackTrace();
                                 }
                             }


                            turnRadarRight(fullTurn);
                            }


                        }

                      }

                 execute();
		} //while
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent evt) {
		// Don't fire on teammates
		if (isTeammate(evt.getName()))
		{
			return;
		}

               TeamDuck nuDuck = new TeamDuck(evt, getTime(), getX(), getY(), getHeading(),
                 getVelocity(), true, false, false);

               if (!curFlock.contains(nuDuck))  {
                      curFlock.add(nuDuck);
        //   out.println("added a duck... " + evt.getName());
            }




	}

        public void assignMission() {
   	     try {
                   broadcastMessage(new String(REPORT_POSITION));
		} catch (IOException e) {
                  e.printStackTrace();
		}

        }

       public void onRobotDeath(RobotDeathEvent evt) {
        if(curTarget != null) {
         if (evt.getName().equals(curTarget.getName()))  {

            curTarget.setAlive(false);

            onAssignment = false;
              curTarget = curFlock.getNextUnclaimedRoaster(getX(),getY());
                           if (curTarget != null) {
                           curTarget.setClaimed(true);  // claim duck
                          curTarget.setClaimedBy(getName());
                          curTarget.setClaimedTime(getTime());
                          onAssignment = true;
                           } else
                                if (curFlock.isFlockGone())
                              try {
                             broadcastMessage( FLOCK_GONE);
                             flockGone=true;
                                 }  catch (IOException ex) {
                                   ex.printStackTrace();
                                 }


            }
          }
       }

	public void onMessageReceived(MessageEvent e)
	{

		// Got Position of Droid, Assign Duck
		if (e.getMessage() instanceof DroidPosition)
		{

			DroidPosition p = (DroidPosition) e.getMessage();

                        TeamDuck tpDuck = curFlock.getNextUnclaimedRoaster(
                         p.getX(), p.getY());

                         if (tpDuck == null)  {
                            if (curFlock.isFlockGone())
                              try {
                             broadcastMessage( FLOCK_GONE);
                             flockGone=true;
                                 }  catch (IOException ex) {
                                   ex.printStackTrace();
                                 }
                             }
                         else {
            out.println("assigning a duck to grill: " + e.getSender() + "," +
                            tpDuck.getName());
                         try {

                          sendMessage(e.getSender(), tpDuck);
                           }  catch (IOException ex) {
                                   ex.printStackTrace();
                                 }
                          tpDuck.setClaimed(true);  // claim duck
                          tpDuck.setClaimedBy(e.getSender());
                          tpDuck.setClaimedTime(getTime());
                          }

		}
		// Got Duck Bagged Info
                if (e.getMessage() instanceof DuckBagged)
                {
			DuckBagged goodDuck = (DuckBagged) e.getMessage();
                        TeamDuck tpDuck = curFlock.findDuckWithName(goodDuck.getName());
                        tpDuck.setAlive(false);
                        out.println("got a duck gone message from " + e.getSender() +  " for " + goodDuck.getName());
		}


	}

}
