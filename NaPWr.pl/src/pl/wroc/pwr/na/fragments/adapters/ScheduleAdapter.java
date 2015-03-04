package pl.wroc.pwr.na.fragments.adapters;

import java.util.List;

import pl.wroc.pwr.na.R;
import pl.wroc.pwr.na.database.objects.ScheduleObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScheduleAdapter extends ArrayAdapter<ScheduleObject> {

	private static final int RESOURCE = R.layout.fragment_schedule_item;
	String response;
	Context context;

	// Initialize adapter
	public ScheduleAdapter(Context context, List<ScheduleObject> items) {
		super(context, RESOURCE, items);

	}

	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout alertView;
		// Get the current alert object
		ScheduleObject event = getItem(position);

		// Inflate the view
		if (convertView == null) {
			alertView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(RESOURCE, alertView, true);
		} else {
			alertView = (LinearLayout) convertView;
		}

		TextView title = (TextView) alertView
				.findViewById(R.id.schedule_item_title);
		TextView time = (TextView) alertView
				.findViewById(R.id.schedule_item_time);
		TextView content = (TextView) alertView
				.findViewById(R.id.schedule_item_content);
		TextView date = (TextView) alertView
				.findViewById(R.id.schedule_item_date);

		LinearLayout item = (LinearLayout) alertView
				.findViewById(R.id.schedule_item);
		LinearLayout separator = (LinearLayout) alertView
				.findViewById(R.id.schedule_item_separator);

		if (event.type.equals("separator")) {
			separator.setVisibility(View.VISIBLE);
			item.setVisibility(View.GONE);

			date.setText(event.date);
		} else {
			separator.setVisibility(View.GONE);
			item.setVisibility(View.VISIBLE);

			title.setText(event.title);
			time.setText(event.place + ", " + event.time);
			content.setText(prepareContent(event));
		}

		return alertView;
	}

	private String prepareContent(ScheduleObject event) {
		String description = "";

		String sType = event.type;
		if (sType.equals("W")) {
			description += "Wykład, ";
		} else if (sType.equals("C")) {
			description += "Ćwiczenia, ";
		} else if (sType.equals("P")) {
			description += "Projekt, ";
		} else if (sType.equals("S")) {
			description += "Seminarium, ";
		} else if (sType.equals("L")) {
			description += "Laboratoria, ";
		} else if (sType.equals("X")) {
			description += "Inne, ";
		}

		description += event.lecturer;
		return description;
	}
}
