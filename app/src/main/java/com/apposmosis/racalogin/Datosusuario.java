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
    private TextView tvcodigo,tvapellidos,tvfecha,tvhorasalida,tvminutosalida;
    private FirebaseFirestore firestore;
    //private Switch noche;
    //ProgressDialog progressDialog;
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
    btnguardarhora=findViewById(R.id.btn_agregarHora);
    tvfecha=findViewById(R.id.txt_fecha);
    tvhorasalida=findViewById(R.id.txt_horasalida);
    tvminutosalida=findViewById(R.id.txt_minutosalida);
    btnmostrarsalidas=findViewById(R.id.btn_mostrarsalidas);
    //noche=findViewById(R.id.switch_noche);
    //añadiendo los reciclerview
    rcvaddsalidas = findViewById(R.id.rcv_addsalida);
    rcvaddsalidas.setHasFixedSize(true);
    rcvaddsalidas.setLayoutManager(new LinearLayoutManager(this));
    salidasaddArrayList= new ArrayList<modeloaddsalida>();
    miadaptador=new adaptadoraddsalida(Datosusuario.this,salidasaddArrayList);
    rcvaddsalidas.setAdapter(miadaptador);




//        InputFilter[] filterArray = new InputFilter[1];
//        filterArray[0] = new InputFilter.LengthFilter(2);
//        tvhorasalida.setFilters(filterArray);
//        tvminutosalida.setFilters(filterArray);
//    tvhorasalida.setFilters(new InputFilter[]{ new InputFilterMinMax("1","24") });
//    tvminutosalida.setFilters(new InputFilter[]{ new InputFilterMinMax("0","59") });
//        //estableciendo vecha en edittext dd/mm/yyyy
//        long date = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        String dateString = sdf.format(date);
//        tvfecha.setText(dateString);
//        //obeteniendo numero de la semanna
//        Calendar calendar = new GregorianCalendar();
//        Date trialTime = new Date(dateString);
//        calendar.setTime(trialTime);
//        Integer numerosemana=calendar.get(Calendar.WEEK_OF_YEAR);
//        EventChangeListener();




        btnlogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            startActivity(new Intent(Datosusuario.this,login.class));
            finish();
        }
    });
//    noche.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (noche.isChecked()){
//                tvhorasalida.setText("1");
//                tvminutosalida.setText("00");
//                tvhorasalida.setVisibility(view.INVISIBLE);
//                tvminutosalida.setVisibility(view.INVISIBLE);
//            }
//            else{
//                tvhorasalida.setText("");
//                tvminutosalida.setText("");
//                tvhorasalida.setVisibility(view.VISIBLE);
//                tvminutosalida.setVisibility(view.VISIBLE);
//            }
//        }
//    });
    btnmostrarsalidas.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Datosusuario.this,salidas.class));
        }
    });
//    btnguardarhora.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            progressDialog=new ProgressDialog(Datosusuario.this);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Guardando salida");
//            progressDialog.show();
//
//
//            String strhsalida=tvhorasalida.getText().toString();
//            String strmsalida=tvminutosalida.getText().toString();
//            String fechats=tvfecha.getText().toString();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            Date fechatedate=null;
//            try {
//                fechatedate = formatter.parse(fechats);
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            formatter = new SimpleDateFormat("ddMMyyyy");
//
//
//            if (strhsalida.isEmpty() || strmsalida.isEmpty()){
//                Toast.makeText(Datosusuario.this, "Ingrese datos en horas y minutos", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//            else{
//                if (fechats.length()==10){
//                    Double horas=Double.parseDouble(strhsalida);
//                    Double minutos=Double.parseDouble(strmsalida);
//                    String codigotxt=tvcodigo.getText().toString();
//                    String horasalida= tvhorasalida.getText().toString()+":"+tvminutosalida.getText().toString();
//                    DecimalFormat df = new DecimalFormat("###.##");
//                    Double input=((horas+(minutos/60))-14.75);
//                    //Date fechatedate= new Date(fechats,"dd/mm/yy");
//                    BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
//                    double tiempoextra = bd.doubleValue();
//
//                    if (tiempoextra>=1){
//                        tiempoextra=tiempoextra+0;
//                    }
//                    else {
//                        tiempoextra=0.0;
//                    }
//
//                    if (noche.isChecked()){
//                        horasalida="Turno de noche";
//                        tiempoextra=3.25;
//
//
//                    }
//                    else{
//                        horasalida=horasalida;
//                        tiempoextra=tiempoextra;
//
//                    }
//
////                    Calendar calendario = Calendar.getInstance();
////                calendar.setFirstDayOfWeek( Calendar.MONDAY);
////                calendar.setMinimalDaysInFirstWeek(2);
////                calendar.setTime(fechatedate);
////                int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
//
//                    Map<String, Object> map= new HashMap<>();
//
//                    String id=mAuth.getCurrentUser().getUid();
//                    String docid= UUID.randomUUID().toString();
//
//                    map.put("codigo",codigotxt);
//                    map.put("fecha",fechatedate);
//                    map.put("horadesalida",horasalida);
//                    map.put("horasextra",tiempoextra);
//                    map.put("uid",id);
//                    map.put("docid",docid);
//                    //map.put("semana",numberWeekOfYear);
//
//
//
//
//                    firestore.collection("salidas").document(docid).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task2) {
//                            if (task2.isSuccessful()){
//
//                                Toast.makeText(Datosusuario.this, "Se guardo la hora de salida", Toast.LENGTH_SHORT).show();
//
//                            }
//                            else
//                            {
//
//                                Toast.makeText(Datosusuario.this,"No se pudo registrar la hora de salida", Toast.LENGTH_SHORT).show();
//
//                            }
//                            progressDialog.dismiss();
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(Datosusuario.this, "Ingrese formato de fecha dd/mm/aaaa", Toast.LENGTH_SHORT).show();
//                    tvfecha.setError("Ingrese fecha válida");
//                    progressDialog.dismiss();
//
//
//                }
//
//
//            }
//
//        }
//    });

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
                    String apellidos=documentSnapshot.getString("apellidos");
                    String nombres=documentSnapshot.getString("nombres");
                    String celular=documentSnapshot.getString("celular");
                    String email=documentSnapshot.getString("correo");
                    String password=documentSnapshot.getString("password");
                    String uid=documentSnapshot.getString("uid");
                    String nombrecompleto=apellidos+" "+nombres;
                    tvcodigo.setText(codigo);
                    tvapellidos.setText(nombrecompleto);

                    firestore.collection("salidas").whereEqualTo("uid",id ).whereEqualTo("semana",9);
                    firestore.collection("restaurants").whereEqualTo("uid",id )
                            .whereEqualTo("semana",9)
                            .get();



                }
                else{
                    Toast.makeText(Datosusuario.this, "El documento no existe", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


}