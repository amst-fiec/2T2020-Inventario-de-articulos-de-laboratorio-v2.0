package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EquipoActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        context = getApplicationContext();
    }

    public void prestarEquipo(View view) {

        Intent intent = new Intent(context, PrestarEquipoActivity.class);
        startActivity(intent);
    }
}