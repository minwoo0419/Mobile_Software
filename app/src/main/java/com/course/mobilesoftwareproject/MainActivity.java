package com.course.mobilesoftwareproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new HomeFragment()).commit();
        SettingListener();
    }
    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new TabSelectedListener());
    }
    class TabSelectedListener implements NavigationBarView.OnItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.home: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLayout, new HomeFragment())
                            .commit();
                    return true;
                }
                case R.id.check: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLayout, new CheckFragment())
                            .commit();
                    return true;
                }
                case R.id.analyze: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLayout, new AnalyzeFragment())
                            .commit();
                    return true;
                }
            }
            return false;
        }
    }

}