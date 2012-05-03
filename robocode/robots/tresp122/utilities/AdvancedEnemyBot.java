package tresp122.utilities;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class AdvancedEnemyBot extends EnemyBot {

	private double x;
	private double y;

	public AdvancedEnemyBot() {
		reset();
	}

	@Override
	public void reset() {
		super.reset();
		update(0.0, 0.0);
	}

	private void update(double pX, double pY) {
		x = pX;
		y = pY;
	}

	public void update(ScannedRobotEvent pEvent, Robot pRobot) {

		super.update(pEvent);

		double absBearingDeg = (pRobot.getHeading() + pEvent.getBearing());

		if (absBearingDeg < 0)
			absBearingDeg += 360;

		x = pRobot.getX() + Math.sin(Math.toRadians(absBearingDeg))
				* pEvent.getDistance();

		y = pRobot.getY() + Math.cos(Math.toRadians(absBearingDeg))
				* pEvent.getDistance();
	}

	public double getFutureX(long pWhen) {
		return x + Math.sin(Math.toRadians(getHeading())) * getVelocity()
				* pWhen;
	}

	public double getFutureY(long pWhen) {
		return y + Math.cos(Math.toRadians(getHeading())) * getVelocity()
				* pWhen;
	}

	public double getX() {
		return x;
	}

	public void setX(double pX) {
		x = pX;
	}

	public double getY() {
		return y;
	}

	public void setY(double pY) {
		y = pY;
	}
}
