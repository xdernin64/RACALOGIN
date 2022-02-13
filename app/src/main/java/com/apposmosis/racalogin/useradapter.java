package com.apposmosis.racalogin;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class useradapter extends  FirestoreRecyclerAdapter<usuarios,useradapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public useradapter(@NonNull FirestoreRecyclerOptions<usuarios> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull usuarios usuarios) {
        holder.txtcodigo.setText(usuarios.getCodigo());
        holder.txtapellidos.setText(usuarios.getApellidos());
        holder.txtnombres.setText(usuarios.getNombres());
        holder.txtcelular.setText(usuarios.getCelular());
        holder.txtemail.setText(usuarios.getCorreo());
        holder.txtpassword.setText(usuarios.getPassword());
        holder.txtuid.setText(usuarios.getUid());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vstadeinicio,viewGroup,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtcodigo;
        TextView txtapellidos;
        TextView txtnombres;
        TextView txtcelular;
        TextView txtemail;
        TextView txtpassword;
        TextView txtuid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*
            txtcodigo=itemView.findViewById(txt_codigo);
            txtapellidos=itemView.findViewById(txt_apellidos);
            txtnombres=itemView.findViewById(txt_nombres);
            txtcelular=itemView.findViewById(txt_celular);
            txtemail=itemView.findViewById(txt_celular);
            txtpassword=itemView.findViewById(txt_password);
            txtuid=itemView.findViewById(txt_uid);*/
        }
    }
}
