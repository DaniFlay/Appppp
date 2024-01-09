package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.aplicacioncolegio.clases.Usuario;

public class Reuniones extends AppCompatActivity implements View.OnClickListener {
    Button botonCal, botonNot, botonCon;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniones);
        getSupportActionBar().setTitle(R.string.reuniones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        botonCal = findViewById(R.id.botonCalendario);
        botonNot= findViewById(R.id.botonNotificaciones);
        botonCon= findViewById(R.id.botonConvocar);
        botonCal.setOnClickListener(this);
        botonNot.setOnClickListener(this);
        botonCon.setOnClickListener(this);
        usuario= getIntent().getParcelableExtra(getString(R.string.usuario));
        if(usuario.getPuesto().equals(getString(R.string.jefe))){
            botonCon.setVisibility(View.VISIBLE);
            botonCon.setClickable(true);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.botonCalendario){
            Intent intent = new Intent(Reuniones.this, CalendarioReuniones.class);
            startActivity(intent);
        }
        else if(v.getId()== R.id.botonNotificaciones){
            Intent intent = new Intent(Reuniones.this, ReunionesNotificaciones.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Reuniones.this, ConvocarReunion.class);
            intent.putExtra(getString(R.string.usuario),(Parcelable) usuario);
            startActivity(intent);
        }
    }
}