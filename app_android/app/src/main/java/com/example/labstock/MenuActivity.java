package com.example.labstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static Helpers.NotificationHelper.CANAL_1_ID;

public class MenuActivity extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;
    private String descripcionNotificaion;
    private String mensajeNotificacion;
    private FirebaseAuth mAuth;

    private Context context;
    FirebaseUser user;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference bateria_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context = getApplicationContext();

        bateria_reference = FirebaseDatabase.getInstance().getReference().child("bateria");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        Toast.makeText(context, "Bienvenido " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        loadBateria();

    }

    private void loadBateria() {
        bateria_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ((TextView) findViewById(R.id.bateria_carga)).setText((snapshot.child("carga").getValue().toString()) + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void laboratorios(View view) {
        Notification notification = new NotificationCompat.Builder(
                this,
                CANAL_1_ID).setSmallIcon(R.drawable.icono)
                .setContentTitle(mensajeNotificacion)
                .setContentText(descripcionNotificaion)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(Color.RED)
                .build();
        Intent intent = new Intent(context, LaboratoriosActivity.class);
        startActivity(intent);
    }

    //Borrar token etc
    public void salir(View view) {

        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                task -> {

                    deleteUser();
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });


    }

    public void profile(View view) {

        HashMap<String, String> info_user = new HashMap<String, String>();
        info_user.put("user_name", user.getDisplayName());
        info_user.put("user_email", user.getEmail());
        info_user.put("user_photo", String.valueOf(user.getPhotoUrl()));


        if (user.getPhoneNumber() != null) {
            info_user.put("user_phone", user.getPhoneNumber());
        } else {
            info_user.put("user_phone", "No tiene numero registrado");
        }

        Intent intent = new Intent(context, PerfilActivity.class);
        intent.putExtra("info_user", info_user);
        startActivity(intent);
    }

    public void deleteUser() {
        Toast.makeText(context, "Regresa Pronto", Toast.LENGTH_SHORT).show();

//        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE).edit();
//        editor.remove("userId");
//        editor.apply();
    }
}