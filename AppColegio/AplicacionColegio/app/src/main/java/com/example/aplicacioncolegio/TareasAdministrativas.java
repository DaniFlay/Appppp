package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aplicacioncolegio.clases.Tarea;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TareasAdministrativas extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    DatabaseReference ref;
    ArrayList<Tarea> tareas ;
    ArrayList<String> tareas2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_administrativas);
        listview= findViewById(R.id.listaTareas);
        tareas2= new ArrayList<>();
        tareas= getIntent().getParcelableArrayListExtra("tareas");
        for(int i=0;i<tareas.size();i++){
            tareas2.add(tareas.get(i).getTipo());
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,tareas2);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        getSupportActionBar().setTitle(R.string.mistareas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference("Tarea Administrativa");

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tarea t= tareas.get(position);

       new MaterialAlertDialogBuilder(TareasAdministrativas.this)
                .setMessage(t.getTipo()+"\nFecha límite: "+t.getFechaFin().toString())
                .setNeutralButton(R.string.ok,null)
                .setPositiveButton(R.string.completado, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot d: snapshot.getChildren()){
                                    Tarea dummy= d.getValue(Tarea.class);
                                    if(dummy.equals(t)){

                                        dummy.setCompletado(true);
                                        d.getRef().setValue(dummy);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        tareas.remove(position);
                        tareas2.remove(position);
                        listview.invalidateViews();
                    }
                })
                .show();
    }
}