package dw;

import robocode.*;
import java.awt.Color;

/**********************************************
 * IBM developerWorks Sample Code (c) 2002, All Right Reserved
 */

public class MultiMoveBot extends AdvancedRobot {

	final double veryFar = 9999.0;
	final double quarterTurn = 90.0;
	final double halfTurn = 180.0;
	final double threeQuarterTurn = 270.0;
	final double fullTurn = 360.0;

	boolean clockwise = true;

	public void run() {

		setColors(Color.red, Color.green, Color.red);

		centerRobot();
		setTurnRight(fullTurn);
		setAhead(veryFar);
		setTurnGunLeft(fullTurn);

		while (true) {
			waitFor(new TurnCompleteCondition(this));
			toggleDirection();
		}
	}

	private void toggleDirection() {
		if (clockwise) {
			setTurnLeft(fullTurn);
			setBack(veryFar);
			setTurnGunRight(fullTurn);
		} else {
			setTurnRight(fullTurn);
			setAhead(veryFar);
			setTurnGunLeft(fullTurn);
		}
		clockwise = !clockwise;

	}

	public void onHitWall(HitWallEvent ev) {
		centerRobot();

	}

	public void onHitRobot(HitRobotEvent ev) {
		toggleDirection();
	}

	private void centerRobot() {
		double width, height, x, y;

		height = this.getBattleFieldHeight();
		width = this.getBattleFieldWidth();
		y = this.getY();
		x = this.getX();
		// turnRight(getHeading());
		if (x > width / 2)
			turnRight(threeQuarterTurn - getHeading());
		else
			turnRight(quarterTurn - getHeading());
		ahead(Math.abs(width / 2 - x));
		if (y < height / 2)
			turnLeft(getHeading());
		else
			turnRight(halfTurn - getHeading());
		ahead(Math.abs(height / 2 - y));

	}
}
