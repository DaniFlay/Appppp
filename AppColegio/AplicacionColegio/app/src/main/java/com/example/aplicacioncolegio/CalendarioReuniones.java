package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aplicacioncolegio.clases.Reunion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarioReuniones extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayList<Reunion> reuniones;
    ArrayList<String> fechas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_reuniones);
        reuniones= getIntent().getParcelableArrayListExtra("reuniones");
        fechas= new ArrayList<>();
        for(Reunion r: reuniones){
            fechas.add(r.getFecha());
        }
        getSupportActionBar().setTitle(R.string.reuniones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview = findViewById(R.id.lista);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,fechas);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.convocatoria))
                .setMessage(getString(R.string.invitacionReunion)+": "+reuniones.get(position).getObservaciones()+"\nFecha: "+fechas.get(position)+"\nA las: "+reuniones.get(position).getHoraInicio())
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}