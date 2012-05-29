package tresp122team.event;

import robocode.Event;

public class BThreadEvent extends Event {

	private static final long serialVersionUID = 2557754158520746513L;

	private BThreadEventType mType;
	
	public BThreadEvent(BThreadEventType pType) {
		super();
		setType(pType);
	}

	public BThreadEventType getType() {
		return mType;
	}

	public void setType(BThreadEventType type) {
		mType = type;
	}
}
