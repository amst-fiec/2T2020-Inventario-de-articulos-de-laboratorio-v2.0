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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LaboratoriosActivity extends AppCompatActivity {

    private Context context;
    private Query db_reference;


    private FirebaseUser user;
    private LinearLayout contenedor;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);
        context = getApplicationContext();
        contenedor = (LinearLayout) findViewById(R.id.container_laboratorios);
        scrollView = (ScrollView) findViewById(R.id.container_laboratorios_hidden_scroll);

        // Initialize Firebase Auth
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        db_reference = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("correo").equalTo(user.getEmail());

        leerRegistros();

    }

    public void leerRegistros() {

        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.lab_progress_loader);
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                contenedor.removeAllViews();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    Iterable<DataSnapshot> labs = snapshot.child("laboratorios").getChildren();
                    for (DataSnapshot lab : labs) {
                        insertLaboratorios(lab);
                        //Log.d("Lab", lab.toString());
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Error", error.toException().toString());
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

    public void insertLaboratorios(DataSnapshot laboratorio) {


        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.list_item_lab, null);
        ((Button) linearLayout.findViewById(R.id.btn_item)).setText(laboratorio.child("nombre").getValue().toString());

        ((Button) linearLayout.findViewById(R.id.btn_item)).setOnClickListener(view -> {
            Intent intent = new Intent(context, EquiposActivity.class);
            intent.putExtra("lab", laboratorio.getRef().toString());
            startActivity(intent);

        });

        contenedor.addView(linearLayout);
    }
}