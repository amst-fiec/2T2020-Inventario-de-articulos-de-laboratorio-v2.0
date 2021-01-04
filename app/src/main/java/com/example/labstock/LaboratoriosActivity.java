package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LaboratoriosActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);
        context = getApplicationContext();
    }

    //Equipo ejemplo
    public void lab(View view) {
        Intent intent = new Intent(context, EquiposActivity.class);
        startActivity(intent);
    }

    //Crear laboratorio formulario
    public void crearLab(View view) {
        Intent intent = new Intent(context, registro_laboratorio.class);
        startActivity(intent);
    }
}