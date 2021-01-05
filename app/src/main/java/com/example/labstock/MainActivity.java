package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import static Helpers.NotificationHelper.CANAL_1_ID;

public class MainActivity extends AppCompatActivity {

    private Context context;
    // Splash Screen Timer
    private final int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
        String userId = prefs.getString("userId", null);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (userId == null) {
                    intent = new Intent(context, LoginActivity.class);
                } else {
                    intent = new Intent(context, MenuActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}