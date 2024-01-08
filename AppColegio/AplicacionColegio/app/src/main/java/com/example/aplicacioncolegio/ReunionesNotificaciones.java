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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ReunionesNotificaciones extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayList<String> reuniones = new ArrayList<>(Arrays.asList("14/10/2023","15/02/2024","21/03/2024","22/05/2024","11/01/2024","09/11/2024","01/12/2024","25/03/2024"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniones_notificaciones);
        getSupportActionBar().setTitle(R.string.notificaciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview= findViewById(R.id.lista);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,reuniones);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.convocatoria))
                .setMessage(getString(R.string.invitacionReunion)+"\n"+getString(R.string.fecha)+reuniones.get(position)+"\n"+getString(R.string.aceotaorechaza))
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reuniones.remove(position);
                        listview.invalidateViews();
                        Snackbar.make(parent, R.string.aceptado,Snackbar.LENGTH_LONG)
                                .setAction(R.string.aceptar, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    }
                })
                .setNegativeButton(R.string.rechazar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reuniones.remove(position);
                        listview.invalidateViews();
                        Snackbar.make(parent, R.string.rechazado,Snackbar.LENGTH_LONG)
                                .setAction(R.string.aceptar, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    }
                })
                .show();

    }

}