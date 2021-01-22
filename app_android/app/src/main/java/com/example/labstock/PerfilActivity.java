package com.example.labstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class PerfilActivity extends AppCompatActivity {

    private TextView txt_name, txt_email, txt_phone;
    private ImageView imv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        txt_name =(TextView) findViewById(R.id.txt_nombre);
        txt_email = (TextView)findViewById(R.id.txt_correo);
        imv_photo =(ImageView) findViewById(R.id.imv_photo);
        txt_phone = (TextView) findViewById(R.id.txt_phone);


        Intent intent = getIntent();

        HashMap<String, String> info_user = (HashMap<String, String>) intent.getSerializableExtra("info_user");


        txt_name.setText(info_user.get("user_name"));
        txt_email.setText(info_user.get("user_email"));
        txt_phone.setText(info_user.get("user_phone"));
        //info_user.get("user_")
        if (info_user.get("user_phone").equals("")) {
            txt_phone.setVisibility(View.GONE);
        }
        String photo = info_user.get("user_photo");
        Picasso.with(getApplicationContext()).load(photo).into(imv_photo);
    }
}