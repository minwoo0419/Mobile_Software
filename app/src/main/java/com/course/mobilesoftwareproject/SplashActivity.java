package com.course.mobilesoftwareproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);	//theme로 지정했다면 삭제한다.

        moveMain(2);	//1초 후 main activity 로 넘어감
    }

    private void moveMain(int sec) {
        new CountDownTimer(1000 * sec, 1000) {
            public void onFinish() {
                // 타이머가 끝나면 실행되는 부분
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            public void onTick(long millisUntilFinished) {
                // 타이머가 감소할 때마다 실행되는 부분
            }
        }.start();
    }
}