package com.detroitlabs.kyleofori.annotationspractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

@EActivity (R.layout.activity_homepage)
public class MainActivity extends ActionBarActivity {

    @ViewById (R.id.resource_loading_status)
    TextView txtResourceLoadingStatus;

    @ViewById (R.id.btn_go_to_search)
    Button btnGoToSearch;

    @Click (R.id.btn_go_to_search)
        void updateButtonText() {
        txtResourceLoadingStatus.setText(getString(R.string.resources_loaded));

    }
}
