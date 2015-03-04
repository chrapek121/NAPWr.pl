package pl.wroc.pwr.na.database.parsers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.wroc.pwr.na.database.objects.EventObject;
import pl.wroc.pwr.na.tools.RequestTaskString;

public class EventParser {

	public ArrayList<EventObject> getAllEvents() {
		String url = "http://www.napwr.pl/mobile/wydarzenia";
		return getEventsJSON(url);
	}

	private ArrayList<EventObject> getEventsJSON(String URL) {

		JSONArray completeJSONArr = getItemJSONArray(URL);

		JSONObject event;
		ArrayList<EventObject> eventList = new ArrayList<EventObject>();

		for (int i = 0; i < completeJSONArr.length(); i++) {
			try {
				event = completeJSONArr.getJSONObject(i);
				eventList.add(parser(event));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return eventList;
	}

	private JSONArray getItemJSONArray(String URL) {
		try {
			return new JSONArray((String) new RequestTaskString().execute(URL)
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

	private EventObject parser(JSONObject event) {
		int wydarzenieId = -1;
		String wydarzenieTytul = "";
		String wydarzenieTresc = "";
		String wydarzenieZewnetrznyLink = "";
		int organizacjaId = -1;
		int adresId = -1;
		String wydarzenieSala = "";
		String wydarzenieAdresInny = "";
		String plakatUrl = "";
		int tag1 = -1;
		int tag2 = -1;
		int tag3 = -1;
		int tag4 = -1;
		int tag5 = -1;
		String wydarzenieDataPoczatek = "";
		String wydarzenieDataKoniec = "";
		boolean wydarzeniePlatne = false;
		int wydarzenieCena = -1;
		String wydarzenieZapisyPoczatek = "";
		String wydarzenieZapisyKoniec = "";
		boolean wydarzenieTrwaCalyDzien = false;

		try {
			wydarzenieId = event.getInt("wydarzenieId");
			wydarzenieTytul = event.getString("wydarzenieTytul");
			wydarzenieTresc = event.getString("wydarzenieTresc");
			wydarzenieZewnetrznyLink = event
					.getString("wydarzenieZewnetrznyLink");
			organizacjaId = event.getInt("organizacjaId");
			adresId = event.getInt("adresId");
			wydarzenieSala = event.getString("wydarzenieSala");
			wydarzenieAdresInny = event.getString("wydarzenieAdresInny");
			plakatUrl = event.getString("plakatMobile");
			if (!event.isNull("tagId"))
				tag1 = event.getInt("tagId");
			// tag1 = event.getInt("tag1");
			// tag2 = event.getInt("tag2");
			// tag3 = event.getInt("tag3");
			// tag4 = event.getInt("tag4");
			// tag5 = event.getInt("tag5");
			wydarzenieDataPoczatek = getData("wydarzenieDataPoczatek", event);
			wydarzenieDataKoniec = getData("wydarzenieDataKoniec", event);
			wydarzeniePlatne = event.getBoolean("wydarzeniePlatne");
			wydarzenieCena = event.getInt("wydarzenieCena");
			wydarzenieZapisyPoczatek = getData("wydarzenieZapisyPoczatek",
					event);
			wydarzenieZapisyKoniec = getData("wydarzenieZapisyKoniec", event);
			wydarzenieTrwaCalyDzien = event
					.getBoolean("wydarzenieTrwaCalyDzien");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new EventObject(wydarzenieId, wydarzenieTytul, wydarzenieTresc,
				wydarzenieZewnetrznyLink, organizacjaId, adresId,
				wydarzenieSala, wydarzenieAdresInny, "http://napwr.pl"
						+ plakatUrl, tag1, tag2, tag3, tag4, tag5,
				wydarzenieDataPoczatek, wydarzenieDataKoniec, wydarzeniePlatne,
				wydarzenieCena, wydarzenieZapisyPoczatek,
				wydarzenieZapisyKoniec, wydarzenieTrwaCalyDzien, false);
	}

	private String getData(String field, JSONObject event) throws JSONException {
		JSONObject jsonObject = event.getJSONObject(field);
		return jsonObject.getString("date");
	}
}
