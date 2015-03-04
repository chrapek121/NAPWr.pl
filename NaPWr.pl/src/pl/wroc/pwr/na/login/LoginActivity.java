package pl.wroc.pwr.na.login;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.wroc.pwr.na.NAPWrApplication;
import pl.wroc.pwr.na.R;
import pl.wroc.pwr.na.events.MainActivity;
import pl.wroc.pwr.na.tools.RequestTaskString;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener {

	private EditText email;
	private EditText password;
	private Button login;
	private Button skip;
	private TextView errorMsg;
	NAPWrApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		app = (NAPWrApplication) getApplication();

		if (app.getUserId() == -1) {
			initializeVariables();
			setOnClickListeners();
			setButtonFont();
		} else {
			startActivity();
		}
	}

	private void initializeVariables() {
		email = (EditText) findViewById(R.id.login_input_email);
		password = (EditText) findViewById(R.id.login_input_password);
		login = (Button) findViewById(R.id.login_button_login);
		skip = (Button) findViewById(R.id.login_button_skip);
		errorMsg = (TextView) findViewById(R.id.login_not_correct_login);
	}

	private void setOnClickListeners() {
		login.setOnClickListener(this);
		skip.setOnClickListener(this);
	}

	private void setButtonFont() {
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		login.setTypeface(font);
		skip.setTypeface(font);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login_button_login:
			boolean isLogged = login();
			if (isLogged) {
				startActivity();
			}
			break;
		case R.id.login_button_skip:
			startActivity();
			break;
		}
	}

	private void startActivity() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public boolean login() {

		boolean isPasswordOk = !password.getText().toString().equals("");
		boolean isLoginOk = !email.getText().toString().equals("");

		if (!isPasswordOk) {
			password.setError("Pole wymagane!");
		}

		if (!isLoginOk) {
			email.setError("Pole wymagane!");
		}

		return isPasswordOk && isLoginOk && isDataCorrect();
	}

	private boolean isDataCorrect() {
		boolean isLogged = false;
		try {
			String s = "http://www.napwr.pl/mobile/login/"
					+ email.getText().toString() + "/"
					+ password.getText().toString();
			s = startMyTask(s);

			JSONArray completeJSONArr = new JSONArray("[" + s + "]");
			JSONObject event = completeJSONArr.getJSONObject(0);

			if (event.getBoolean("approved")) {

				String wydzial = event.getString("wydzial");
				if (wydzial.contains("W-")) {
					wydzial = wydzial.substring(2);
					app.setWydzial(Integer.parseInt(wydzial) - 1);
				}
				app.setUser(event.getInt("id"), event.getString("nik"));

				isLogged = true;
			} else {
				errorMsg.setVisibility(View.VISIBLE);
			}
		} catch (JSONException e) {
			// empty
		}

		return isLogged;
	}

	String startMyTask(String s) {
		try {
			return getResponse(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getResponse(String s) throws InterruptedException,
			ExecutionException {
		if (isHoneycombOrHigher()) {
			return getRespondeForHoneycombAndHigher(s);
		} else {
			return getResponseForGingerbread(s);
		}
	}

	private boolean isHoneycombOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	// API 11
	private String getRespondeForHoneycombAndHigher(String s)
			throws InterruptedException, ExecutionException {
		return (String) new RequestTaskString().executeOnExecutor(
				AsyncTask.THREAD_POOL_EXECUTOR, s).get();
	}

	private String getResponseForGingerbread(String s)
			throws InterruptedException, ExecutionException {
		return new RequestTaskString().execute(s).get();
	}
}
