package tresp122team.bthreads;

import java.util.LinkedList;
import java.util.Queue;

import robocode.Event;
import robocode.MessageEvent;

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

					String messageBody = event.getMessage().toString();

					if (messageBody.equals("ENEMY_IS_DEAD"))
						super.notifyToMailingList(new BThreadEvent(
								BThreadEventType.ENEMY_IS_DEAD, ""));

					else
						super.notifyToMailingList(new BThreadEvent(
								BThreadEventType.NEW_TARGET, messageBody));
				}

				else
					mLock.unlock();
			}
		}
	}

	@Override
	public void notifyAboutEvent(Event pEvent) {

		if (pEvent instanceof MessageEvent) {

			mLock.lock();

			mMessageEvents.add((MessageEvent) pEvent);

			mLock.unlock();
		}
	}

}
