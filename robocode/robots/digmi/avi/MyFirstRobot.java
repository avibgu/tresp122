package digmi.avi;

import robocode.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

public class MyFirstRobot extends AdvancedRobot {

	// This will be used to tell our threads to terminate. To do this, we must
	// be able to modify its value, so a simple Boolean object will not do.
	// It will also be used to make our threads wait until the next round. For
	// this, it must inherit from Object to get at its notifyAll() method.
	Boolean token = true;

	// Number of threads we have spawned.
	int threadCount = 0;

	@Override
	public void run() {

		// Get the radar spinning so we can get ScannedRobotEvents
		setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

		while (true) {
			out.println("Robot time: " + getTime());

			if (threadCount < 5) {
				final long spawnTime = getTime();

				// Quick and dirty code to create a new thread. If you don't
				// already know how to do this, you probably haven't learned all
				// the intricacies involved in multithreading on the JVM yet.

				new Thread(new Runnable() {

					int runCount = 0;

					public void run() {

						synchronized (token) {
							while (token == true && runCount < 100) {
								System.out
										.printf("\tHi from Thread#%d (current time: %d). Repeat count: %d\n",
												spawnTime, getTime(),
												++runCount);
								try {
									// Sleep until re-awakened in next turn
									token.wait();
								} catch (InterruptedException e) {
								}
							}
						}
					}

				}).start();

				threadCount++;

			}

			execute();
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		synchronized (token) {
			// Wake up threads! It's a new turn!
			token.notifyAll();
		}
	}

	// The following events MUST be overriden if you plan to multithread your
	// robot.
	// Failure to do so can cause exceptions and general annoyance.

	@Override
	public void onWin(WinEvent e) {

		// Politely tell our threads to stop because the round is over
		synchronized (token) {
			token = false;
		}
	}

	@Override
	public void onDeath(DeathEvent e) {

		// Politely tell our threads to stop because the round is over
		synchronized (token) {
			token = false;
		}
	}

	@Override
	public void onBattleEnded(BattleEndedEvent e) {

		// Politely tell our threads to stop because the battle has been ended.
		// This gets called whether the battle was aborted or ended naturally,
		// so beware of duplication with onDeath/onWin (if that is important to
		// you).
		synchronized (token) {
			token = false;
		}
	}
}
