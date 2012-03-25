package tresp122;

import java.util.Comparator;

public class EventsComparator implements Comparator<BThreadEvent> {

	@Override
	public int compare(BThreadEvent o1, BThreadEvent o2) {

		// we want that the biggest priority will be in the top of the queue 
		
		if (o1.getPriority() < o2.getPriority())
			return 1;
		
		if (o1.getPriority() > o2.getPriority())
			return -1;
		
		return 0;
	}

}
