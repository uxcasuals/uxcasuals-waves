package com.uxcasuals.waves.asynctasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uxcasuals.waves.events.DataAvailableEvent;
import com.uxcasuals.waves.models.Station;
import com.uxcasuals.waves.utils.EventBus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dhakchianandan on 14/09/15.
 */
public class StationsNetworkLoader extends AsyncTask {

    private static final String TAG = StationsNetworkLoader.class.getName();

    private static final String SERVER_URL = "https://uxcasuals-waves.herokuapp.com/api/stations";
    private InputStream inputStream;

    @Override
    protected Object doInBackground(Object[] params) {
        List<Station> stations = null;
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int response = httpURLConnection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                Reader reader = new InputStreamReader(inputStream);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                stations = new ArrayList<Station>();
                stations = Arrays.asList(gson.fromJson(reader, Station[].class));
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        List<Station> stations = (List<Station>) o;
//        Log.v(TAG, "Stations:" + stations);
        EventBus.getInstance().post(new DataAvailableEvent(stations));
    }
}
