package com.apposmosis.racalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    private EditText codigo,nombres,apellidos,celular,correo,contrasena;
    public Button btnregistrar;
    public TextView iralogin;
    public Spinner spnarea,spnlabor;
    private QuerySnapshot Areas;


    //datos a registrar
    private String codigotext="";
    private String nombrestext="";
    private String apellidostext="";
    private String celulartext="";
    private String correotext="";
    private String contrasenatext="";
    private String area;
    private String labor;
    private ArrayList<String> optiosarea;
    private ArrayList<String> optiosntrabajo;
    private ArrayAdapter<String> adaptera;
    private ArrayAdapter<String> adapterl;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();
        mFirestore=FirebaseFirestore.getInstance();
        //campos
        codigo=(EditText) findViewById(R.id.txt_email);
        nombres=(EditText)findViewById(R.id.txt_nombres);
        apellidos=(EditText)findViewById(R.id.txt_apellidos);
        celular=(EditText)findViewById(R.id.txt_celular);
        correo=(EditText)findViewById(R.id.txt_correo);
        contrasena=(EditText)findViewById(R.id.txt_pwd);

        //botones
        btnregistrar=findViewById(R.id.btn_register);
        iralogin=findViewById(R.id.tv_iralogin);
        spnarea=findViewById(R.id.spn_area);
        spnlabor=findViewById(R.id.spn_labor);


        //datos a registrar
        optiosarea=new ArrayList<>();
        optiosntrabajo=new ArrayList<>();
        adaptera=new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item);
        adaptera.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnarea.setAdapter(adaptera);
        spnarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), adaptera.getItem(i), Toast.LENGTH_SHORT).show();
                Log.e("Id ", String.valueOf(Areas.getDocuments().get(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigotext=codigo.getText().toString();
                nombrestext=nombres.getText().toString();
                apellidostext=apellidos.getText().toString();
                celulartext=celular.getText().toString();
                correotext=correo.getText().toString();
                contrasenatext=contrasena.getText().toString();


                cargarspinner();
                if(codigotext.isEmpty() || nombrestext.isEmpty() || apellidostext.isEmpty()
                 || celulartext.isEmpty() || contrasenatext.isEmpty() || correotext.isEmpty()){
                    Toast.makeText(register.this, "Ingrese datos en todos los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    registrarauth();


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
    private void cargarspinner() {
        mFirestore.collection("Areas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Areas=queryDocumentSnapshots;
                if (queryDocumentSnapshots.size()>0)
                {

                    for (DocumentSnapshot doc:queryDocumentSnapshots){
                        optiosarea.add(doc.getString("Nombre"));
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Datos encontrados", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

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
                    String nombresyapellidos=apellidostext+" "+nombrestext;


                    map.put("codigo",codigotext);
                    map.put("Nombres y apellidos",nombresyapellidos);
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