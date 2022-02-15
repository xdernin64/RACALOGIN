package com.apposmosis.racalogin;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class adaptadorsalidas extends RecyclerView.Adapter<adaptadorsalidas.MyViewHolder> {
    Context context;
    ArrayList<model> salidasArrayList;

    public  adaptadorsalidas(Context context,ArrayList<model> salidasArrayList){
        this.context=context;
        this.salidasArrayList=salidasArrayList;

    }
    @NonNull
    @Override
    public adaptadorsalidas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.salida , parent , false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorsalidas.MyViewHolder holder, int position) {
    model modelo= salidasArrayList.get(position);
        Integer segundos= Integer.parseInt(String.valueOf(modelo.fecha.getSeconds()));
        Date date = new Date(segundos*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);

        holder.tvfecha.setText(String.valueOf(formattedDate));
    holder.tvcodigo.setText(modelo.codigo);
    holder.tvhsalida.setText(modelo.horadesalida);
    holder.tvhextras.setText(String.valueOf(modelo.horasextra));


    }

    @Override
    public int getItemCount() {
        return salidasArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvfecha,tvcodigo,tvhsalida,tvhextras;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvfecha=itemView.findViewById(R.id.tvshow_fecha);
            tvcodigo=itemView.findViewById(R.id.tvshow_codigo);
            tvhsalida=itemView.findViewById(R.id.tvshow_salida);
            tvhextras=itemView.findViewById(R.id.tvshow_horasextras);

        }
    }
}
