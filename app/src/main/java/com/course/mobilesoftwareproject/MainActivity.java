package com.course.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
                case R.id.input: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainLayout, new InputFragment())
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