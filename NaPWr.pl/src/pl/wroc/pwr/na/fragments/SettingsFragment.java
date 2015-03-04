package pl.wroc.pwr.na.fragments;

import java.util.ArrayList;
import java.util.List;

import pl.wroc.pwr.na.NAPWrApplication;
import pl.wroc.pwr.na.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingsFragment extends Fragment {
	private Spinner facultity;
	private Spinner exit;
	private Button save;
	private NAPWrApplication app;
	private ToggleButton wifi;
	private ToggleButton notyfications;
	private ToggleButton images;

	public SettingsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);

		app = (NAPWrApplication) getActivity().getApplication();

		prepareWifiOnly(rootView);
		prepareIncludeImages(rootView);
		prepareNotyfications(rootView);
		prepareExitOption(rootView);
		prepareFacultityOption(rootView);
		prepareSaveButton(rootView);

		return rootView;
	}

	private void prepareWifiOnly(View rootView) {
		wifi = (ToggleButton) rootView.findViewById(R.id.settings_wifi_only);
		wifi.setChecked(app.getWifiOnly());
	}

	private void prepareIncludeImages(View rootView) {
		images = (ToggleButton) rootView
				.findViewById(R.id.settings_include_images);
		images.setChecked(app.getIncludeImages());
	}

	private void prepareNotyfications(View rootView) {
		notyfications = (ToggleButton) rootView
				.findViewById(R.id.settings_notyfications);
		notyfications.setChecked(app.getNotyfications());
	}

	private void prepareExitOption(View rootView) {
		exit = (Spinner) rootView.findViewById(R.id.settings_exit);
		List<String> list = new ArrayList<String>();
		list.add("Minimalizuj");
		list.add("Zamknij");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				rootView.getContext(), R.layout.fragment_settings_facultity,
				list);

		exit.setAdapter(adapter);
		exit.setSelection(app.getExit());
	}

	private void prepareFacultityOption(View rootView) {
		facultity = (Spinner) rootView.findViewById(R.id.settings_facultity);
		List<String> list = new ArrayList<String>();
		list.add("W-1 Architektura");
		list.add("W-2 Budownictwo");
		list.add("W-3 Chemia");
		list.add("W-4 Elektronika");
		list.add("W-5 Elektryczny");
		list.add("W-6 WGGG");
		list.add("W-7 IŚ");
		list.add("W-8 WIZ");
		list.add("W-9 WME");
		list.add("W-10 Mechaniczny");
		list.add("W-11 WPPT");
		list.add("W-12 WEMiF");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				rootView.getContext(), R.layout.fragment_settings_facultity,
				list);

		facultity.setAdapter(adapter);
		facultity.setSelection(app.getWydzial());
	}

	private void prepareSaveButton(View rootView) {
		save = (Button) rootView.findViewById(R.id.settings_save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				app.setWydzial(facultity.getSelectedItemPosition());
				app.setExit(exit.getSelectedItemPosition());
				app.setIncludeImages(images.isChecked());
				app.setNotyfications(notyfications.isChecked());
				app.setWifiOnly(wifi.isChecked());

				Toast.makeText(getActivity(), "Ustawienia zapisane.",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}