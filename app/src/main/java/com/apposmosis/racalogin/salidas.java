package com.apposmosis.racalogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class salidas extends AppCompatActivity {
    RecyclerView rcvsalidas;
    FirebaseFirestore db;
    ArrayList<model> salidasArrayList;
    adaptadorsalidas miadaptador;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_salidas);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando salidas");
        progressDialog.show();
        mAuth=FirebaseAuth.getInstance();


        rcvsalidas = findViewById(R.id.rec_salidas);
        rcvsalidas.setHasFixedSize(true);
        rcvsalidas.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        salidasArrayList= new ArrayList<model>();
        miadaptador=new adaptadorsalidas(salidas.this,salidasArrayList);
        rcvsalidas.setAdapter(miadaptador);

        EventChangeListener();

    }

    private void EventChangeListener() {
        String id=mAuth.getCurrentUser().getUid();
        db.collection("salidas").whereEqualTo("uid",id).orderBy("fecha",Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("error firestore",error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType()==DocumentChange.Type.ADDED){
                        salidasArrayList.add(dc.getDocument().toObject(model.class));
                    }

                    miadaptador.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }

            }
        });
    }
}
