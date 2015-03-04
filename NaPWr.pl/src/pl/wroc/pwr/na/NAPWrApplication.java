package pl.wroc.pwr.na;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class NAPWrApplication extends Application {

	private String PREFS = "NaPwrPreferences";
	private SharedPreferences mPrefs;
	
	@Override
	public void onCreate() {
		super.onCreate();

		mPrefs = getSharedPreferences(PREFS, 0);
	}

	public int getWydzial() {
		return mPrefs.getInt("wydzial", 0);
	}

	public void setWydzial(int wydzial) {
		Editor e = mPrefs.edit();
		e.putInt("wydzial", wydzial);
		e.commit();
	}

	public int getExit() {
		return mPrefs.getInt("exit", 0);
	}

	public void setExit(int exit) {
		Editor e = mPrefs.edit();
		e.putInt("exit", exit);
		e.commit();
	}

	public boolean getWifiOnly() {
		return mPrefs.getBoolean("wifi", false);
	}

	public void setWifiOnly(boolean wifi) {
		Editor e = mPrefs.edit();
		e.putBoolean("wifi", wifi);
		e.commit();
	}

	public boolean getIncludeImages() {
		return mPrefs.getBoolean("images", true);
	}

	public void setIncludeImages(boolean images) {
		Editor e = mPrefs.edit();
		e.putBoolean("images", images);
		e.commit();
	}

	public boolean getNotyfications() {
		return mPrefs.getBoolean("notyfications", false);
	}

	public void setNotyfications(boolean notyfications) {
		Editor e = mPrefs.edit();
		e.putBoolean("notyfications", notyfications);
		e.commit();
	}

	public void setUser(int id, String nik) {
		Editor e = mPrefs.edit();
		e.putInt("userId", id);
		e.putString("userNik", nik);
		e.commit();
	}

	public int getUserId() {
		return mPrefs.getInt("userId", -1);
	}

	public String getUserNik() {
		return mPrefs.getString("userNik", "");
	}

}
