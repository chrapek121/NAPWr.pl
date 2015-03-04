package pl.wroc.pwr.na.database.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pl.wroc.pwr.na.database.objects.OrganizationObject;
import pl.wroc.pwr.na.tools.RequestTaskString;

public class OrganizationParser {

    public ArrayList<OrganizationObject> getAllOrganizations() {
        //String url = "http://konieckropka.eu/organizacje.json";
        String url = "http://www.napwr.pl/mobile/organizacje";
        return getOrganizationsJSON(url);
    }

    private ArrayList<OrganizationObject> getOrganizationsJSON(String URL) {

        JSONArray completeJSONArr = getItemJSONArray(URL);

        JSONObject organization;
        ArrayList<OrganizationObject> organizationList = new ArrayList<OrganizationObject>();

        for (int i = 0; i < completeJSONArr.length(); i++) {
            try {
                organization = completeJSONArr.getJSONObject(i);
                organizationList.add(parser(organization));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return organizationList;
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

    private OrganizationObject parser(JSONObject event) {
        int organizacjaId = -1;
        String organizacjaPelnaNazwa = "";
        String avatarPlik = "";
        String uzytkownikNazwaWyswietlana = "";

//        String organizacjaAdresWww;
//        String organizacjaPrzewodniczacy;
//        String organizacjaTelefon;
//        String organizacjaFacebookStrona;
//        String organizacjaGooglePlus;
//        String organizacjaOpis;
//        String organizacjaSala;
//        String organizacjaTwitter;
//        String organizacjaYoutube;
//        String organizacjaPunktyAkcji;
//        String organizacjaPrzeczytalo;
//        String uzytkownikNazwaUrl;
//        String uzytkownikEmail;

		try {
            organizacjaId = event.getInt("organizacjaId");
            organizacjaPelnaNazwa = event.getString("organizacjaPelnaNazwa");
            uzytkownikNazwaWyswietlana = event.getString("uzytkownikNazwaWyswietlana");
            avatarPlik = event.getString("avatarPlik");

        } catch (JSONException e) {
			e.printStackTrace();
		}

        return new OrganizationObject(organizacjaId, organizacjaPelnaNazwa, uzytkownikNazwaWyswietlana, "http://napwr.pl" + avatarPlik);
    }
}
