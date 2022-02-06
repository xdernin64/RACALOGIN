package com.apposmosis.racalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Datosusuario extends AppCompatActivity {
    private Button btnlogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datosusuario);
    mAuth=FirebaseAuth.getInstance();
    btnlogout=(Button) findViewById(R.id.btn_logout);
    btnlogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            startActivity(new Intent(Datosusuario.this,login.class));
            finish();
        }
    });
    }
}