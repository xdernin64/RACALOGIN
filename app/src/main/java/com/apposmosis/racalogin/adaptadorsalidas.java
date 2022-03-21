package com.apposmosis.racalogin;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class adaptadorsalidas extends RecyclerView.Adapter<adaptadorsalidas.MyViewHolder> {
    Context context;
    ArrayList<model> salidasArrayList;
    FirebaseFirestore firestore;
    salidas actsalidas;
    Double totalhorasextras;

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

        Double price = Double.parseDouble(String.valueOf(salidasArrayList.get(position).getHorasextra()));
        int count = getItemCount();


        for (int i = 0; i < count; i++){
            Double tsum=0.00;
            tsum = tsum + price;
            Log.d("total pay : ", String.valueOf(tsum));
        }
        //Intent i = new Intent(context, salidas.class);
        //i.putExtra("KEY",tsum);


        //holder.txthorasemanales.setText(String.valueOf(tsum));

        //holder.tvcodigo.setText(String.valueOf(tsum));

       /* totalhorasextras=totalhorasextras+(modelo.horasextra);

            holder.tvcodigo.setText(totalhorasextras.toString());*/







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
    public int grandTotal(ArrayList<model> items){

        int totalPrice = 0;
        for(int i = 0 ; i < items.size(); i++) {
            totalPrice += items.get(i).getHorasextra();
        }
        return totalPrice;

    }

    @Override
    public int getItemCount() {
        return salidasArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvfecha,tvcodigo,tvhsalida,tvhextras,txthorasemanales,tvdatos,tvobserva;
        Button btneliminar;
        String uid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvfecha=itemView.findViewById(R.id.tvshow_fecha);
            tvcodigo=itemView.findViewById(R.id.tvshow_codigo);
            tvhsalida=itemView.findViewById(R.id.tvshow_salida);
            tvhextras=itemView.findViewById(R.id.tvshow_horasextras);
            btneliminar=itemView.findViewById(R.id.btn_eliminar);
            txthorasemanales=itemView.findViewById(R.id.txt_fechamax);
            tvdatos=itemView.findViewById(R.id.txt_datos);
            tvobserva=itemView.findViewById(R.id.tv_observaciones);
        }
    }
}
