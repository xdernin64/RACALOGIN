package com.apposmosis.racalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Datosusuario extends AppCompatActivity {
    private Button btnlogout,btnguardarhora;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView tvcodigo,tvapellidos;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datosusuario);
    mAuth=FirebaseAuth.getInstance();
    mDatabase=FirebaseDatabase.getInstance().getReference();
    firestore=FirebaseFirestore.getInstance();
    btnlogout=(Button) findViewById(R.id.btn_logout);
    tvcodigo=findViewById(R.id.tv_codigo);
    tvapellidos=findViewById(R.id.tv_apellidos);


    btnlogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            startActivity(new Intent(Datosusuario.this,login.class));
            finish();
        }
    });
        informaciondeusuario();
    }

    private void informaciondeusuario() {
        String id=mAuth.getCurrentUser().getUid();
        firestore.collection("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()){
                    String Codigo=datasnapshot.child("Codigo").getValue().toString();
                    String Apellidos=datasnapshot.child("Apellidos").getValue().toString();
                    String Nombres=datasnapshot.child("Nombres").getValue().toString();
                    String nombrecompleto=Apellidos+" "+Nombres;

                    tvcodigo.setText(Codigo);
                    tvapellidos.setText(nombrecompleto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}