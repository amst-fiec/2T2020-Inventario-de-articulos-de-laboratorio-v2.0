package com.example.labstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class registroEquipo extends AppCompatActivity {

    private Context context;
    private String labId;
    private DatabaseReference db_reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipo);

        context = getApplicationContext();

        Intent intent = getIntent();
        labId = intent.getStringExtra("lab");

        db_reference = FirebaseDatabase.getInstance().getReferenceFromUrl(labId);
    }

    //Guardar equipo y volver al menu del laboratorio
    public void registrarEquipo(View view) {

        String nombreEquipo = ((EditText) findViewById(R.id.editNombreEquipo)).getText().toString();
        String marcaEquipo = ((EditText) findViewById(R.id.editMarcaEquipo)).getText().toString();
        String modeloEquipo = ((EditText) findViewById(R.id.editModeloEquipo)).getText().toString();
        String descripcionEquipo = ((EditText) findViewById(R.id.editDescripcionEquipo)).getText().toString();
        String responsableEquipo = ((EditText) findViewById(R.id.editResponsableEquipo)).getText().toString();

        if (!nombreEquipo.isEmpty() && !marcaEquipo.isEmpty()
                && !modeloEquipo.isEmpty() && !descripcionEquipo.isEmpty()
                && !responsableEquipo.isEmpty()) {

            DatabaseReference equiposrf = db_reference.child("equipos");

            DatabaseReference pushEquipo = equiposrf.push();
            List<String> newEquipo = new ArrayList<String>();

            String key = pushEquipo.getKey();

            //Writing Hashmap
            Map<String, Object> equipo = new HashMap<>();

            equipo.put("descripcion", descripcionEquipo);
            equipo.put("estado", 0);
            equipo.put("marca", marcaEquipo);
            equipo.put("modelo", modeloEquipo);
            equipo.put("nombre", nombreEquipo);
            equipo.put("responsable", responsableEquipo);

            equiposrf.child(key).setValue(equipo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Equipo Registrado", Toast.LENGTH_SHORT).show();
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