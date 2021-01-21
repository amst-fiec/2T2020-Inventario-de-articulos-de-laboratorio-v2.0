package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EquiposActivity extends AppCompatActivity {

    private Context context;

    private Query db_reference;


    private FirebaseUser user;
    String labId;

    LinearLayout contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);
        context = getApplicationContext();

        Intent intent = getIntent();
        labId = intent.getStringExtra("lab");
        contenedor = (LinearLayout) findViewById(R.id.lab_equipos);

        // Initialize Firebase Auth
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        db_reference = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("correo").equalTo(user.getEmail());

        loadData();

    }

    public void loadData() {

        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    DataSnapshot lab = snapshot.child("laboratorios").child(labId);
                    renderDataInfo(lab);
                    //Log.d("Equipos", lab.toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Error", error.toException().toString());
            }
        });
    }

    private void renderDataInfo(DataSnapshot laboratorio) {
        ((ProgressBar)findViewById(R.id.lab_progress_loader)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.lab_mainContainer)).setVisibility(View.VISIBLE);
        //Toast.makeText(context,laboratorio.child("nombre").getValue().toString(),Toast.LENGTH_SHORT).show();
        ((TextView) findViewById(R.id.lab_name)).setText(laboratorio.child("nombre").getValue().toString());
        ((TextView) findViewById(R.id.lab_description)).setText(laboratorio.child("descripcion").getValue().toString());
        ((TextView) findViewById(R.id.lab_location)).setText(laboratorio.child("ubicacion").getValue().toString());

        for (DataSnapshot equipo : laboratorio.child("equipos").getChildren()) {
            renderDataEquipos(equipo);
        }
    }

    private void renderDataEquipos(DataSnapshot equipo) {

        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.list_item, null);
        ((Button) linearLayout.findViewById(R.id.btn_item)).setText(equipo.child("nombre").getValue().toString());

        ((Button) linearLayout.findViewById(R.id.btn_item)).setOnClickListener(view -> {
            Intent intent = new Intent(context, EquipoActivity.class);
            intent.putExtra("lab", equipo.getKey());
            startActivity(intent);

        });

        contenedor.addView(linearLayout);
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