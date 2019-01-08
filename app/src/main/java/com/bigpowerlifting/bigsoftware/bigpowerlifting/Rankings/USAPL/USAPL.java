package com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.Federation;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Cacher;
import com.bigpowerlifting.bigsoftware.bigpowerlifting.utils.Networker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap
        ;
import java.util.List;

import static com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL.USAPL.Endpoints.USAPLLifterEndpoint;
import static com.bigpowerlifting.bigsoftware.bigpowerlifting.Rankings.USAPL.USAPL.Endpoints.USAPLLifterInfoEndpoint;

/**
 * Created by shanesepac on 6/2/18.
 */

public class USAPL extends Federation {

    public static class Endpoints{
        public static final String USAPLRankingsEndpoint = "http://usapl.liftingdatabase.com/rankings-default?";
        //Combine this with user ID to get an individual's lifter info.
        public static final String USAPLLifterInfoEndpoint = "http://usapl.liftingdatabase.com/lifters-view?";
        //Contains the JSON with all lifter names
        public static final String USAPLLifterEndpoint = "http://usapl.liftingdatabase.com/lifter_search.php?";
    }

    private static final String TAG = "USAPL";
    private static final String USAPL_CACHE_FILENAME = "USAPLNamesList.ser";
    private static final LinkedHashMap
            <String, String> sex;
    private static final LinkedHashMap
            <String, Integer> division;
    private static final LinkedHashMap
            <String, Integer> weightclass;
    private static final LinkedHashMap
            <String, Integer> exercise;
    private static final LinkedHashMap
            <String, Integer> state;
    private static final LinkedHashMap
            <String, Integer> year;
    private static final LinkedHashMap
            <String, String> orderby;

