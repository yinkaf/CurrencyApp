package com.xipsoft.currency.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import com.xipsoft.currency.app.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yinka on 2/3/16.
 */
public class WebServiceUtils {
    public static final String TAG = WebServiceUtils.class.getName();

    public static JSONObject requestJSONObject(String serviceURL) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(serviceURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(Constants.CONNECTION_READ_TIMEOUT);

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                LogUtils.log(TAG, "Unauthorized access!");
            } else if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                LogUtils.log(TAG, "404 page not found!");
            } else if(statusCode != HttpURLConnection.HTTP_OK) {
                LogUtils.log(TAG, "URL Response error :"+statusCode);
            }else {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return new JSONObject(convertInputStreamToSring(in));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtils.log(TAG, e.getMessage());
        } catch (IOException e) {
            LogUtils.log(TAG, e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.log(TAG,e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private static String convertInputStreamToSring(InputStream in) {
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }


    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
