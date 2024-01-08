package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarioReuniones extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayList<String> reuniones= new ArrayList<String>(Arrays.asList("14/12/2024","15/03/2024","01/05/2024","01/02/2024","29/12/2023"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_reuniones);
        getSupportActionBar().setTitle(R.string.reuniones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview = findViewById(R.id.lista);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,reuniones);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.convocatoria))
                .setMessage(getString(R.string.invitacionReunion)+"\n"+getString(R.string.fecha)+reuniones.get(position))
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}