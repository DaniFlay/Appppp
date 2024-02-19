package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Reunion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ReunionesNotificaciones extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayList<Reunion> notificaciones;
    ArrayList<String> fechas;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificaciones= getIntent().getParcelableArrayListExtra("notificaciones");
        fechas= new ArrayList<>();
        for(Reunion r:notificaciones){
            fechas.add(r.getFecha());
        }
        setContentView(R.layout.activity_reuniones_notificaciones);
        getSupportActionBar().setTitle(R.string.notificaciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview= findViewById(R.id.lista);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,fechas);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.convocatoria))
                .setMessage(getString(R.string.invitacionReunion)+":\nDescripción: "+notificaciones.get(position).getObservaciones()+getString(R.string.fecha)+fechas.get(position)+"\n"+getString(R.string.aceotaorechaza))
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fechas.remove(position);
                        listview.invalidateViews();
                        Snackbar.make(parent, R.string.aceptado,Snackbar.LENGTH_LONG)
                                .setAction(R.string.aceptar, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ref= FirebaseDatabase.getInstance().getReference("Reunion");
                                        ref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot d: snapshot.getChildren()){
                                                    Reunion r= d.getValue(Reunion.class);
                                                    if(r.equals(notificaciones.get(position))){
                                                        r.setEstado("aceptado");
                                                        d.getRef().setValue(r);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                })
                                .show();
                    }
                })
                .setNegativeButton(R.string.rechazar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fechas.remove(position);
                        listview.invalidateViews();
                        Snackbar.make(parent, R.string.rechazado,Snackbar.LENGTH_LONG)
                                .setAction(R.string.aceptar, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ref= FirebaseDatabase.getInstance().getReference("Reunion");
                                        ref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot d: snapshot.getChildren()){
                                                    Reunion r= d.getValue(Reunion.class);
                                                    if(r.equals(notificaciones.get(position))){
                                                        r.setEstado("rechazado");
                                                        d.getRef().setValue(r);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                })
                                .show();
                    }
                })
                .show();

    }

}