package pl.wroc.pwr.na.fragments;

import java.util.List;

import pl.wroc.pwr.na.NAPWrApplication;
import pl.wroc.pwr.na.R;
import pl.wroc.pwr.na.database.DataBaseHandler;
import pl.wroc.pwr.na.database.objects.ScheduleObject;
import pl.wroc.pwr.na.fragments.adapters.ScheduleAdapter;
import pl.wroc.pwr.na.tools.PlanPreparator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ScheduleFragment extends Fragment {
	private RelativeLayout noUser;
	private RelativeLayout noContent;
	private RelativeLayout fullContent;

	private NAPWrApplication app;

	private ListView schedule;
	private List<ScheduleObject> scheduleObjects;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_schedule,
				container, false);

		app = (NAPWrApplication) getActivity().getApplication();

		DataBaseHandler db = new DataBaseHandler(rootView.getContext());
		scheduleObjects = db.getAllScheduleObjects();
		db.close();

		initializeItems(rootView);
		prepareItems(rootView);

		return rootView;
	}

	private void initializeItems(View rootView) {
		noUser = (RelativeLayout) rootView.findViewById(R.id.schedule_no_login);
		noContent = (RelativeLayout) rootView
				.findViewById(R.id.schedule_no_schedule);
		fullContent = (RelativeLayout) rootView
				.findViewById(R.id.schedule_schedule);
		schedule = (ListView) rootView.findViewById(R.id.schedule_item_list);
	}

	private void prepareItems(View rootView) {
		noUser.setVisibility(View.GONE);
		noContent.setVisibility(View.GONE);
		fullContent.setVisibility(View.GONE);

		if (app.getUserId() == -1) {
			noUser.setVisibility(View.VISIBLE);
		} else if (scheduleObjects.isEmpty()) {
			noContent.setVisibility(View.VISIBLE);
		} else {
			fullContent.setVisibility(View.VISIBLE);
			prepareScheduleList(rootView);
		}
	}

	private void prepareScheduleList(View rootView) {
		PlanPreparator preparator = new PlanPreparator();

		ScheduleAdapter adapter = new ScheduleAdapter(rootView.getContext(),
				preparator.preparePlan(scheduleObjects));

		schedule.setAdapter(adapter);
	}
}