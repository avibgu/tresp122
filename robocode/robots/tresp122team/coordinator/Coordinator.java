package tresp122team.coordinator;

import tresp122team.action.BThreadAction;

public interface Coordinator extends Runnable{

	public void addAction(BThreadAction action);
	
	public void decideWhatToDo();

	void stop();
}
