package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aplicacioncolegio.clases.Informe;
import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;


public class InformeGuardia extends AppCompatActivity implements View.OnClickListener {
    Button  botonGuardar;
    ImageButton botonFecha, botonParticipantes;
    TextView fechaTexto;
    EditText observaciones;
    Usuario usuario, usuarioAviso;
    ArrayList<Usuario> usuarios;
    DatabaseReference ref;
    String[] participantes;
    int eleccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_guardia);
        usuario= getIntent().getParcelableExtra("usuario");
        observaciones= findViewById(R.id.observaciones);
        fechaTexto= findViewById(R.id.fecha);
        botonParticipantes= findViewById(R.id.botonAñadir);
        botonFecha= findViewById(R.id.botonFecha);
        botonGuardar= findViewById(R.id.boton_guardar);
        botonParticipantes.setOnClickListener(this);
        botonFecha.setOnClickListener(this);
        botonGuardar.setOnClickListener(this);
        ref= FirebaseDatabase.getInstance().getReference("usuario");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Usuario dummy= d.getValue(Usuario.class);
                    usuarios.add(dummy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    String fecha= dayOfMonth+"/"+month+"/"+year;
                    fechaTexto.setText(fecha);
                }
            },anyo,mes,dia);
            elegirFecha.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            elegirFecha.show();
        } else if (v.getId()==R.id.botonAñadir) {
            participantes= new String[usuarios.size()];
            for(int i=0; i<participantes.length;i++){
                participantes[i]= usuarios.get(i).getNombre()+" "+usuarios.get(i).getApellidos();
                eleccion=-1;
            }
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setSingleChoiceItems(participantes, eleccion, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eleccion=which;
                    for(Usuario u:usuarios){
                        if((u.getNombre()+" "+u.getApellidos()).equals(participantes[which])){
                            usuarioAviso=u;
                        }
                    }
                }
            });

        } else{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String fecha= dtf.format(now);
            Informe informe= new Informe(usuarioAviso,usuario,fecha,observaciones.getText().toString());
            ref= FirebaseDatabase.getInstance().getReference(getString(R.string.informe));
            ref.push().setValue(informe);
            ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
            Notificacion notificacion= new Notificacion(usuario, usuarioAviso,getString(R.string.notificacion),observaciones.getText().toString(),fecha,false);
            ref.push().setValue(notificacion);
            Snackbar.make(v, R.string.guardado,Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(InformeGuardia.this,MenuPrincipal.class);
                            intent.putExtra("usuario",(Parcelable) usuario);
                            startActivity(intent);
                        }
                    })
                    .show();

        }
    }
}