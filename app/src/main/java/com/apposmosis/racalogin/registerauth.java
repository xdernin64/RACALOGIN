package com.apposmosis.racalogin;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerauth extends AppCompatActivity {
    TextView codigo,nombres,apellidos,celular,email,contrasena;
    Button registrarse;
    TextView iralogin;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerauth);


        codigo=findViewById(R.id.txt_id);
        apellidos=findViewById(R.id.txt_apellidos);
        nombres=findViewById(R.id.txt_nombres);
        celular=findViewById(R.id.txt_celular);
        email=findViewById(R.id.txt_email);
        contrasena=findViewById(R.id.txt_pwd);
        registrarse=findViewById(R.id.btn_register);
        iralogin=findViewById(R.id.tv_iralogin);

        firebaseAuth=FirebaseAuth.getInstance();

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = email.getText().toString();
                String password = contrasena.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    email.setError("Correo no válido");
                    email.setFocusable(true);
                }
                else if (password.length()<6){
                    contrasena.setError("Ingresar contraseña de mas de 6 dígitos");
                    contrasena.setFocusable(true);

                }
                else {
                    REGISTRAR(correo,password);
                }
            }
        });



    }

    private void REGISTRAR(String correo, String password) {
        firebaseAuth.createUserWithEmailAndPassword(correo,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //para registro exitoso
                if (task.isSuccessful()){
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    //agrgando el resto de campos
                    assert user!= null;
                    String uid = user.getUid();
                    String apellido=apellidos.getText().toString();
                    String nombre=nombres.getText().toString();
                    String telefono=celular.getText().toString();
                    String corre=email.getText().toString();
                    String password=contrasena.getText().toString();
                    //hasmap para mandar datos a firebase
                    HashMap<Object,String>DatosUsuario = new HashMap<>();
                    DatosUsuario.put("uid",uid);
                    DatosUsuario.put("apellido",apellido);
                    DatosUsuario.put("nombre",nombre);
                    DatosUsuario.put("telefono",telefono);
                    DatosUsuario.put("correo",corre);
                    DatosUsuario.put("password",password);

                    //imagen de usuario
                    DatosUsuario.put("imagen","");

                    //iniciando instancua de database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    //se crea la base de datis
                    DatabaseReference reference = database.getReference("USUARIOS_DE_APP");
                    reference.child(uid).setValue(DatosUsuario);
                    Toast.makeText(registerauth.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(registerauth.this,loginauth.class));
                }else{
                    Toast.makeText(registerauth.this, "Algo saló mal", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registerauth.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}