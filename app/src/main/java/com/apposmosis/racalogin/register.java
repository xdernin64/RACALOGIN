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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class register extends AppCompatActivity {
    private EditText codigo,nombres,apellidos,celular,correo,contrasena;
    public Button btnregistrar;
    public TextView iralogin,tvarea;
    public Spinner spnarea,spnlabor;
    private QuerySnapshot Areas;



    //datos a registrar
    private String codigotext="";
    private String nombrestext="";
    private String apellidostext="";
    private String celulartext="";
    private String correotext="";
    private String contrasenatext="";
    private String strarea;
    private String strlabor;

    private ArrayList<String> optiosntrabajo;
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
        tvarea=findViewById(R.id.tvidarea);

        //botones
        btnregistrar=findViewById(R.id.btn_register);
        iralogin=findViewById(R.id.tv_iralogin);
        spnarea=findViewById(R.id.spn_area);
        spnlabor=findViewById(R.id.spn_labor);


        //llenando spinner
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference Areasref = rootRef.collection("Areas");
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnarea.setAdapter(adapter);
        List<String> docid = new ArrayList<>();
        Areasref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String idarea =document.getId();
                        String subject = document.getString("Nombre");
                        //subspinner

                        subjects.add(subject);
                        docid.add(idarea);
                    }
                    adapter.notifyDataSetChanged();

                }

            }



        });

        spnarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String area=spnarea.getSelectedItem().toString();
                String iddoccumento;
                int arreglosz=subjects.size();
                for (int i=0;arreglosz>i;i++)
                {
                    if (subjects.get(i) == area)
                    {

                        String docid=String.valueOf(i+1);
                        cargarsubspinner(docid);
                        tvarea.setText(docid);
                    }
                }
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
                strarea=spnarea.getSelectedItem().toString();
                strlabor=spnlabor.getSelectedItem().toString();



//                cargarspinner();
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
private void cargarsubspinner(String idarea) {

    CollectionReference Laborref = mFirestore.collection("Areas").document(idarea).collection("trabajos");
    List<String> Labor = new ArrayList<>();
    ArrayAdapter<String> Adapaterlabor = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Labor);
    Adapaterlabor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnlabor.setAdapter(Adapaterlabor);
    Laborref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String subjectlabor = document.getString("Nombre");
                    Labor.add(subjectlabor);

                }
                Adapaterlabor.notifyDataSetChanged();
            }
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
                    map.put("apellidosynombres",nombresyapellidos);
                    map.put("celular",celulartext);
                    map.put("correo",correotext);
                    map.put("password",contrasenatext);
                    map.put("area",strarea);
                    map.put("labor",strlabor);
                    map.put("uid",id);
                    //Salvando en la nueva tabla
                        mFirestore.collection("usuarios").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){

                                ///guardandousuario dentro del area
                                mFirestore.collection("Areas").document(id).collection("usuarios").document(codigotext).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity((new Intent(register.this,Datosusuario.class)));
                                        finish();

                                    }
                                });
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