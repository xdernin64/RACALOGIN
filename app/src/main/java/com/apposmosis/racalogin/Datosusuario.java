package com.apposmosis.racalogin;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Datosusuario extends AppCompatActivity {
    private Button btnlogout,btnguardarhora;
    private FirebaseAuth mAuth;
    private TextView tvcodigo,tvapellidos,tvfecha,tvhorasalida,tvminutosalida;
    private FirebaseFirestore firestore;
    private Switch noche;
    //adaptador
    private adaptadorsalidas myadaptadorsalidas;
    private List<model> list;

    RecyclerView rcvsalidas;
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
    noche=findViewById(R.id.switch_noche);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(2);
        tvhorasalida.setFilters(filterArray);
        tvminutosalida.setFilters(filterArray);
    tvhorasalida.setFilters(new InputFilter[]{ new InputFilterMinMax("1","24") });
    tvminutosalida.setFilters(new InputFilter[]{ new InputFilterMinMax("0","59") });
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        tvfecha.setText(dateString);
    rcvsalidas=findViewById(R.id.rcv_listausuarios);
    rcvsalidas.setHasFixedSize(true);
    rcvsalidas.setLayoutManager(new LinearLayoutManager(this));
    //adaptador para listas
        list = new ArrayList<>();
        myadaptadorsalidas=new adaptadorsalidas(this,list);
        showdata();






    btnlogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            startActivity(new Intent(Datosusuario.this,login.class));
            finish();
        }
    });
    noche.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (noche.isChecked()){
                tvhorasalida.setText("1");
                tvminutosalida.setText("00");
                tvhorasalida.setVisibility(view.INVISIBLE);
                tvminutosalida.setVisibility(view.INVISIBLE);
            }
            else{
                tvhorasalida.setText("");
                tvminutosalida.setText("");
                tvhorasalida.setVisibility(view.VISIBLE);
                tvminutosalida.setVisibility(view.VISIBLE);
            }
        }
    });
    btnguardarhora.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String strhsalida=tvhorasalida.getText().toString();
            String strmsalida=tvminutosalida.getText().toString();
            String fechats=tvfecha.getText().toString();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date fechatedate=null;
            try {
                fechatedate = formatter.parse(fechats);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            formatter = new SimpleDateFormat("ddMMyyyy");


            if (strhsalida.isEmpty() || strmsalida.isEmpty()){
                Toast.makeText(Datosusuario.this, "Ingrese datos en horas y minutos", Toast.LENGTH_SHORT).show();
            }
            else{
                if (fechats.length()==10){
                    Double horas=Double.parseDouble(strhsalida);
                    Double minutos=Double.parseDouble(strmsalida);
                    String codigotxt=tvcodigo.getText().toString();
                    String horasalida= tvhorasalida.getText().toString()+":"+tvminutosalida.getText().toString();
                    Double tiempoextra=(horas+(minutos/60))-14.75;
                    //Date fechatedate= new Date(fechats,"dd/mm/yy");


                    if (tiempoextra>1){
                        tiempoextra=tiempoextra+0;
                    }
                    else {
                        tiempoextra=0.0;
                    }

                    if (noche.isChecked()){
                        horasalida="Turno de noche";
                        tiempoextra=3.25;


                    }
                    else{
                        horasalida=horasalida;
                        tiempoextra=tiempoextra;

                    }

                    Map<String, Object> map= new HashMap<>();

                    String id=mAuth.getCurrentUser().getUid();

                    map.put("codigo",codigotxt);
                    map.put("fecha",fechatedate);
                    map.put("horadesalida",horasalida);
                    map.put("horasextra",tiempoextra);
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
                else {
                    Toast.makeText(Datosusuario.this, "Ingrese formato de fecha dd/mm/aaaa", Toast.LENGTH_SHORT).show();
                    tvfecha.setError("Ingrese fecha válida");
                }


            }

        }
    });

        informaciondeusuario();
    }

    private void showdata() {
        firestore.collection("salidas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
            list.clear();
            for (DocumentSnapshot snapshot:task.getResult())
                {
                    model model=new model(snapshot.getString("fecha"),snapshot.getString("apellidos"),snapshot.getString("horasalida"),snapshot.getString("horasextras"));

                }

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
                }
                else{
                    Toast.makeText(Datosusuario.this, "El documento no existe", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}