package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registroEquipo extends AppCompatActivity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipo);
    }

    //Guardar equipo y volver al menu del laboratorio
    public void registrarEquipo(View view) {
        Intent intent = new Intent(context, EquiposActivity.class);
        startActivity(intent);
    }

}