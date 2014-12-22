package com.detroitlabs.kyleofori.annotationspractice;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @ViewById(R.id.myInput)
    EditText myInput;

    @ViewById(R.id.myTextView)
    TextView textView;

    @Click
    void myButton() {
        String name = myInput.getText().toString();
        textView.setText("Hello " + name);
    }
}
