package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EquiposActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);
        context = getApplicationContext();
    }

    //Equipo ejemplo
    public void equipo(View view) {
        Intent intent = new Intent(context, EquipoActivity.class);
        startActivity(intent);
    }

    //Crear laboratorio formulario
    public void crearEquipo(View view) {
        Intent intent = new Intent(context, registroEquipo.class);
        startActivity(intent);
    }

}