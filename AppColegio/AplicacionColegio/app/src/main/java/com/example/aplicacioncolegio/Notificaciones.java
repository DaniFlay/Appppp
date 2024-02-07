package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aplicacioncolegio.adaptadores.AdaptadorNotificaciones;
import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Usuario;
import com.example.aplicacioncolegio.encapsuladores.EncapsuladorNotificaciones;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notificaciones extends AppCompatActivity  {
    RecyclerView recyclerView ;
    ArrayList<EncapsuladorNotificaciones> notificaiones= new ArrayList<EncapsuladorNotificaciones>();
    ArrayList<Notificacion> notifs= new ArrayList<>();
    DatabaseReference ref;
    Usuario user;
    RecyclerView.LayoutManager layoutManager;
    AdaptadorNotificaciones adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.misnotificaciones);
        user= getIntent().getParcelableExtra("usuario");
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Notificacion n = d.getValue(Notificacion.class);
                    if(n.getUsuario().getCorreo().equals(user.getCorreo())&&!n.isVisto()){
                        notificaiones.add(new EncapsuladorNotificaciones(R.drawable.notification,n.getName(),n.getFecha(),n.getDescripcion()));
                        notifs.add(n);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView= findViewById(R.id.listaNotificaciones);
        adapter= new AdaptadorNotificaciones(this,notificaiones,R.layout.item_permiso);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder builder= new AlertDialog.Builder(Notificaciones.this);
                builder.setTitle(notificaiones.get(position).getNombre());
                builder.setMessage(notificaiones.get(position).getDescripcion());
                builder.setPositiveButton(getString(R.string.marcarLeido), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot d: snapshot.getChildren()){
                                    Notificacion n= d.getValue(Notificacion.class);
                                    for(Notificacion n2:notifs){
                                        if(n2.equals(n)){
                                            n.setVisto(true);
                                            d.getRef().setValue(n);
                                            break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        v.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                });

            }
        });


    }


}