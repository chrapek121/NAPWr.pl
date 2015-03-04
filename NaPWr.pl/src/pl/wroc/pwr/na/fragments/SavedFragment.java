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

public class SavedFragment extends Fragment {
	private RelativeLayout noContent;
	private RelativeLayout fullContent;
	private List<EventObject> savedEvents;
	private ListView eventListView;
	private EventPreparator preparator = new EventPreparator();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_saved,
				container, false);

		initializeContainers(rootView);

		DataBaseHandler db = new DataBaseHandler(rootView.getContext());
		savedEvents = db.getAllSavedEventObjects();

		setVisibility(rootView);

		return rootView;
	}

	private void initializeContainers(final View rootView) {
		noContent = (RelativeLayout) rootView.findViewById(R.id.saved_no_items);
		fullContent = (RelativeLayout) rootView.findViewById(R.id.saved_is_ok);
		eventListView = (ListView) rootView.findViewById(R.id.saved_event_list);
	}

	private void setVisibility(View rootView) {
		noContent.setVisibility(View.GONE);
		fullContent.setVisibility(View.GONE);

		if (savedEvents.isEmpty()) {
			noContent.setVisibility(View.VISIBLE);
		} else {
			fullContent.setVisibility(View.VISIBLE);
			prepareEventList(rootView);
		}
	}

	private void prepareEventList(final View rootView) {
		EventListAdapter adapter = new EventListAdapter(rootView.getContext(),
				preparator.prepareEvents(savedEvents));
		eventListView.setAdapter(adapter);

		eventListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Intent i = new Intent(rootView.getContext(),
								EventActivity.class);
						i.putExtra(EventActivity.EVENT_ID,
								savedEvents.get(position).wydarzenieId);
						startActivity(i);
					}
				});
	}
}