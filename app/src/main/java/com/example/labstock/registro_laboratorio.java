package com.example.labstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_laboratorio);
        context = getApplicationContext();
        Intent intent = getIntent();
        String ref = intent.getStringExtra("user");


        String idLabI = intent.getStringExtra("lab");
        String nombreLabI = intent.getStringExtra("nombre");
        String ubicacionLabI = intent.getStringExtra("ubicacion");
        String descripcionLabI = intent.getStringExtra("descripcion");

        if (idLabI != null && nombreLabI != null && ubicacionLabI != null && descripcionLabI != null) {
            dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(idLabI);
            ((EditText) findViewById(R.id.editNombreLab)).setText(nombreLabI);
            ((EditText) findViewById(R.id.editUbicacionLab)).setText(ubicacionLabI);
            ((EditText) findViewById(R.id.editDescripcionLab)).setText(descripcionLabI);
            ((Button)findViewById(R.id.btn_guardarLab)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.btn_guardarLabChg)).setVisibility(View.VISIBLE);

        } else {
            dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);
        }
    }

    //Guardar Lab y volver al menu
    public void guardarLab(View view) {

        String nombreLab = ((EditText) findViewById(R.id.editNombreLab)).getText().toString();
        String ubicacionLab = ((EditText) findViewById(R.id.editUbicacionLab)).getText().toString();
        String descripcionLab = ((EditText) findViewById(R.id.editDescripcionLab)).getText().toString();


        if (!nombreLab.isEmpty() && !ubicacionLab.isEmpty() && !descripcionLab.isEmpty()) {
            DatabaseReference labsrf = dbRef.child("laboratorios");

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

    //Guardar Cambios Edit Lab
    public void guardarLabChg(View view) {

        String nombreLab = ((EditText) findViewById(R.id.editNombreLab)).getText().toString();
        String ubicacionLab = ((EditText) findViewById(R.id.editUbicacionLab)).getText().toString();
        String descripcionLab = ((EditText) findViewById(R.id.editDescripcionLab)).getText().toString();


        if (!nombreLab.isEmpty() && !ubicacionLab.isEmpty() && !descripcionLab.isEmpty()) {


            //Writing Hashmap
            Map<String, Object> newDataLab = new HashMap<>();

            newDataLab.put("nombre", nombreLab);
            newDataLab.put("ubicacion", ubicacionLab);
            newDataLab.put("descripcion", descripcionLab);


            dbRef.updateChildren(newDataLab).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Cambios Guardados", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Fallo en los Cambios", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(context, "Campos Vacíos", Toast.LENGTH_SHORT).show();
        }
    }
}