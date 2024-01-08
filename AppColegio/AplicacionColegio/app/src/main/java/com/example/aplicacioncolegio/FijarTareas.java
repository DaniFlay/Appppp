package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FijarTareas extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
EditText part, fecha;
ImageButton botonPart, botonFecha;
Button enviar;
Spinner spinner;
int[] botones;
String[] texto;
    Set<String> profesorado= new HashSet<String>();
    boolean[] checked= {false,false,false,false,false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fijar_tareas);
        botones= getIntent().getIntArrayExtra("botones");
        texto= getIntent().getStringArrayExtra("texto");
        getSupportActionBar().setTitle(R.string.fijarTarea);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner= findViewById(R.id.spinnerTarea);
        enviar= findViewById(R.id.boton_enviar);
        botonPart= findViewById(R.id.botonAñadir);
        botonFecha= findViewById(R.id.botonFecha);
        part= findViewById(R.id.participantes);
        fecha= findViewById(R.id.fecha);
        ArrayList<String> tareas= new ArrayList<>(Arrays.asList("Firma de actas","Entrega de circulares","Reparto de horarios","Entrega de programaciones","Entrega de normas"));
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,tareas);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        enviar.setOnClickListener(this);
        botonPart.setOnClickListener(this);
        botonFecha.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.botonAñadir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.eligeAlosParticipantes));
            String[] participantes = {"Jose Antonio Perez", "Miguel Rojo", "Jose Maria Tejero", "Manuel Jose Gonzalez", "Leonardo Bosques"};
            builder.setMultiChoiceItems(participantes, checked, new DialogInterface.OnMultiChoiceClickListener() {
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
                    part.setText(profesorado.toString());
                }
            });
            builder.setNegativeButton(R.string.cancelar, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (v.getId()== R.id.botonFecha) {
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

        }else{
            part.setText("");
            fecha.setText("");
            Snackbar.make(v.getContext(),v,getString( R.string.sehaenviadoconexito),Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent= new Intent (FijarTareas.this, MenuPrincipal.class);
                            intent.putExtra("botones", botones);
                            intent.putExtra("texto",texto);
                            startActivity(intent);
                        }
                    }).show();
        }
    }
}