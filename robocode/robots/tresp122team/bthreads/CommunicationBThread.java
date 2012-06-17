package tresp122team.bthreads;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import robocode.Event;
import robocode.HitByBulletEvent;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;

import tresp122team.action.BThreadAction;
import tresp122team.action.BThreadActionType;
import tresp122team.coordinator.AviBatelRobot;
import tresp122team.event.BThreadEvent;
import tresp122team.event.BThreadEventType;

public class CommunicationBThread extends BThread {

	protected Queue<MessageEvent> mMessageEvents;

	public CommunicationBThread(AviBatelRobot pRobot) {

		super(pRobot);

		mMessageEvents = new LinkedList<MessageEvent>();
	}

	@Override
	public void decideWhichActionToPerform() {

		if (mRobot.isLeader()) {

			try {
				Thread.sleep(1000);
			}

			catch (InterruptedException e) {
			}
		}

		else {

			if (mLock.tryLock()) {

				if (!mMessageEvents.isEmpty()) {

					MessageEvent event = mMessageEvents.poll();

					mLock.unlock();

					super.notifyToMailingList(new BThreadEvent(
							BThreadEventType.NEW_TARGET, event.getMessage()
									.toString()));
				}

				else
					mLock.unlock();
			}
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (mRobot.isLeader())
			return;

		if (pEvent instanceof MessageEvent) {

			mLock.lock();

			mMessageEvents.add((MessageEvent) pEvent);

			mLock.unlock();
		}
	}

}
