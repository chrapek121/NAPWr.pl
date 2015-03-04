package pl.wroc.pwr.na.menu;

import java.util.List;

import pl.wroc.pwr.na.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuListAdapter extends ArrayAdapter<String> {

	private static final int MENU_ITEM = R.layout.fragment_navigation_drawer_item;

	public MenuListAdapter(Context context, List<String> items) {
		super(context, MENU_ITEM, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout alertView = getAlertView(convertView);

		String item = getItem(position);

		TextView option = (TextView) alertView
				.findViewById(R.id.fragment_main_button);
		option.setText(item);

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
		vi.inflate(MENU_ITEM, alertView, true);

		return alertView;
	}
}