package tresp122;

import robocode.ScannedRobotEvent;

public interface BThread extends Runnable {

	BThreadID getID();

	void onScannedRobot(ScannedRobotEvent event);
}
