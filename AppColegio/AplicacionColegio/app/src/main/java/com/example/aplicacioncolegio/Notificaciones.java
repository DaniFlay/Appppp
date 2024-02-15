package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.concurrent.CountDownLatch;

public class Notificaciones extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView ;
    DatabaseReference ref;
    Usuario user;
    RecyclerView.LayoutManager layoutManager;
    AdaptadorNotificaciones adapter;
    Button salir;

    ArrayList<EncapsuladorNotificaciones> notificaiones =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.misnotificaciones);
        salir= findViewById(R.id.salir);
        salir.setOnClickListener(this);
        user = getIntent().getParcelableExtra("usuario");
        notificaiones= getIntent().getParcelableArrayListExtra("listaEnc");
        ref = FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));

            recyclerView = findViewById(R.id.listaNotificaciones);
            adapter = new AdaptadorNotificaciones(this, notificaiones, R.layout.item_permiso);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildAdapterPosition(v);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Notificaciones.this);
                    builder.setTitle(notificaiones.get(position).getNombre());
                    builder.setMessage(notificaiones.get(position).getDescripcion());
                    builder.setPositiveButton(getString(R.string.marcarLeido), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot d : snapshot.getChildren()) {
                                        Notificacion n = d.getValue(Notificacion.class);
                                            if (n.getUsuario().getCorreo().equals(user.getCorreo())&&notificaiones.get(position).getNombre().equals(n.getName())&&notificaiones.get(position).getDescripcion().equals(n.getDescripcion())&&notificaiones.get(position).getFecha().equals(n.getFecha())) {
                                                notificaiones.remove(n);
                                                n.setVisto(true);
                                                d.getRef().setValue(n);
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
                    builder.show();

                }
            });
            recyclerView.setAdapter(adapter);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);


        }

    @Override
    public void onClick(View v) {
        if(v.getId()==salir.getId()){
            Intent intent= new Intent(Notificaciones.this, MenuPrincipal.class);
            intent.putExtra("usuario",(Parcelable) user);

            startActivity(intent);
        }
    }
}


