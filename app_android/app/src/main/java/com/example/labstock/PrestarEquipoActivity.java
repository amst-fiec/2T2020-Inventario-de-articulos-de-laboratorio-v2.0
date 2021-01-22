package com.example.labstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrestarEquipoActivity extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    private Context context;

    DatabaseReference equipo_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestar_equipo);
        context = getApplicationContext();
        Intent intent = getIntent();
        String ref = intent.getStringExtra("equipo");
        equipo_ref = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);

    }

    public void selectDate(View view) {
        showDatePickerDialog();
    }

    public void prestarEquipo(View view) {

        String fechadevPrestamo = ((EditText) findViewById(R.id.prestamo_devolucion)).getText().toString();
        String responsablePrestamo = ((EditText) findViewById(R.id.prestamo_responsable)).getText().toString();
        String ubicacionPrestamo = ((EditText) findViewById(R.id.prestamo_ubicacion)).getText().toString();

        if (!fechadevPrestamo.isEmpty() &&
                !responsablePrestamo.isEmpty() &&
                !ubicacionPrestamo.isEmpty()) {

            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            DatabaseReference prestamosrf = equipo_ref.child("prestamos");
            DatabaseReference pushPrestamo = prestamosrf.push();

            String key = pushPrestamo.getKey();

            Map<String, Object> newPrestamo = new HashMap<>();
            newPrestamo.put("devuelto", false);
            newPrestamo.put("fechaDevolucion", fechadevPrestamo);
            newPrestamo.put("fechaPrestamo", currentDate);
            newPrestamo.put("responsable", responsablePrestamo);
            newPrestamo.put("ubicacion", ubicacionPrestamo);

            prestamosrf.child(key).setValue(newPrestamo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Prestamo Registrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Fallo en el Registro", Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        } else {
            Toast.makeText(context, "Campos Vacíos", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDatePickerDialog() {

        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                ((EditText) findViewById(R.id.prestamo_devolucion)).setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);


        //Muestro el widget
        recogerFecha.show();
    }


}