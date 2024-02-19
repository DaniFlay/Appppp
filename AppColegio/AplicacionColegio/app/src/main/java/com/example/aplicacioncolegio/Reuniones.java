package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Reunion;
import com.example.aplicacioncolegio.clases.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reuniones extends AppCompatActivity implements View.OnClickListener {
    Button botonCal, botonNot, botonCon;
    Usuario usuario;
    DatabaseReference ref;
    ArrayList<Reunion> reuniones, notificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniones);
        getSupportActionBar().setTitle(R.string.reuniones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notificaciones= new ArrayList<>();
        reuniones= new ArrayList<>();
        botonCal = findViewById(R.id.botonCalendario);
        botonNot= findViewById(R.id.botonNotificaciones);
        botonCon= findViewById(R.id.botonConvocar);
        botonCal.setOnClickListener(this);
        botonNot.setOnClickListener(this);
        botonCon.setOnClickListener(this);
        usuario= getIntent().getParcelableExtra("usuario");
        if(usuario.getPuesto().equals(getString(R.string.jefe))){
            botonCon.setVisibility(View.VISIBLE);
            botonCon.setClickable(true);
        }
        ref= FirebaseDatabase.getInstance().getReference("Reunion");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Reunion r= d.getValue(Reunion.class);
                        if(r.getEstado().equals("aceptado")&&r.getUsuario().getCorreo().equals(usuario.getCorreo())){
                            reuniones.add(r);
                        }
                        if(r.getEstado().equals("pendiente")&&r.getUsuario().getCorreo().equals(usuario.getCorreo())){
                            notificaciones.add(r);
                        }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.botonCalendario){
            Intent intent = new Intent(Reuniones.this, CalendarioReuniones.class);
            intent.putParcelableArrayListExtra("reuniones",reuniones);
            startActivity(intent);
        }
        else if(v.getId()== R.id.botonNotificaciones){
            Intent intent = new Intent(Reuniones.this, ReunionesNotificaciones.class);
            intent.putParcelableArrayListExtra("notificaciones",notificaciones);
            intent.putExtra("usuario",(Parcelable) usuario);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Reuniones.this, ConvocarReunion.class);
            intent.putExtra("usuario",(Parcelable) usuario);
            startActivity(intent);
        }
    }
}