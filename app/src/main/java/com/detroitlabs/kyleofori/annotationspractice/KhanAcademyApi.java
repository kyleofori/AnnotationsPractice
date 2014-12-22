package com.detroitlabs.kyleofori.annotationspractice;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kyleofori on 12/22/14.
 */
public class KhanAcademyApi {

    public void getKhanAcademyPlaylists(KhanAcademyApiCallback callback) {
        Uri.Builder builder = new Uri.Builder()
                .scheme("http")
                .authority("www.khanacademy.org")
                .appendPath("api")
                .appendPath("v1")
                .appendPath("playlists");

        Uri uri = builder.build();

        new LoadDataInBackground(callback).execute(uri);
    }

    private JSONArray getJSONObjectFromUri(Uri uri) throws IOException, JSONException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        int bytesRead;
        StringBuilder stringBuilder = new StringBuilder();

        while ((bytesRead = bufferedInputStream.read()) != -1) {
            stringBuilder.append((char)bytesRead);
        }

        bufferedInputStream.close();
        httpURLConnection.disconnect();
        return new JSONArray(stringBuilder.toString());
    }

    private class LoadDataInBackground extends AsyncTask<Uri, Void, JSONArray> {

        private KhanAcademyApiCallback khanAcademyApiCallback;

        private LoadDataInBackground(KhanAcademyApiCallback khanAcademyApiCallback) {
            this.khanAcademyApiCallback = khanAcademyApiCallback;

        }

        @Override
        protected JSONArray doInBackground(Uri... params) {
            try {
                Uri uri = params[0];
                return getJSONObjectFromUri(uri);
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if (result != null) {
                this.khanAcademyApiCallback.onSuccess(result);
            } else {
                this.khanAcademyApiCallback.onError();
            }
        }
        //parse

    }
}
