package tresp122;

public interface Coordinator {

	public void addAction(BThreadID id, BThreadAction action);
	
	public void decideWhatToDo();
}
