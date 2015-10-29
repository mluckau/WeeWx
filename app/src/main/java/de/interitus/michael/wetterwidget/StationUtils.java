package de.interitus.michael.wetterwidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.MessageFormat;

import java.net.URL;

/**
 * Created by michael on 29.10.15.
 */
public class StationUtils {

    private static final String URL = "http://wetter.interitus.de/json/daily.json";

    private static final String NAME = "location";
    private static final String ZEIT = "time";
    private static final String CURTEMP = "outTemp";

    public static StationData getWeather()
        throws JSONException,
            IOException /* MalformedURLException */ {

        String name = null;
        String zeit = null;
        String currentTemp = null;

        JSONObject jsonObject = new JSONObject(
                StationUtils.getFromServer(MessageFormat.format(URL, "")));

        if (jsonObject.has(NAME)){
            name = jsonObject.getString(NAME);
        }

        if (jsonObject.has(ZEIT)){
            zeit = jsonObject.getString(ZEIT);
        }

        if (jsonObject.has(CURTEMP)){
            currentTemp = jsonObject.getString(CURTEMP);
        }

        return new StationData(name, zeit, currentTemp);


    }

    public static String getFromServer(String url)
            throws IOException /* MalformedURLException */ {

        StringBuilder sb = new StringBuilder();
        URL _url = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) _url.openConnection();
        final int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            try {
                bufferedReader.close();
            } catch (IOException e){
                // ein Fehler beim Schließen wird bewusst ignoriert
            }
        }

        httpURLConnection.disconnect();
        return sb.toString();

    }

}
