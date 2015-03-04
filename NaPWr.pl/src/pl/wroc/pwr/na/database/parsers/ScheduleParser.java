package pl.wroc.pwr.na.database.parsers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.wroc.pwr.na.database.objects.ScheduleObject;
import pl.wroc.pwr.na.tools.RequestTaskString;

public class ScheduleParser {

	public ArrayList<ScheduleObject> getAllScheduleObjects(int userId) {
		String url = "http://www.napwr.pl/mobile/plan/" + userId;
		return getScheduleJSON(url);
	}

	private ArrayList<ScheduleObject> getScheduleJSON(String URL) {
		ArrayList<ScheduleObject> scheduleList = new ArrayList<ScheduleObject>();
		try {
			JSONObject completeObject = getItemJSONArray(URL);
			JSONArray completeJSONArr = completeObject
					.getJSONObject("schedule").getJSONArray("entries");

			JSONObject organization;

			for (int i = 0; i < completeJSONArr.length(); i++) {
				try {
					organization = completeJSONArr.getJSONObject(i);
					ScheduleObject parser = parser(organization);
					if (parser != null) {
						scheduleList.add(parser);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return scheduleList;
	}

	private JSONObject getItemJSONArray(String URL) {
		try {
			return new JSONObject((String) new RequestTaskString().execute(URL)
					.get());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ScheduleObject parser(JSONObject event) {
		ScheduleObject scheduleObject = null;

		try {
			int id = -1;
			String time = getTime(event);
			String place = event.getString("building") + " / "
					+ event.getString("room");
			String title = event.getString("course_name");
			String lecturer = event.getString("lecturer");
			String type = event.getString("course_type");
			int day = event.getInt("week_day");
			;
			int week = event.getInt("week");
			int startHour = event.getInt("start_hour");

			scheduleObject = new ScheduleObject(id, time, place, title,
					lecturer, type, day, week, startHour);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return scheduleObject;
	}

	private String getTime(JSONObject event) throws JSONException {
		String time = event.getInt("start_hour") + ":";

		if (event.getInt("start_min") < 10)
			time += "0";

		time += event.getInt("start_min") + " - " + event.getInt("end_hour")
				+ ":";

		if (event.getInt("end_min") < 10)
			time += "0";

		time += event.getInt("end_min");
		return time;
	}
}
