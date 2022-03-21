package com.apposmosis.racalogin;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.SumPathEffect;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.apposmosis.racalogin.adapatadores.adaptadoraddsalida;
import com.apposmosis.racalogin.modelos.modeloaddsalida;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Datosusuario extends AppCompatActivity {
    private Button btnlogout,btnguardarhora,btnmostrarsalidas;
    private FirebaseAuth mAuth;
    private TextView tvcodigo,tvapellidos;
    private FirebaseFirestore firestore;
    RecyclerView rcvaddsalidas;
    ArrayList<modeloaddsalida> salidasaddArrayList;
    adaptadoraddsalida miadaptador;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datosusuario);
    mAuth=FirebaseAuth.getInstance();
    firestore=FirebaseFirestore.getInstance();
    btnlogout=(Button) findViewById(R.id.btn_logout);
    tvcodigo=findViewById(R.id.tv_codigo);
    tvapellidos=findViewById(R.id.tv_apellidos);
    btnmostrarsalidas=findViewById(R.id.btn_mostrarsalidas);
    //añadiendo los reciclerview
    rcvaddsalidas = findViewById(R.id.rcv_addsalida);
    rcvaddsalidas.setHasFixedSize(true);
    rcvaddsalidas.setLayoutManager(new LinearLayoutManager(this));
    salidasaddArrayList= new ArrayList<modeloaddsalida>();
    miadaptador=new adaptadoraddsalida(Datosusuario.this,salidasaddArrayList);
    rcvaddsalidas.setAdapter(miadaptador);


        btnlogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            startActivity(new Intent(Datosusuario.this,login.class));
            finish();
        }
    });

    btnmostrarsalidas.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Datosusuario.this,salidas.class));
        }
    });

        informaciondeusuario();
        EventChangeListener();
    }

    private void EventChangeListener() {
        String id=mAuth.getCurrentUser().getUid();
        miadaptador.notifyDataSetChanged();
        // db.collection("salidas").whereEqualTo("uid",id).whereEqualTo("semana",semana).orderBy("fecha",Query.Direction.DESCENDING)
        firestore.collection("usuarios").orderBy("codigo",Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {

                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType()==DocumentChange.Type.ADDED){
                        salidasaddArrayList.add(dc.getDocument().toObject(modeloaddsalida.class));
                        miadaptador.notifyDataSetChanged();

                    }

                }
                miadaptador.notifyDataSetChanged();

            }

        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)

    private void informaciondeusuario() {

        String id=mAuth.getCurrentUser().getUid();
        firestore.collection("usuarios").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    String codigo=documentSnapshot.getString("codigo");
                    String nombrecompleto=documentSnapshot.getString("apellidosynombres");
                    String celular=documentSnapshot.getString("celular");
                    String email=documentSnapshot.getString("correo");
                    String uid=documentSnapshot.getString("uid");
                    tvcodigo.setText(codigo);
                    tvapellidos.setText(nombrecompleto);


                }
                else{
                    Toast.makeText(Datosusuario.this, "El documento no existe", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


}