package pl.wroc.pwr.na.database.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pl.wroc.pwr.na.database.objects.AddressObject;
import pl.wroc.pwr.na.tools.RequestTaskString;

public class AddressParser {

    public ArrayList<AddressObject> getAllAddressess() {
        String url = "http://www.napwr.pl/mobile/adresy";
        return getAddressessJSON(url);
    }

    private ArrayList<AddressObject> getAddressessJSON(String URL) {

        JSONArray completeJSONArr = getItemJSONArray(URL);

        JSONObject address;
        ArrayList<AddressObject> addressList = new ArrayList<AddressObject>();

        for (int i = 0; i < completeJSONArr.length(); i++) {
            try {
                address = completeJSONArr.getJSONObject(i);
                addressList.add(parser(address));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return addressList;
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

    private AddressObject parser(JSONObject event) {

        int adresId = -1;
        String adresUlica = "";
        String adresMiasto = "";
        String adresBudynek = "";
        String adresStronaWww = "";
        String adresKategoria = "";

        try {
            adresId = event.getInt("adresId");
            adresBudynek = event.getString("adresBudynek");
            adresUlica = event.getString("adresUlica");
            adresMiasto = event.getString("adresMiasto");
            adresStronaWww = event.getString("adresStronaWww");
            adresKategoria = event.getString("adresKategoria");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new AddressObject(adresId, adresUlica, adresMiasto, adresBudynek, adresStronaWww, adresKategoria);
    }
}
