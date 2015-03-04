package pl.wroc.pwr.na.fragments;

import java.util.List;

import pl.wroc.pwr.na.R;
import pl.wroc.pwr.na.database.DataBaseHandler;
import pl.wroc.pwr.na.database.objects.EventObject;
import pl.wroc.pwr.na.events.EventActivity;
import pl.wroc.pwr.na.fragments.adapters.EventListAdapter;
import pl.wroc.pwr.na.tools.EventPreparator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class EventListFragment extends Fragment {

	private List<EventObject> eventList;

	private RelativeLayout noContent;
	private RelativeLayout fullContent;
	private ListView eventListView;
	private EventPreparator preparator = new EventPreparator();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_category,
				container, false);

		DataBaseHandler db = new DataBaseHandler(rootView.getContext());
		eventList = db.getAllEventObjects();
		db.close();

		initializeItems(rootView);
		prepareItems(rootView);

		return rootView;
	}

	private void initializeItems(View rootView) {
		noContent = (RelativeLayout) rootView
				.findViewById(R.id.category_no_events);
		fullContent = (RelativeLayout) rootView
				.findViewById(R.id.category_events);
		eventListView = (ListView) rootView
				.findViewById(R.id.category_event_list);
	}

	private void prepareItems(View rootView) {
		noContent.setVisibility(View.GONE);
		fullContent.setVisibility(View.GONE);

		if (eventList.isEmpty()) {
			noContent.setVisibility(View.VISIBLE);
		} else {
			fullContent.setVisibility(View.VISIBLE);
			prepareEventList(rootView);
		}
	}

	private void prepareEventList(final View rootView) {
		EventListAdapter adapter = new EventListAdapter(rootView.getContext(),
				preparator.prepareEvents(eventList));
		eventListView.setAdapter(adapter);

		eventListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Intent i = new Intent(rootView.getContext(),
								EventActivity.class);
						i.putExtra(EventActivity.EVENT_ID,
								eventList.get(position).wydarzenieId);
						startActivity(i);
					}
				});
	}
}