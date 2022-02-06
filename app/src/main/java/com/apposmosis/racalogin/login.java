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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity {
    private EditText email,contrasena;
    private Button btnlogin;
    private String emailtext="",passwordtext="";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.txt_email);
        contrasena=findViewById(R.id.txt_pwd);
        btnlogin=findViewById(R.id.btn_register);

        final TextView iraregistrar=findViewById(R.id.tv_iralogin);
        mAuth=FirebaseAuth.getInstance();

    btnlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          emailtext=email.getText().toString();
          passwordtext=contrasena.getText().toString();
        if (emailtext.isEmpty()|| passwordtext.isEmpty())
        {
            Toast.makeText(login.this, "Hay un campo vacio escriba porfavor su usuario y contraseña ", Toast.LENGTH_SHORT).show();
        }
        else   {
            loginuser();
            /*databaseReference.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
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
            });*/

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

    private void loginuser() {
        mAuth.signInWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(login.this,Datosusuario.class));
                    finish();
                }
                else{
                    Toast.makeText(login.this, "No se pudo iniciar sesion compruebe sus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}