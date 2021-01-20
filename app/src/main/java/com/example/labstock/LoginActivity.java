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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;

import static Helpers.NotificationHelper.CANAL_1_ID;

public class LoginActivity extends AppCompatActivity {

    //public
    static final int GOOGLE_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();



        mAuth = FirebaseAuth.getInstance();
        context = getApplicationContext();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if (msg != null) {
            if (msg.equals("cerrarSesion")) {
                cerrarSesion();
            }
        }

    }

    public void login(View view) {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void cerrarSesion() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                task -> updateUI(null));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(
                    data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("TAG", "Fallo el inicio de sesión con google.", e);
                Toast.makeText(context, "Fallo el inicio de sesión con google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),
                null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                System.out.println("error");
                updateUI(null);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //String name = user.getDisplayName();
            //String email = user.getEmail();
            //String photo = String.valueOf(user.getPhotoUrl());

            Toast.makeText(context, "Bienvenido " +  user.getDisplayName(), Toast.LENGTH_SHORT).show();

//            HashMap<String, String> info_user = new HashMap<String, String>();
//            info_user.put("user_name", user.getDisplayName());
//            info_user.put("user_email", user.getEmail());
//            info_user.put("user_photo", String.valueOf(user.getPhotoUrl()));
//            info_user.put("user_id", user.getUid());
//
//            if (user.getPhoneNumber() != null) {
//                info_user.put("user_phone", user.getPhoneNumber());
//            } else {
//                info_user.put("user_phone", "No tiene numero celular registrado");
//            }


            Intent intent = new Intent(this, MenuActivity.class);
            //intent.putExtra("info_user", info_user);
            startActivity(intent);
            finish();

        } else {
            System.out.println("sin registrarse");
            Toast.makeText(context, "Usuario no Registrado", Toast.LENGTH_SHORT).show();
        }
    }


}