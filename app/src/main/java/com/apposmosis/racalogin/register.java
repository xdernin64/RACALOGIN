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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    private EditText codigo,nombres,apellidos,celular,correo,contrasena;
    public Button btnregistrar;
    public TextView iralogin;

    //datos a registrar
    private String codigotext="";
    private String nombrestext="";
    private String apellidostext="";
    private String celulartext="";
    private String correotext="";
    private String contrasenatext="";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();
        mFirestore=FirebaseFirestore.getInstance();

        codigo=(EditText) findViewById(R.id.txt_email);
        nombres=(EditText)findViewById(R.id.txt_nombres);
        apellidos=(EditText)findViewById(R.id.txt_apellidos);
        celular=(EditText)findViewById(R.id.txt_celular);
        correo=(EditText)findViewById(R.id.txt_correo);
        contrasena=(EditText)findViewById(R.id.txt_pwd);

        btnregistrar=findViewById(R.id.btn_register);
        iralogin=findViewById(R.id.tv_iralogin);


        //datos a registrar

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigotext=codigo.getText().toString();
                nombrestext=nombres.getText().toString();
                apellidostext=apellidos.getText().toString();
                celulartext=celular.getText().toString();
                correotext=correo.getText().toString();
                contrasenatext=contrasena.getText().toString();

                if(codigotext.isEmpty() || nombrestext.isEmpty() || apellidostext.isEmpty()
                 || celulartext.isEmpty() || contrasenatext.isEmpty() || correotext.isEmpty()){
                    Toast.makeText(register.this, "Ingrese datos en todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    registrarauth();

                  /*  databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
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

                                registrarauth();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });*/

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

    private void registrarauth() {
        mAuth.createUserWithEmailAndPassword(correotext,contrasenatext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String, Object> map= new HashMap<>();

                    String id=mAuth.getCurrentUser().getUid();

                    map.put("codigo",codigotext);
                    map.put("apellidos",apellidostext);
                    map.put("nombres",nombrestext);
                    map.put("celular",celulartext);
                    map.put("correo",correotext);
                    map.put("password",contrasenatext);
                    map.put("uid",id);
                    //mDatabase.child("Usuarios").child(id).setValue(map).
                        mFirestore.collection("usuarios").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity((new Intent(register.this,Datosusuario.class)));
                                finish();
                            }
                            else
                            {

                                Toast.makeText(register.this,"No se crearon los datos correctos", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });


                }
                else{
                    Toast.makeText(register.this, "Usuario no se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}