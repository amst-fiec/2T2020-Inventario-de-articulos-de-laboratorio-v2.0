package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class registro_laboratorio extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_laboratorio);
    }

    //Guardar Lab y volver al menu
    public void guardarLab(View view) {
        Intent intent = new Intent(context, MenuActivity.class);
        startActivity(intent);
    }
}