package com.android.arjun.redditfinder;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpFeedTask extends AsyncTask <String, Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        final StringBuilder response  = new StringBuilder();

        try {
            final URL url = new URL(strings[0]);
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpConn.getInputStream()),8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
