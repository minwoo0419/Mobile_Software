package com.course.mobilesoftwareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

public class InputPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);

        Spinner placeSpinner = (Spinner)findViewById(R.id.place);
        ArrayAdapter placeAdapter = ArrayAdapter.createFromResource(this,
                R.array.place, android.R.layout.simple_spinner_item);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);

        Spinner whenSpinner = (Spinner)findViewById(R.id.when);
        ArrayAdapter whenAdapter = ArrayAdapter.createFromResource(this,
                R.array.when, android.R.layout.simple_spinner_item);
        whenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        whenSpinner.setAdapter(whenAdapter);
    }
}