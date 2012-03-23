package dw;
import robocode.*;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class Duck implements DuckConstants, java.io.Serializable {
  private String name = null;
  private  boolean locked = false;
  private  boolean alive = false;
  private DuckInfo duckInfo;
  private long timeSighted;
  private double duckX;
  private double duckY;
  private double xWhenSighted;
  private double yWhenSighted;
  private double headingWhenSighted;
  private double velocityWhenSighted;

 public Duck() {}


 public Duck(ScannedRobotEvent inevt, long timeCaptured, double myX,
       double myY, double myHeading,double myVelocity, boolean inalive, boolean inlocked) {
     setInfo(inevt);
     timeSighted = timeCaptured;
     xWhenSighted = myX;
     yWhenSighted = myY;
     headingWhenSighted = myHeading;
     velocityWhenSighted = myVelocity;
     setAlive(inalive);
     setLocked(inlocked);
     calcDuckXY();
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
      return duckInfo.getName();
      }

      public void setInfo(ScannedRobotEvent inevt) {


        duckInfo = new DuckInfo(inevt.getName(), inevt.getEnergy(),
         inevt.getBearing(), inevt.getDistance(), inevt.getHeading(),
          inevt.getVelocity());


      }

      private void calcDuckXY() {

        // remove the sign
       double absoluteBearing = ( headingWhenSighted + duckInfo.getBearing()) % fullTurn;
       double distanceWhenSighted = duckInfo.getDistance();
       absoluteBearing = Math.toRadians(absoluteBearing);   // convert to radians

        duckX = xWhenSighted + Math.sin(absoluteBearing) * distanceWhenSighted;
        duckY = yWhenSighted + Math.cos(absoluteBearing) * distanceWhenSighted;

       // System.out.println( duckInfo.getName() + " is at " + duckX + ", " + duckY);

      }

public double distanceToDuck(double x,double y) {

   double deltaX = duckX - x;
   double deltaY = duckY - y;
   return Math.sqrt( deltaX * deltaX + deltaY * deltaY);

 }
public double bearingToDuckRadian( double x,double y ) {
   double deltaX = duckX-x;
   double deltaY = duckY-y;
   double distanceAway = distanceToDuck(x, y);
   if (deltaX == 0) return 0;

   if (deltaX > 0) {
       if  (deltaY > 0 )
          return Math.asin( deltaX / distanceAway );
       else
        return (Math.PI - Math.asin( deltaX / distanceAway ));
     }  else  {  // deltaX < 0
       if (deltaY >0)
           return ((2 * Math.PI) - Math.asin(-deltaX/distanceAway));
       else
       	   return Math.PI + Math.asin( -deltaX / distanceAway );
       }
}

 public double bearingToDuck(double x, double y)  {
   return ( Math.toDegrees(    bearingToDuckRadian( x, y)));
 }

 public boolean equals(Object obj) {
   if (obj instanceof Duck)  {
 //    System.out.println("in obj is " + ((Duck) obj).getName() + " me is " + duckInfo.getName());
      if (((Duck) obj).getName().equals(this.duckInfo.getName()) )
        return true;
   }
//   System.out.println("equals called");
   return false;
 }

 class DuckInfo implements java.io.Serializable{
   private String name;
   private double energy;
   private double bearing;
   private double distance;
   private double heading;
   private double velocity;
   public DuckInfo() {
   }
   public DuckInfo(String inname, double inenergy, double inbearing,
       double indistance, double inheading, double invelocity) {
       setName(inname);
       setEnergy(inenergy);
       setBearing(inbearing);
       setDistance(indistance);
       setHeading(inheading);
       setVelocity(velocity);


   }

   public String getName() {
     return name;
   }
   public double getBearing(){
    return bearing;
   }
  public double getDistance(){
    return distance;
   }
  public double getHeading(){
    return heading;
   }
  public double getVelocity(){
    return velocity;
   }
  public double getEnergy(){
    return energy;
   }
  public void setName(String inname) {
  name = inname;
  }
  public void setDistance(double inval) {
  distance = inval;
  }
    public void setHeading(double inval) {
    heading = inval;
  }
  public void setVelocity(double inval) {
    velocity = inval;
  }
  public void setEnergy(double inval) {
   energy = inval;
  }
  public void setBearing(double inval) {
    bearing = inval;
  }


 }  // of class DuckInfo

  }



