package com.apposmosis.racalogin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Datosusuario extends AppCompatActivity {
    private Button btnlogout,btnguardarhora;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView tvcodigo,tvapellidos,tvfecha,tvhorasalida,tvminutosalida;
    private FirebaseFirestore firestore;
    private Switch noche;

    RecyclerView recyclerViewusuarios;
    @RequiresApi(api = Build.VERSION_CODES.N)
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
    btnguardarhora=findViewById(R.id.btn_agregarHora);
    tvfecha=findViewById(R.id.txt_fecha);
    tvhorasalida=findViewById(R.id.txt_horasalida);
    tvminutosalida=findViewById(R.id.txt_minutosalida);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(2);
        tvhorasalida.setFilters(filterArray);
        tvminutosalida.setFilters(filterArray);
    tvhorasalida.setFilters(new InputFilter[]{ new InputFilterMinMax("1","24") });
    tvminutosalida.setFilters(new InputFilter[]{ new InputFilterMinMax("0","59") });
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(date);
        tvfecha.setText(dateString);




    btnlogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            startActivity(new Intent(Datosusuario.this,login.class));
            finish();
        }
    });
    btnguardarhora.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int horas=Integer.parseInt(tvhorasalida.getText().toString());
            int minutos=Integer.parseInt(tvminutosalida.getText().toString());
            int horasextras= ((horas*60)+minutos) - ((14*60)+45);
            String codigotxt=tvcodigo.getText().toString();
            String fechats=tvfecha.getText().toString();
            String horasalida= tvhorasalida.getText().toString()+":"+tvminutosalida.getText().toString();

            String Maximo = "14:45";
            String startTime = "00:00";

            int h = horasextras / 60 + Integer.parseInt(startTime.substring(0,1));
            int m = horasextras % 60 + Integer.parseInt(startTime.substring(3,4));
            Double minutodecimal=new Double((m));
            Double horadecimal=new Double((h));
            Double horasextrastotales=horadecimal+(minutodecimal/120);


            Map<String, Object> map= new HashMap<>();

            String id=mAuth.getCurrentUser().getUid();

            map.put("codigo",codigotxt);
            map.put("fecha",fechats);
            map.put("horadesalida",horasalida);
            map.put("horasextra",horasextrastotales);
            map.put("uid",id);
            firestore.collection("salidas").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if (task2.isSuccessful()){
                        Toast.makeText(Datosusuario.this, "Se guardo la hora de salida", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Toast.makeText(Datosusuario.this,"No se pudo registrar la hora de salida", Toast.LENGTH_SHORT).show();
                    }



                }
            });
        }
    });

        informaciondeusuario();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)

    private void informaciondeusuario() {
        String id=mAuth.getCurrentUser().getUid();
        firestore.collection("usuarios").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String codigo=documentSnapshot.getString("codigo");
                    String apellidos=documentSnapshot.getString("apellidos");
                    String nombres=documentSnapshot.getString("nombres");
                    String celular=documentSnapshot.getString("celular");
                    String email=documentSnapshot.getString("correo");
                    String password=documentSnapshot.getString("password");
                    String uid=documentSnapshot.getString("uid");
                    String nombrecompleto=apellidos+" "+nombres;
                    tvcodigo.setText(codigo);
                    tvapellidos.setText(nombrecompleto);
                }
                else{

                }
            }
        });

        /*String id=mAuth.getCurrentUser().getUid();
        firestore.collection("Usuarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@Non     Null Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Codigo=QueryDocumentSnapshot.collection("Codigo").getValue().toString();
                            }
                        } else {

                        }
                    }
                });
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
        });*/
    }

}