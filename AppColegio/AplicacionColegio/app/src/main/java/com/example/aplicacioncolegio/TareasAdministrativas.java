package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TareasAdministrativas extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    DatabaseReference ref;
    ArrayList<Tarea> tareas = new ArrayList<Tarea>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_administrativas);
            tareas.add(new Tarea("Entrega de programación", "14/06/2024"));
            tareas.add(new Tarea("Firma de actas", "21/08/2023"));
            tareas.add(new Tarea("Entrega de hoja mensual", "01/05/2024"));
            tareas.add(new Tarea("Entrega de programación", "01/01/2024"));
            tareas.add(new Tarea("Firma de actas", "11/06/2023"));
            tareas.add(new Tarea("Firma de actas", "23/10/2023"));
            tareas.add(new Tarea("Entrega de hoja mensual", "09/12/2023"));
            tareas.add(new Tarea("Entrega de programación", "15/11/2023"));
            tareas.add(new Tarea("Firma de actas", "17/05/2023"));
            tareas.add(new Tarea("Entrega de hoja mensual", "12/03/2023"));
        listview= findViewById(R.id.listaTareas);
        ref= FirebaseDatabase.getInstance().getReference().child("Tarea Administrativa");
        Tarea t2= new Tarea();
        t2.setTipo("blabla");
        t2.setFechaFin("blablabla");
        ref.push().setValue(t2);
        ArrayAdapter<Tarea> adapter= new ArrayAdapter<Tarea>(this, android.R.layout.simple_dropdown_item_1line,tareas);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        getSupportActionBar().setTitle(R.string.mistareas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tarea t= tareas.get(position);
        ref.push().setValue(t);
       /* new MaterialAlertDialogBuilder(TareasAdministrativas.this)
                .setMessage(t.getTipo()+"\nFecha límite: "+t.getFechaFin().toString())
                .setNeutralButton(R.string.ok,null)
                .setPositiveButton(R.string.completado, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tareas.remove(position);
                        listview.invalidateViews();
                    }
                })
                .show();*/
    }
}