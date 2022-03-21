package com.apposmosis.racalogin.adapatadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apposmosis.racalogin.Datosusuario;
import com.apposmosis.racalogin.R;
import com.apposmosis.racalogin.adaptadorsalidas;
import com.apposmosis.racalogin.modelos.modeloaddsalida;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class adaptadoraddsalida extends RecyclerView.Adapter<adaptadoraddsalida.MyViewHolder> {
    Context context;
    ArrayList<modeloaddsalida> salidasaddaraylist;
    FirebaseFirestore firestore;
    Datosusuario datosusuario;
    private  FirebaseFirestore db=FirebaseFirestore.getInstance();
    public adaptadoraddsalida(Context context,ArrayList<modeloaddsalida> salidasaddaraylist){
        this.context=context;
        this.salidasaddaraylist=salidasaddaraylist;
        firestore=FirebaseFirestore.getInstance();
        this.datosusuario=datosusuario;

    }


    @NonNull
    @Override
    public adaptadoraddsalida.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.user_hextras_item,parent,false);
        return new MyViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull adaptadoraddsalida.MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
    modeloaddsalida modeloaddsalid=salidasaddaraylist.get(position);
//        Integer segundos= Integer.parseInt(String.valueOf(modeloaddsalid.fecha.getSeconds()));
//        Date date = new Date(segundos*1000L);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        String formattedDate = sdf.format(date);
        //holder.txtfecha.setText(10/10/22);

        holder.txtcodigo.setText(modeloaddsalid.codigo);
        holder.txtdatos.setText(modeloaddsalid.apellidosynombres);
        holder.tvarea.setText(modeloaddsalid.area);
        holder.tvlabor.setText(modeloaddsalid.labor);


    }

    @Override
    public int getItemCount() {
        return  salidasaddaraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        EditText txtfecha,txthosrasalida,txthorasextras,txtobservacion;
        TextView txtcodigo,txtdatos,tvarea,tvlabor  ;
        CheckBox estado;
        String nombres,apellidos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtcodigo=itemView.findViewById(R.id.txt_codigousr);
            txtdatos=itemView.findViewById(R.id.txt_datosusr);
            tvarea=itemView.findViewById(R.id.tv_area);
            tvlabor=itemView.findViewById(R.id.tv_cargo);

        }
    }
}
