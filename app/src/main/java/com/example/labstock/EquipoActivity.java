package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EquipoActivity extends AppCompatActivity {

    private Context context;
    private String equipoId;
    private DatabaseReference db_reference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        context = getApplicationContext();
        Intent intent = getIntent();
        equipoId = intent.getStringExtra("equipo");

        // Initialize Firebase Auth
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        db_reference = FirebaseDatabase.getInstance().getReferenceFromUrl(equipoId);
        loadData();

    }

    public void loadData() {

        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot equipo) {
                ((ProgressBar) findViewById(R.id.equipo_progress_loader)).setVisibility(View.GONE);
                renderData(equipo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Error", error.toException().toString());
            }
        });
    }

    public void renderData(DataSnapshot equipo) {
        ((ConstraintLayout)findViewById(R.id.equipo_mainContainer)).setVisibility(View.VISIBLE);
        Log.d("Equipos", equipo.getRef().toString());
        ((TextView) findViewById(R.id.equipo_name)).setText(equipo.child("nombre").getValue().toString());
        ((TextView) findViewById(R.id.equipo_model)).setText(equipo.child("modelo").getValue().toString());
        ((TextView) findViewById(R.id.equipo_marca)).setText(equipo.child("marca").getValue().toString());
        ((TextView) findViewById(R.id.equipo_description)).setText(equipo.child("descripcion").getValue().toString());
        ((TextView) findViewById(R.id.equipo_responsable)).setText(equipo.child("responsable").getValue().toString());
    }

    public void prestarEquipo(View view) {

        Intent intent = new Intent(context, PrestarEquipoActivity.class);
        startActivity(intent);
    }
}