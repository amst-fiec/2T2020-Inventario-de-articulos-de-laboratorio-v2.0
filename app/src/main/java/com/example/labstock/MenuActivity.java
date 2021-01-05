package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import static Helpers.NotificationHelper.CANAL_1_ID;

public class MenuActivity extends AppCompatActivity {

    private Context context=this;
    private NotificationManagerCompat notificationManager;
    private String descripcionNotificaion;
    private String mensajeNotificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }


    public void laboratorios(View view) {
        Notification notification= new NotificationCompat.Builder(
                this,
                CANAL_1_ID).setSmallIcon(R.drawable.icono)
                .setContentTitle(mensajeNotificacion)
                .setContentText(descripcionNotificaion)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(Color.RED)
                .build();
        Intent intent = new Intent(context, LaboratoriosActivity.class);
        startActivity(intent);
    }

    //Borrar token etc
    public void salir(View view) {
        deleteUser();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void deleteUser() {

        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE).edit();
        editor.remove("userId");
        editor.apply();
    }
}