    //All constants initialized and populated here
    static {

        sex = new LinkedHashMap
                <>();
        division = new LinkedHashMap
                <>();
        weightclass = new LinkedHashMap
                <>();
        exercise = new LinkedHashMap
                <>();
        state = new LinkedHashMap
                <>();
        year = new LinkedHashMap
                <>();
        orderby = new LinkedHashMap
                <>();

        sex.put("Male", "m");
        sex.put("Female", "f");

        division.put("All", -1);
        division.put("Collegiate", 278);
        division.put("High School", 276);
        division.put("Junior", 1);
        division.put("High School JV", 22);
        division.put("Master", 2);
        division.put("Master 1", 18);
        division.put("Master 2", 16);
        division.put("Master 3", 17);
        division.put("Master 4", 273);
        division.put("Master 5", 386);
        division.put("Master 6", 39);
        division.put("Open", 9);
        division.put("Raw Collegiate", 280);
        division.put("Raw High School", 282);
        division.put("Raw Junior", 205);
        division.put("Raw High School JV", 45);
        division.put("Raw Master", 392);
        division.put("Raw Master 1", 206);
        division.put("Raw Master 2", 210);
        division.put("Raw Master 3", 207);
        division.put("Raw Master 4", 321);
        division.put("Raw Master 5", 218);
        division.put("Raw Master 6", 446);
        division.put("Raw Master 7", 272);
        division.put("Raw Military Open", 303);
        division.put("Raw Open", 59);
        division.put("Raw Special Olympian", 281);
        division.put("Raw Teen", 390);
        division.put("Raw Teen 1", 60);
        division.put("Raw Teen 2", 61);
        division.put("Raw Teen 3", 62);
        division.put("Raw High School Varsity", 46);
        division.put("Raw Youth", 185);
        division.put("Raw Youth 1", 215);
        division.put("Raw Youth 2", 216);
        division.put("Raw Youth 3", 64);
        division.put("Special Olympian", 277);
        division.put("Teen", 6);
        division.put("Teen 1", 26);
        division.put("Teen 2", 27);
        division.put("Teen 3", 28);
        division.put("High School Varsity", 23);

        //IPF - Female
        weightclass.put("-30", 149);
        weightclass.put("-35", 150);
        weightclass.put("-40", 151);
        weightclass.put("-43", 148);
        weightclass.put("-47", 9);
        weightclass.put("-52", 10);
        weightclass.put("-57", 11);
        weightclass.put("-63", 12);
        weightclass.put("-72", 13);
        weightclass.put("-84", 14);
        weightclass.put("84+", 15);
        //IPF - Male
        weightclass.put("-30", 152);
        weightclass.put("-35", 153);
        weightclass.put("-40", 154);
        weightclass.put("-44", 155);
        weightclass.put("-48", 156);
        weightclass.put("-53", 147);
        weightclass.put("-59", 1);
        weightclass.put("-66", 2);
        weightclass.put("-74", 3);
        weightclass.put("-83", 4);
        weightclass.put("-93", 5);
        weightclass.put("-105", 6);
        weightclass.put("-120", 7);
        weightclass.put("120+", 8);
        //USAPL Nationals - Female
        weightclass.put("-30", 157);
        weightclass.put("-35", 158);
        weightclass.put("-40", 159);
        weightclass.put("-44", 26);
        weightclass.put("-48", 27);
        weightclass.put("-52", 28);
        weightclass.put("-56", 29);
        weightclass.put("-60", 30);
        weightclass.put("-67.5", 31);
        weightclass.put("-75", 32);
        weightclass.put("-82.5", 33);
        weightclass.put("-90", 34);
        weightclass.put("90+", 35);
        //USAPL Nationals - Male
        weightclass.put("-30", 160);
        weightclass.put("-35", 161);
        weightclass.put("-40", 162);
        weightclass.put("-52", 16);
        weightclass.put("-56", 146);
        weightclass.put("-60", 17);
        weightclass.put("-67.5", 18);
        weightclass.put("-75", 19);
        weightclass.put("-82.5", 20);
        weightclass.put("-90", 21);
        weightclass.put("-100", 22);
        weightclass.put("-110", 23);
        weightclass.put("-125", 24);
        weightclass.put("125+", 25);

        exercise.put("Total", -1);
        exercise.put("Squat", 1);
        exercise.put("Bench press", 2);
        exercise.put("Deadlift", 3);

        state.put("All", null);
        state.put("Alabama", 1);
        state.put("Alaska", 2);
        state.put("Arizona", 3);
        state.put("Arkansas", 4);
        state.put("California", 5);
        state.put("Colorado", 6);
        state.put("Connecticut", 7);
        state.put("Delaware", 8);
        state.put("Florida", 9);
        state.put("Georgia", 10);
        state.put("Hawaii", 11);
        state.put("Idaho", 12);
        state.put("Illinois", 13);
        state.put("Indiana", 14);
        state.put("Iowa", 15);
        state.put("Kansas", 16);
        state.put("Kentucky", 17);
        state.put("Louisiana", 18);
        state.put("Maine", 19);
        state.put("Maryland", 20);
        state.put("Massachusetts", 21);
        state.put("Michigan", 22);
        state.put("Minnesota", 23);
        state.put("Mississippi", 24);
        state.put("Missouri", 25);
        state.put("Montana", 26);
        state.put("Nationals", 51);
        state.put("Nebraska", 27);
        state.put("Nevada", 28);
        state.put("New Hampshire", 29);
        state.put("New Jersey", 30);
        state.put("New Mexico", 31);
        state.put("New York", 32);
        state.put("North Carolina", 33);
        state.put("North Dakota", 34);
        state.put("Ohio", 35);
        state.put("Oklahoma", 36);
        state.put("Oregon", 37);
        state.put("Pennsylvania", 38);
        state.put("Regionals", 52);
        state.put("Rhode Island", 39);
        state.put("South Carolina", 40);
        state.put("South Dakota", 41);
        state.put("Tennessee", 42);
        state.put("Texas", 43);
        state.put("Utah", 44);
        state.put("Vermont", 45);
        state.put("Virginia", 46);
        state.put("Washington", 47);
        state.put("Washington DC", 53);
        state.put("West Virginia", 48);
        state.put("Wisconsin", 49);
        state.put("Wyoming", 50);

        year.put("All", -1);
        year.put("2018", 2018);
        year.put("2017", 2017);
        year.put("2016", 2016);
        year.put("2015", 2015);
        year.put("2014", 2014);
        year.put("2013", 2013);
        year.put("2012", 2012);
        year.put("1900", 1900);

        orderby.put("Points", "p");
        orderby.put("Weight", "w");

    }

    private USAPLNamesListObtained itemsRetrieved;

    public USAPL(Networker networker) {
        super(networker);
    }

    public static LinkedHashMap
            <String, String> getSex() {
        return sex;
    }

    public static LinkedHashMap
            <String, Integer> getDivision() {
        return division;
    }

    public static LinkedHashMap
            <String, Integer> getWeightclass() {
        return weightclass;
    }

    public static LinkedHashMap
            <String, Integer> getExercise() {
        return exercise;
    }

    public static LinkedHashMap
            <String, Integer> getState() {
        return state;
    }

    public static LinkedHashMap
            <String, Integer> getYear() {
        return year;
    }

    public static LinkedHashMap
            <String, String> getOrderby() {
        return orderby;
    }

