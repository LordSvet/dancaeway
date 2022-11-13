package com.example.dancway.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.dancway.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2200);
                }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                }
            }
        };
        thread.start();

            }
        }
