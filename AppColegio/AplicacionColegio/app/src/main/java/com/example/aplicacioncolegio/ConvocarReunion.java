package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ConvocarReunion extends AppCompatActivity implements View.OnClickListener {
    EditText participantess, fecha, horaa, observaciones;
    Button enviar;
    ImageButton añadir, bFecha, bHora;
    Set<String> profesorado= new HashSet<String>();
    boolean[] checked = {false,false,false,false,false};
    String[] texto;
    int[] botones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocar_reunion);
        texto= getIntent().getStringArrayExtra("texto");
        botones = getIntent().getIntArrayExtra("botones");
        participantess= findViewById(R.id.participantes);
        fecha= findViewById(R.id.fecha);
        horaa= findViewById(R.id.horaInicio);
        observaciones= findViewById(R.id.observaciones);
        enviar= findViewById(R.id.boton_enviar);
        añadir = findViewById(R.id.botonAñadir);
        bFecha= findViewById(R.id.botonFecha);
        bHora= findViewById(R.id.botonHoraI);
        getSupportActionBar().setTitle(R.string.convocarreuncion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enviar.setOnClickListener(this);
        añadir.setOnClickListener(this);
        bFecha.setOnClickListener(this);
        bHora.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.botonFecha){
            Calendar c= Calendar.getInstance();
            int dia= c.get(Calendar.DAY_OF_MONTH);
            int mes= c.get(Calendar.MONTH);
            int anyo= c.get(Calendar.YEAR);
            DatePickerDialog elegirFecha= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String fecha1= dayOfMonth+"/"+month+"/"+year;
                    fecha.setText(fecha1);
                }
            },anyo,mes,dia);
            elegirFecha.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            elegirFecha.show();
        }
        else if(v.getId()== R.id.botonHoraI){
            Calendar c= Calendar.getInstance();
            int hora= c.get(Calendar.HOUR);
            int min= c.get(Calendar.MINUTE);
            TimePickerDialog elegirHora= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hora, int min) {
                    String hora1= hora+":"+min;
                    horaa.setText(hora1);
                }
            },hora,min,true);
            elegirHora.show();
        }else if(v.getId()== R.id.botonAñadir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.eligeAlosParticipantes));
            String[] participantes = {"Jose Antonio Perez", "Miguel Rojo", "Jose Maria Tejero", "Manuel Jose Gonzalez", "Leonardo Bosques"};
            builder.setMultiChoiceItems(participantes, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked){
                        profesorado.add(participantes[which]);
                        checked[which]=true;
                    }
                    else{
                        profesorado.remove(participantes[which]);
                        checked[which]=false;
                    }

                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    participantess.setText(profesorado.toString());
                }
            });
            builder.setNegativeButton(R.string.cancelar, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            Snackbar.make(v, R.string.sehaenviadoconexito,Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            participantess.setText("");
                            fecha.setText("");
                            horaa.setText("");
                            observaciones.setText("");
                            Intent intent = new Intent(ConvocarReunion.this,Reuniones.class);
                            intent.putExtra("texto",texto);
                            intent.putExtra("botones",botones);
                            startActivity(intent);
                        }
                    })
                    .show();

        }
    }
}