    private RetryPolicy getUSAPLRetryPolicy(int timeMs) {
        return new DefaultRetryPolicy(
                timeMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    @Override
    public List<Competition> getLifterInfo(String id, LifterInfoObtained infoObtained) {
        //TODO: Complete this method
        networker.getPageContent(USAPLLifterInfoEndpoint + "id=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String newStr = null;
                try {
                    newStr = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Document doc = Jsoup.parse(newStr);
                Elements compTable = doc.getElementsByAttributeValue("id", "competition_view_results");
                Elements tbody = compTable.select("tbody");
                List<Competition> compList = new ArrayList<>();
                for (Element e : tbody.select("tr")) {
                    List<String> dataStore = new ArrayList<>();
                    for (Element ee : e.children()) {
                        Log.d(TAG, "onResponse: " + ee.text());
                        dataStore.add(ee.text());
                    }
                    String[] squats = {dataStore.get(6), dataStore.get(7), dataStore.get(8)};
                    String[] bench = {dataStore.get(9), dataStore.get(10), dataStore.get(11)};
                    String[] deadlift = {dataStore.get(12), dataStore.get(13), dataStore.get(14)};

                    Competition c = new Competition(
                            dataStore.get(0),
                            dataStore.get(1),
                            dataStore.get(2),
                            dataStore.get(3),
                            dataStore.get(4),
                            dataStore.get(5),
                            squats,
                            bench,
                            deadlift,
                            dataStore.get(15),
                            dataStore.get(16)
                    );
                    compList.add(c);
                }

                infoObtained.lifterInfoObtained(compList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: Couldn't get USAPL lifter info: " + error);
            }
        }, getUSAPLRetryPolicy(10000));
        return null;
    }
    
    
    @Override
    public List<Ranking> getRankings(RankingsObtained rankingsObtained, String address) {
        //TODO: Implement method that obtain USAPL rankings

        networker.getPageContent(address,
                r -> {
                    String newStr = null;
                    try {
                        newStr = URLDecoder.decode(URLEncoder.encode(r, "iso8859-1"), "UTF-8");
                        Document doc = Jsoup.parse(newStr);
                        Elements tableData = doc.getElementsByAttributeValue("class", "tabledata").select("tbody");
                        List<Ranking> rankingList = new ArrayList<>();
                        for (Element e : tableData.select("tr")) {
                            List<String> dataStore = new ArrayList<>();
                            for (Element ee : e.children()) {
                                dataStore.add(ee.text());
                            }
                            rankingList.add(new Ranking(dataStore.get(0),
                                    dataStore.get(1),
                                    dataStore.get(2),
                                    dataStore.get(3),
                                    dataStore.get(4)));
                        }
                        rankingsObtained.rankingsObtained(rankingList);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                },
                e -> {
                    Log.d(TAG, "getRankings: Could not get USAPL rankings, sorry.");
                },
                getUSAPLRetryPolicy(10000));
        return null;
    }

    @FunctionalInterface
    public interface USAPLNamesListObtained {
        void namesObtained(LinkedHashMap
                                   <String, String> namesAndIDs);
    }

    void getAutoFillNames(USAPLNamesListObtained itemsRetrieved, Context c) {

        //Check to see if lifter names are cached.
        //If not, perform network request to obtain them, then cache.
        //This is done asynchronously via AsyncTask.
        Cacher.deserializeLinkedHashMap
                (USAPL_CACHE_FILENAME, c, itemsRetrieved::namesObtained,
                ex -> networker.getPageContent(USAPLLifterEndpoint, response -> {
                            LinkedHashMap
                                    <String, String> results = new LinkedHashMap
                                    <>();
                            try {
                                Log.d(TAG, "onResponse: Succesfully obtained names from USAPL; creating LinkedHashMap" +
                                        "");
                                JSONArray data = new JSONArray(response);
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    results.put(obj.getString("firstname") + " " + obj.getString("lastname"),
                                            obj.getString("id"));

                                    //Set up object up for gc
                                    //TODO: Verify if setting JSONObject to null is necessary or not
                                    obj = null;
                                }
                                itemsRetrieved.namesObtained(results);
                                Cacher.serializeLinkedHashMap
                                        (results, USAPL_CACHE_FILENAME, c, e -> {
                                    //Couldn't serialize the LinkedHashMap
                                            // upon successful obtaining of lifter data
                                    Log.d(TAG, "onResponse: Couldn't serialize lifter data upon obtaining from USAPL datastore!");
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "onErrorResponse: Couldn't get lifter names", error);
                            }
                        }, getUSAPLRetryPolicy(10000)));
    }
}
