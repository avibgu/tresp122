package tresp122Advanced.coordinator;

import tresp122Advanced.action.BThreadAction;

public interface Coordinator extends Runnable{

	public void addAction(BThreadAction action);
	
	public void decideWhatToDo();

	void stop();
}
