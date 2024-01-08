package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class InformeGuardia extends AppCompatActivity implements View.OnClickListener {
    Button  botonGuardar;
    ImageButton botonFecha;
    TextView fechaTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_guardia);
        fechaTexto= findViewById(R.id.fecha);
        botonFecha= findViewById(R.id.botonFecha);
        botonGuardar= findViewById(R.id.boton_guardar);
        botonFecha.setOnClickListener(this);
        botonGuardar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       int[] botones = getIntent().getIntArrayExtra("botones");
       String[] texto = getIntent().getStringArrayExtra("texto");
        if(v.getId()==R.id.botonFecha){
            Calendar c= Calendar.getInstance();
            int dia= c.get(Calendar.DAY_OF_MONTH);
            int mes= c.get(Calendar.MONTH);
            int anyo= c.get(Calendar.YEAR);
            DatePickerDialog elegirFecha= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String fecha= dayOfMonth+"/"+month+"/"+year;
                    fechaTexto.setText(fecha);
                }
            },anyo,mes,dia);
            elegirFecha.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            elegirFecha.show();
        }
        else{
            Snackbar.make(v, R.string.guardado,Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(InformeGuardia.this,MenuPrincipal.class);
                            intent.putExtra("botones",botones);
                            intent.putExtra("texto",texto);
                            startActivity(intent);
                        }
                    })
                    .show();

        }
    }
}