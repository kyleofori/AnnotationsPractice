package com.detroitlabs.kyleofori.annotationspractice;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

@EActivity (R.layout.activity_homepage)
public class MainActivity extends ActionBarActivity implements KhanAcademyApiCallback {

    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        khanAcademyApi.getKhanAcademyPlaylists(this);
    }

    @ViewById (R.id.resource_loading_status)
    TextView txtResourceLoadingStatus;

    @ViewById (R.id.btn_go_to_search)
    Button btnGoToSearch;

    @Click (R.id.btn_go_to_search)
        void updateButtonText() {
        txtResourceLoadingStatus.setText(getString(R.string.resources_loaded));
    }


    @Override
    public void onSuccess(JSONArray response) {
//        khanAcademyLessonModels = KhanAcademyJSONParser.parseJSONObject(response);
        txtResourceLoadingStatus.setText(R.string.resources_loaded);
        Log.i(this.getClass().getSimpleName(), response.toString().substring(1, 100));
        btnGoToSearch.setEnabled(true);
    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.error_loading_lesson_plans, Toast.LENGTH_SHORT).show();
        btnGoToSearch.setEnabled(true);
        btnGoToSearch.setText(getString(R.string.search_on_error));
    }
}
