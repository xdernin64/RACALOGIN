package com.apposmosis.racalogin;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.firebase.firestore.DocumentSnapshot;
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
    FirebaseFirestore firestore;
    salidas actsalidas;
     private FirebaseFirestore db=FirebaseFirestore.getInstance();

    public  adaptadorsalidas(Context context,ArrayList<model> salidasArrayList){
        this.context=context;
        this.salidasArrayList=salidasArrayList;
        firestore=FirebaseFirestore.getInstance();
        this.actsalidas=actsalidas;


    }

    public void deleteData(int position){
        model item = salidasArrayList.get(position);
        db.collection("salidas").document(item.getDocid()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            salidasArrayList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Salida eliminada" , Toast.LENGTH_SHORT).show();


                        }else{
                            Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @NonNull
    @Override
    public adaptadorsalidas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.salida , parent , false);
        return new MyViewHolder(v);
    }

    private void notifyRemoved(int position){

        Intent intebtsalidas=new Intent(context,salidas.class);

        context.startActivity(intebtsalidas);
        actsalidas.finish();
    }



    @Override
    public void onBindViewHolder(@NonNull adaptadorsalidas.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

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

    holder.btneliminar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            firestore.collection("salidas").document(modelo.docid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Salida eliminada", Toast.LENGTH_SHORT).show();
                    salidasArrayList.remove(position);
                    notifyItemRemoved(position);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                }
            });

        }
    });



    }

    @Override
    public int getItemCount() {
        return salidasArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvfecha,tvcodigo,tvhsalida,tvhextras;
        Button btneliminar;
        String uid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvfecha=itemView.findViewById(R.id.tvshow_fecha);
            tvcodigo=itemView.findViewById(R.id.tvshow_codigo);
            tvhsalida=itemView.findViewById(R.id.tvshow_salida);
            tvhextras=itemView.findViewById(R.id.tvshow_horasextras);
            btneliminar=itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
