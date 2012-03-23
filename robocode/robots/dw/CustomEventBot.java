package dw;
import robocode.*;
import java.awt.Color;

/**********************************************
 * IBM developerWorks Sample Code
 * (c) 2002, All Right Reserved
 */

public class CustomEventBot extends AdvancedRobot
{

	final double veryFar = 9999.0;
        final double quarterTurn = 90.0;
        final double halfTurn = 180.0;
        final double threeQuarterTurn = 270.0;
        final double fullTurn = 360;

	public void run() {

        setColors(Color.red,Color.blue,Color.green);
        turnLeft(getHeading());
        turnRight(halfTurn);


          addCustomEvent(
			new Condition("LeftLimit") {
			  public boolean test() {

				  return (getHeading() <= quarterTurn);
				};
			}
		);

          addCustomEvent(
			new Condition("RightLimit") {
			  public boolean test() {
				  return (getHeading() >= threeQuarterTurn);
				};
			}
		);



                    setTurnRight(fullTurn);
                    setTurnGunLeft(fullTurn);
		while(true) {
			execute();
		}
	}


  public void onCustomEvent(CustomEvent ev) {
         Condition cd = ev.getCondition();
         System.out.println("event with " + cd.getName());
         if (cd.getName().equals("RightLimit")) {
            setTurnLeft(fullTurn);
            setTurnGunRight(fullTurn);
            }
            else {
             setTurnRight(fullTurn);
             setTurnGunLeft(fullTurn);
            }
        }
}
