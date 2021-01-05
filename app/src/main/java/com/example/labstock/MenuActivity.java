package com.example.labstock;

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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static Helpers.NotificationHelper.CANAL_1_ID;

public class MenuActivity extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;
    private String descripcionNotificaion;
    private String mensajeNotificacion;
    private FirebaseAuth mAuth;

    private  Context context;
    FirebaseUser user;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context=getApplicationContext();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        Toast.makeText(context,"Bienvenido " +  user.getDisplayName(),Toast.LENGTH_SHORT).show();

    }


    public void laboratorios(View view) {
        Notification notification= new NotificationCompat.Builder(
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

    public void deleteUser() {
        Toast.makeText(context,"Regresa Pronto",Toast.LENGTH_SHORT).show();

//        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE).edit();
//        editor.remove("userId");
//        editor.apply();
    }
}