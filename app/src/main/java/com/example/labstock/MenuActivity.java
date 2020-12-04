package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context=getApplicationContext();
    }

    public void laboratorios(View view){
        Intent intent=new Intent(context,LaboratoriosActivity.class);
        startActivity(intent);
    }

    //Borrar token etc
    public void salir(View view){
        Intent intent=new Intent(context,LoginActivity.class);
        startActivity(intent);
    }
}