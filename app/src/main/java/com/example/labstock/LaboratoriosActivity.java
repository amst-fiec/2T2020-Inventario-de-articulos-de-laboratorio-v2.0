package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.labstock.Models.Account;
import com.example.labstock.Models.Converter;
import com.example.labstock.Models.Laboratorio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LaboratoriosActivity extends AppCompatActivity {

    private Context context;
    private DatabaseReference db_reference;


  private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);
        context = getApplicationContext();

        db_reference = FirebaseDatabase.getInstance().getReference().child("users");

        // Initialize Firebase Auth
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        leerRegistros();

    }

    public void leerRegistros(){

        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Map<String,Object> data=(Map) snapshot.getValue();


                    if(data.get("correo").toString().equals(user.getEmail())){


                        for(Object laboratorio:(List)data.get("laboratorios")){

                            insertLaboratorios((Map)laboratorio);
                        }
                        //Log.d("Labs",data.get("laboratorios").toString());

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Error",error.toException().toString());
            }
        });
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

    public void insertLaboratorios(Map<String,Object> laboratorio){

        LinearLayout contenedor = (LinearLayout) findViewById(R.id.container_laboratorios);

       // Button button = (Button) LayoutInflater.from(this).inflate(R.layout.list_item, null);;
        Button button=new Button(context);

        button.setText(laboratorio.get("nombre").toString());
        button.setOnClickListener(view -> {
        Intent intent=new Intent(context, EquiposActivity.class);
        //intent.putExtra("data",(Map)laboratorio);
        startActivity(intent);

        });

        contenedor.addView(button);
    }
}