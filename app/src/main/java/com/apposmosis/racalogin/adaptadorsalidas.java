package com.apposmosis.racalogin;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.BaseProgressIndicator;

import java.util.List;

public class adaptadorsalidas extends RecyclerView.Adapter<adaptadorsalidas.myviewholder> {
     Datosusuario salidasusuario;
     List<model> mList;
     public adaptadorsalidas(Datosusuario salidasusuario,List<model>mList){
         this.salidasusuario= salidasusuario;
         this.mList=mList;

     };

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(salidasusuario).inflate(R.layout.salida , parent ,false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.fecha.setText(mList.get(position).getFecha());
        holder.nombres.setText(mList.get(position).getNombres());
        holder.horasalida.setText(mList.get(position).getHorasalida());
        holder.horasextras.setText(mList.get(position).getHorasextras());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView fecha,nombres,horasalida,horasextras;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            fecha=itemView.findViewById(R.id.tvshow_fecha);
            nombres=itemView.findViewById(R.id.tv_apellidos);
            horasalida=itemView.findViewById(R.id.tvshow_fecha);
            horasextras=itemView.findViewById(R.id.tvshow_horasextras);

        }
    }
}
