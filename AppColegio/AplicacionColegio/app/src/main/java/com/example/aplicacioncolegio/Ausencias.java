package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Ausencias extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button enviar;
    ImageButton f1,f2,h1,h2;
    EditText nombre,apellidos,fechaI,fechaF,horaI,horaF;
    Spinner spinner;
    Usuario usuario;
    Ausencia a;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ausencias);
        getSupportActionBar().setTitle(R.string.ausencias);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usuario = getIntent().getParcelableExtra(getString(R.string.usuario));
        nombre= (EditText) findViewById(R.id.nombre);
        nombre.setText(usuario.getNombre());
        apellidos= (EditText)findViewById(R.id.apellidos);
        apellidos.setText(usuario.getApellidos());
        fechaI= (EditText)findViewById(R.id.fechaInicio);
        fechaF=(EditText) findViewById(R.id.fechaFin);
        horaI = (EditText)findViewById(R.id.horaInicio);
        horaF= (EditText)findViewById(R.id.horaFin);
        enviar= (Button) findViewById(R.id.enviar);
        f1= (ImageButton) findViewById(R.id.botonFechaI);
        f2= (ImageButton) findViewById(R.id.botonFechaF);
        h1= (ImageButton) findViewById(R.id.botonHoraI);
        h2= (ImageButton) findViewById(R.id.botonHoraF);
        spinner= (Spinner) findViewById(R.id.spinnerRazon);
        enviar.setOnClickListener(this);
        f1.setOnClickListener(this);
        f2.setOnClickListener(this);
        h1.setOnClickListener(this);
        h2.setOnClickListener(this);
        h1.setEnabled(false);
        h2.setEnabled(false);
        h1.setClickable(false);
        h2.setClickable(false);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ausencias));
        spinner.setAdapter(adaptador);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.botonFechaI){
            Calendar c= Calendar.getInstance();
            int dia= c.get(Calendar.DAY_OF_MONTH);
            int mes= c.get(Calendar.MONTH);
            int anyo= c.get(Calendar.YEAR);
            DatePickerDialog elegirFecha= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String fecha1= dayOfMonth+"/"+month+"/"+year;
                    fechaI.setText(fecha1);
                }
            },anyo,mes,dia);
            elegirFecha.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            elegirFecha.show();
        }
        else if(v.getId()==R.id.botonFechaF){
            Calendar c= Calendar.getInstance();
            int dia= c.get(Calendar.DAY_OF_MONTH);
            int mes= c.get(Calendar.MONTH);
            int anyo= c.get(Calendar.YEAR);
            DatePickerDialog elegirFecha= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String fecha1= dayOfMonth+"/"+month+"/"+year;
                    fechaF.setText(fecha1);
                }
            },anyo,mes,dia);
            elegirFecha.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            elegirFecha.show();
        }
        else if(v.getId()==R.id.botonHoraI){
            Calendar c= Calendar.getInstance();
            int hora= c.get(Calendar.HOUR);
            int min= c.get(Calendar.MINUTE);
            TimePickerDialog elegirHora= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hora, int min) {
                    String hora1= hora+":"+min;
                    horaI.setText(hora1);
                }
            },hora,min,true);
            elegirHora.show();
        }
        else if(v.getId()==R.id.botonHoraF){
            Calendar c= Calendar.getInstance();
            int hora= c.get(Calendar.HOUR);
            int min= c.get(Calendar.MINUTE);
            TimePickerDialog elegirHora= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hora, int min) {
                    String hora1= hora+":"+min;
                    horaF.setText(hora1);
                }
            },hora,min,true);
            elegirHora.show();
        }
        else if(v.getId()==R.id.enviar){
            Snackbar.make(v.getContext(),v,getString( R.string.sehaenviadoconexito),Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            a= new Ausencia(usuario, spinner.getSelectedItem().toString(),fechaI.getText().toString(),fechaF.getText().toString(),horaI.getText().toString(),horaF.getText().toString());
                            nombre.setText("");
                            apellidos.setText("");
                            fechaI.setText("");
                            fechaF.setText("");
                            horaF.setText("");
                            horaI.setText("");

                            Intent intent= new Intent (Ausencias.this, MenuPrincipal.class);
                            intent.putExtra(getString(R.string.usuario),(Parcelable) usuario);
                            ref= FirebaseDatabase.getInstance().getReference().child(getString(R.string.ausencia));
                            ref.push().setValue(a);
                            startActivity(intent);
                        }
                    }).show();



        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==1){
            h1.setEnabled(true);
            h2.setEnabled(true);
            h1.setClickable(true);
            h2.setClickable(true);
        }
        else{
            h1.setEnabled(false);
            h2.setEnabled(false);
            h1.setClickable(false);
            h2.setClickable(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}