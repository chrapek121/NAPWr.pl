package pl.wroc.pwr.na.events;

import java.util.List;

import pl.wroc.pwr.na.NAPWrApplication;
import pl.wroc.pwr.na.R;
import pl.wroc.pwr.na.database.DataBaseHandler;
import pl.wroc.pwr.na.database.ObjectsParser;
import pl.wroc.pwr.na.database.objects.AddressObject;
import pl.wroc.pwr.na.database.objects.EventObject;
import pl.wroc.pwr.na.database.objects.ScheduleObject;
import pl.wroc.pwr.na.fragments.EventListFragment;
import pl.wroc.pwr.na.fragments.SavedFragment;
import pl.wroc.pwr.na.fragments.ScheduleFragment;
import pl.wroc.pwr.na.fragments.SettingsFragment;
import pl.wroc.pwr.na.login.LoginActivity;
import pl.wroc.pwr.na.menu.NavigationDrawerFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private NAPWrApplication app;

	private boolean addRefreshButton = true;

	private int refreshItem = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		app = (NAPWrApplication) getApplication();

		// List<EventObject> eventList = ObjectsParser.getAllEvents();
		// DataBaseHandler db = new DataBaseHandler(getApplicationContext());
		// db.addAllEventObjects(eventList);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (position) {
		case 0:
			refreshItem = 0;
			startEvents(fragmentManager);
			break;
		case 1:
			refreshItem = 1;
			startSchedule(fragmentManager);
			break;
		case 2:
			startSaved(fragmentManager);
			break;
		case 3:
			startSettings(fragmentManager);
			break;
		case 4:
			goBactToLogin();
			break;
		case 5:
			closeApplication();
			break;
		}

	}

	private void startEvents(FragmentManager fragmentManager) {
		mTitle = getString(R.string.title_section1);
		addRefreshButton = true;
		fragmentManager.beginTransaction()
				.replace(R.id.container, new EventListFragment()).commit();
	}

	private void startSchedule(FragmentManager fragmentManager) {
		mTitle = getString(R.string.title_section2);
		addRefreshButton = true;
		fragmentManager.beginTransaction()
				.replace(R.id.container, new ScheduleFragment()).commit();
	}

	private void startSaved(FragmentManager fragmentManager) {
		mTitle = getString(R.string.title_section3);
		addRefreshButton = false;
		fragmentManager.beginTransaction()
				.replace(R.id.container, new SavedFragment()).commit();
	}

	private void startSettings(FragmentManager fragmentManager) {
		mTitle = getString(R.string.title_section4);
		addRefreshButton = false;
		fragmentManager.beginTransaction()
				.replace(R.id.container, new SettingsFragment()).commit();
	}

	private void goBactToLogin() {
		app.setUser(-1, "");
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	public void closeApplication() {
		if (app.getExit() == 1) {
			finish();
		} else {
			moveTaskToBack(true);
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	boolean closeApp = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			changeDrawerState();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			closeApp();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void changeDrawerState() {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			mNavigationDrawerFragment.openDrawer();
		} else {
			mNavigationDrawerFragment.closeDrawer();
		}
	}

	private void closeApp() {
		if (mNavigationDrawerFragment.isDrawerOpen()) {
			mNavigationDrawerFragment.closeDrawer();
		} else if (closeApp) {
			closeApplication();
		} else {
			closeApp = true;
			Toast.makeText(
					this,
					"Naciśnij ponownie przycisk \"wstecz\" aby wyjść z aplikacji.",
					Toast.LENGTH_LONG).show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				public void run() {
					closeApp = false;
				}

			}, 5000);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			if (addRefreshButton) {
				getMenuInflater().inflate(R.menu.main, menu);
			}
			restoreActionBar();
			return true;
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_example) {

			switch (refreshItem) {
			case 0:
				refreshAddresses();
				refreshEvents();
				break;
			case 1:
				refreshSchedule();
				break;
			}

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void refreshAddresses() {
		List<AddressObject> objectList = ObjectsParser.getAllAddressess();
		DataBaseHandler db = new DataBaseHandler(getApplicationContext());
		db.addAllAddressObjects(objectList);
		db.close();
	}

	private void refreshEvents() {
		List<EventObject> objectList = ObjectsParser.getAllEvents();
		DataBaseHandler db = new DataBaseHandler(getApplicationContext());
		db.addAllEventObjects(objectList);
		db.close();

		startEvents(getSupportFragmentManager());
	}

	private void refreshSchedule() {
		if (app.getUserId() == -1) {
			Toast.makeText(this, "Zaloguj się aby korzystać z tej opcji.",
					Toast.LENGTH_SHORT).show();
		} else {
			List<ScheduleObject> objectList = ObjectsParser
					.getAllScheduleObject(app.getUserId());
			DataBaseHandler db = new DataBaseHandler(getApplicationContext());
			db.cleanScheduleObjects();
			db.addAllScheduleObjects(objectList);
			db.close();

			startSchedule(getSupportFragmentManager());
		}
	}

}
