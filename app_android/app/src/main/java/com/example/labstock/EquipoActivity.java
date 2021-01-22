package com.example.labstock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EquipoActivity extends AppCompatActivity {

    private Context context;
    private String equipoId;
    private DatabaseReference equipo_reference;
    private FirebaseUser user;
    private LinearLayout contenedorPrestamos;

    private Boolean equipoDevuelto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        context = getApplicationContext();
        contenedorPrestamos = (LinearLayout) findViewById(R.id.equipo_prestamos);
        Intent intent = getIntent();
        equipoId = intent.getStringExtra("equipo");
        equipoDevuelto = false;

        // Initialize Firebase Auth
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        equipo_reference = FirebaseDatabase.getInstance().getReferenceFromUrl(equipoId);
        loadData();

    }

    public void loadData() {

        equipo_reference.addValueEventListener(new ValueEventListener() {
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
        ((LinearLayout) findViewById(R.id.equipo_mainContainer)).setVisibility(View.VISIBLE);
        Log.d("Equipos", equipo.getRef().toString());
        ((TextView) findViewById(R.id.equipo_name)).setText(equipo.child("nombre").getValue().toString());
        ((TextView) findViewById(R.id.equipo_model)).setText(equipo.child("modelo").getValue().toString());
        ((TextView) findViewById(R.id.equipo_marca)).setText(equipo.child("marca").getValue().toString());
        ((TextView) findViewById(R.id.equipo_description)).setText(equipo.child("descripcion").getValue().toString());
        ((TextView) findViewById(R.id.equipo_responsable)).setText(equipo.child("responsable").getValue().toString());
        contenedorPrestamos.removeAllViews();
        equipoDevuelto = devuelto(equipo.child("prestamos"));
    }

    public void renderDataPrestamo(DataSnapshot prestamo) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.list_item_equipo, null);
        ((Button) linearLayout.findViewById(R.id.btn_item)).setText(prestamo.child("ubicacion").getValue().toString() + ": " + prestamo.child("responsable").getValue().toString());
        boolean devuelto = Boolean.parseBoolean(prestamo.child("devuelto").getValue().toString());

        if (devuelto) {
            ((Button) linearLayout.findViewById(R.id.btn_item)).setBackgroundResource(R.drawable.btn_ok);
        } else {

            String fechaDevolucion = prestamo.child("fechaDevolucion").getValue().toString();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            try {
                Date devolDate = format.parse(fechaDevolucion);
                Date currentDate = new Date();

                if (devolDate.compareTo(currentDate) < 0) {
                    ((Button) linearLayout.findViewById(R.id.btn_item)).setBackgroundResource(R.drawable.btn_danger);
                } else {
                    ((Button) linearLayout.findViewById(R.id.btn_item)).setBackgroundResource(R.drawable.btn_warning);
                }
            } catch (ParseException e) {
                ((Button) linearLayout.findViewById(R.id.btn_item)).setBackgroundResource(R.drawable.btn_warning);
                Log.d("Lab", e.getMessage());
            }

        }

        ((Button) linearLayout.findViewById(R.id.btn_item)).setOnClickListener(view -> {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Devolución de Equipo")
                    .setMessage("El equipo fue devuelto ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            prestamo.child("devuelto").getRef().setValue(true);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //prestamo.child("devuelto").getRef().setValue(false);
                        }
                    }).show();

        });

        contenedorPrestamos.addView(linearLayout);
    }

    public void prestarEquipo(View view) {

        if (equipoDevuelto) {
            Intent intent = new Intent(context, PrestarEquipoActivity.class);
            intent.putExtra("equipo", equipo_reference.getRef().toString());
            startActivity(intent);
        } else {
            Toast.makeText(context, "No se puede prestar", Toast.LENGTH_SHORT).show();
        }
    }


    public DataSnapshot getLastPrestamo(Iterable<DataSnapshot> prestamos) {
        DataSnapshot lastElement = null;

        for (DataSnapshot prestamo : prestamos) {
            lastElement = prestamo;
            renderDataPrestamo(prestamo);
        }

        return lastElement;
    }


    public boolean devuelto(DataSnapshot prestamosRef) {

        if (prestamosRef != null) {
            DataSnapshot lastPrestamo = getLastPrestamo(prestamosRef.getChildren());

            if (lastPrestamo != null) {
                boolean devuelto = Boolean.parseBoolean(lastPrestamo.child("devuelto").getValue().toString());
                return devuelto;
            }
        }
        return true;
    }


}