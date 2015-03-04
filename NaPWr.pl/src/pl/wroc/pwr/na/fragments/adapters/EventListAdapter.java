package pl.wroc.pwr.na.fragments.adapters;

import java.util.List;

import pl.wroc.pwr.na.R;
import pl.wroc.pwr.na.database.objects.EventObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<EventObject> {

	private static final int EVENT_ITEM = R.layout.fragment_category_event;

	public EventListAdapter(Context context, List<EventObject> items) {
		super(context, EVENT_ITEM, items);
	}

	public EventObject getEvent(int position) {
		return getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout alertView = getAlertView(convertView);

		TextView eventDay = (TextView) alertView.findViewById(R.id.event_day);
		TextView eventMonth = (TextView) alertView
				.findViewById(R.id.event_month);
		TextView eventTitle = (TextView) alertView
				.findViewById(R.id.event_title);

		EventObject event = getItem(position);

		if (event.wydarzenieDataPoczatek.length() > 10) {
			eventDay.setText(event.getDay(event.wydarzenieDataPoczatek));
			eventMonth.setText(event.getMonth(event.wydarzenieDataPoczatek));
		}

		eventTitle.setText(event.wydarzenieTytul);

		return alertView;
	}

	private LinearLayout getAlertView(View convertView) {
		LinearLayout alertView;
		if (convertView == null) {
			alertView = getInflatedView();
		} else {
			alertView = (LinearLayout) convertView;
		}
		return alertView;
	}

	private LinearLayout getInflatedView() {
		LinearLayout alertView = new LinearLayout(getContext());

		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
				inflater);
		vi.inflate(EVENT_ITEM, alertView, true);

		return alertView;
	}
}