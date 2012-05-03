package tresp122.coordinator;

import tresp122.action.BThreadAction;

public interface Coordinator extends Runnable{

	public void addAction(BThreadAction action);
	
	public void decideWhatToDo();

	void stop();
}
