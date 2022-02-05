package com.apposmosis.racalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://raca-app-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView codigo=findViewById(R.id.txt_id);
        final TextView nombres=findViewById(R.id.txt_nombres);
        final TextView apellidos=findViewById(R.id.txt_apellidos);
        final TextView celular=findViewById(R.id.txt_celular);
        final TextView correo=findViewById(R.id.txt_correo);
        final TextView contrasena=findViewById(R.id.txt_pwd);

        final Button btnregistrar=findViewById(R.id.btn_register);
        final TextView iralogin=findViewById(R.id.tv_iralogin);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String codigotext=codigo.getText().toString();
                final String nombrestext=nombres.getText().toString();
                final String apellidostext=apellidos.getText().toString();
                final String celulartext=celular.getText().toString();
                final String correotext=correo.getText().toString();
                final String contrasenatext=contrasena.getText().toString();
                if(codigotext.isEmpty() || nombrestext.isEmpty() || apellidostext.isEmpty()
                 || celulartext.isEmpty() || contrasenatext.isEmpty() || correotext.isEmpty()){
                    Toast.makeText(register.this, "Ingrese datos en todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(codigotext)){
                                Toast.makeText(register.this, "El codigo ya ha sido registrado", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                databaseReference.child("Usuarios").child(codigotext).child("Apellidos").setValue(apellidostext);
                                databaseReference.child("Usuarios").child(codigotext).child("Nombres").setValue(nombrestext);
                                databaseReference.child("Usuarios").child(codigotext).child("Celular").setValue(celulartext);
                                databaseReference.child("Usuarios").child(codigotext).child("Password").setValue(contrasenatext);
                                databaseReference.child("Usuarios").child(codigotext).child("Correo").setValue(correotext);
                                Toast.makeText(register.this, "El usuario se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                    
                }
            }
        });
        iralogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,login.class));
            }
        });

    }
}