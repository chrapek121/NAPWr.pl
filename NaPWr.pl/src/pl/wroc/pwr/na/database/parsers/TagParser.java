package pl.wroc.pwr.na.database.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pl.wroc.pwr.na.database.objects.TagObject;
import pl.wroc.pwr.na.tools.RequestTaskString;

public class TagParser {

    public ArrayList<TagObject> getAllTags() {
        //String url = "http://konieckropka.eu/organizacje.json";
        String url = "http://www.napwr.pl/mobile/tagi";
        return getTagsJSON(url);
    }

    private ArrayList<TagObject> getTagsJSON(String URL) {

        JSONArray completeJSONArr = getItemJSONArray(URL);

        JSONObject organization;
        ArrayList<TagObject> organizationList = new ArrayList<TagObject>();

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

    private TagObject parser(JSONObject event) {
        int tagId = -1;
        String tagNazwa = "";

        try {
            tagId = event.getInt("tagId");
            tagNazwa = event.getString("tagNazwa");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new TagObject(tagId, tagNazwa);
    }
}
