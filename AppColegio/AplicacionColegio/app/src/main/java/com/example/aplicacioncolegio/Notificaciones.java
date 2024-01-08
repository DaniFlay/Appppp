package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class Notificaciones extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview ;
    ArrayList<String> notificaiones= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.misnotificaciones);
        notificaiones.add("Reunión");
        notificaiones.add("Guardia");
        notificaiones.add("Guardia");
        notificaiones.add("Reunión");
        notificaiones.add("Guardia");
        notificaiones.add("Reunión");
        notificaiones.add("Guardia");
        notificaiones.add("Reunión");
        listview= findViewById(R.id.listaNotificaciones);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,notificaiones);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String m= notificaiones.get(position);
        new MaterialAlertDialogBuilder(this)
                .setMessage(m)
                .setTitle(R.string.notificacion)
                .setPositiveButton(R.string.ok,null)
                .show();
        view.setBackgroundColor(Color.GRAY);
    }

}