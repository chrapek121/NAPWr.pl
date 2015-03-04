package pl.wroc.pwr.na.tools;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.wroc.pwr.na.database.objects.EventObject;

public class EventPreparator {

	public List<EventObject> prepareEvents(List<EventObject> eventList) {
		Collections.sort(eventList, new EventListDateComparator());
		return eventList;
	}

	public class EventListDateComparator implements Comparator<EventObject> {
		@Override
		public int compare(EventObject o1, EventObject o2) {
			return o1.wydarzenieDataPoczatek
					.compareTo(o2.wydarzenieDataPoczatek);
		}
	}
}
