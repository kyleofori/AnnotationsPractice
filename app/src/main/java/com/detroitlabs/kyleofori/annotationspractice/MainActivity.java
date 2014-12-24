package com.detroitlabs.kyleofori.annotationspractice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;

import java.util.List;

@EActivity (R.layout.activity_homepage)
public class MainActivity extends ActionBarActivity implements KhanAcademyApiCallback {

    public static List<Lesson> khanAcademyLessons;
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
        txtResourceLoadingStatus.setText(R.string.resources_loaded);
        Log.i(this.getClass().getSimpleName(), response.toString().substring(1, 100));
        khanAcademyLessons = KhanAcademyJSONParser.parseJSONObject(response);
        Log.i(this.getClass().getSimpleName(), khanAcademyLessons.get(0).getLessonId());
        btnGoToSearch.setEnabled(true);
    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.error_loading_lesson_plans, Toast.LENGTH_SHORT).show();
        btnGoToSearch.setEnabled(true);
        btnGoToSearch.setText(getString(R.string.search_on_error));
    }
}
