package com.example.labstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class registro_laboratorio extends AppCompatActivity {

    private Context context;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_laboratorio);
        context = getApplicationContext();
        Intent intent = getIntent();
        String ref = intent.getStringExtra("user");
        userRef = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);
    }

    //Guardar Lab y volver al menu
    public void guardarLab(View view) {

        String nombreLab = ((EditText) findViewById(R.id.editNombreLab)).getText().toString();
        String ubicacionLab = ((EditText) findViewById(R.id.editUbicacionLab)).getText().toString();
        String descripcionLab = ((EditText) findViewById(R.id.editDescripcionLab)).getText().toString();


        if (!nombreLab.isEmpty() && !ubicacionLab.isEmpty() && !descripcionLab.isEmpty()) {
            DatabaseReference labsrf = userRef.child("laboratorios");

            DatabaseReference pushLab = labsrf.push();


            String key = pushLab.getKey();

            //Writing Hashmap
            Map<String, Object> newLab = new HashMap<>();

            newLab.put("nombre", nombreLab);
            newLab.put("ubicacion", ubicacionLab);
            newLab.put("descripcion", descripcionLab);


            labsrf.child(key).setValue(newLab).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Laboratorio Registrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Fallo en el Registro", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "Campos Vacíos", Toast.LENGTH_SHORT).show();
        }
    }
}