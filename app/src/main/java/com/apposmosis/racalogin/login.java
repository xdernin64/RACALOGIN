package com.apposmosis.racalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://raca-app-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText codigo=findViewById(R.id.txt_id);
        final EditText contrasena=findViewById(R.id.txt_pwd);
        final Button btnlogin=findViewById(R.id.btn_register);
        final TextView iraregistrar=findViewById(R.id.tv_iralogin);
    btnlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        final String codigotext=codigo.getText().toString();
        final String passwordtext=contrasena.getText().toString();
        if (codigotext.isEmpty()|| passwordtext.isEmpty())
        {
            Toast.makeText(login.this, "Hay un campo vacio escriba porfavor su usuario y contraseña ", Toast.LENGTH_SHORT).show();
        }
        else   {
            databaseReference.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(codigotext)){
                        final String getPassword=snapshot.child(codigotext).child("Password").getValue(String.class);

                        if (getPassword.equals(passwordtext)){
                            Toast.makeText(login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login.this,MainActivity.class));

                        }
                        else {
                            Toast.makeText(login.this, "Contraseña incorrecta incorrecta", Toast.LENGTH_SHORT).show();
                        }
                        }
                    else{
                        Toast.makeText(login.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                    }

                    }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        
        }
    });
    iraregistrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(login.this,register.class));
        }
    });


    }


}