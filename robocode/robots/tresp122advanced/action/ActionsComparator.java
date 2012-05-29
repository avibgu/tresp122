package tresp122advanced.action;

import java.util.Comparator;

public class ActionsComparator implements Comparator<BThreadAction> {

	@Override
	public int compare(BThreadAction a1, BThreadAction a2) {

		// we want that the biggest priority will be in the top of the queue 
		
		if (a1.getPriority() < a2.getPriority())
			return 1;
		
		if (a1.getPriority() > a2.getPriority())
			return -1;
		
		return 0;
	}

}
