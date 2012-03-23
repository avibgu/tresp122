package dwteam;
import robocode.*;
import dw.*;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */


class TeamDuck extends Duck implements java.io.Serializable {
 private boolean claimed = false;
 private String claimedBy = null;
 private long claimedTime = 0;

 public TeamDuck() {}

 public TeamDuck(ScannedRobotEvent inevt, long timeCaptured, double myX,
       double myY, double myHeading,double myVelocity, boolean inalive,
       boolean inlocked,  boolean inclaimed) {

  super(inevt, timeCaptured, myX, myY, myHeading, myVelocity, inalive,
       inlocked);
    setClaimed(inclaimed);
  }
 public boolean isClaimed() {
   return claimed;
   }
 public void setClaimed(boolean inclaim) {
   claimed = inclaim;
 }
 public String getClaimedBy() {
   return claimedBy;
 }
 public void setClaimedBy(String inclaimedBy) {
   claimedBy = inclaimedBy;
 }
 public long getClaimedTime() {
   return claimedTime;
   }
  public void setClaimedTime(long intime) {
  claimedTime = intime;
   }
  }



