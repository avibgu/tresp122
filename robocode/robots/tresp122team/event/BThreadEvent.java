package tresp122team.event;

import robocode.Event;

public class BThreadEvent extends Event {

	private static final long serialVersionUID = 2557754158520746513L;

	private BThreadEventType	mType;
	private String				mMessage;

	public BThreadEvent(BThreadEventType pType) {
		this(pType, "");
	}

	public BThreadEvent(BThreadEventType pType, String pMessage) {
		super();
		setType(pType);
		setMessage(pMessage);
	}

	public BThreadEventType getType() {
		return mType;
	}

	public void setType(BThreadEventType type) {
		mType = type;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String pMessage) {
		mMessage = pMessage;
	}
}
