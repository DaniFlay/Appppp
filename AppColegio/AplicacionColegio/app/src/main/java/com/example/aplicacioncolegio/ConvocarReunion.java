package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
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

import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Reunion;
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
import java.util.HashSet;
import java.util.Set;

public class ConvocarReunion extends AppCompatActivity implements View.OnClickListener {
    EditText participantess, fecha, horaa, observaciones;
    Button enviar;
    ImageButton añadir, bFecha, bHora;
    HashSet<Usuario> profesorado= new HashSet<Usuario>();
    boolean[] checked ;
    String[] texto;
    int[] botones;
    Usuario usuario;
    DatabaseReference ref;
    ArrayList<Usuario> usuarios;
    ArrayList<Usuario> usuariosAvisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocar_reunion);
        texto= getIntent().getStringArrayExtra("texto");
        botones = getIntent().getIntArrayExtra("botones");
        usuario= getIntent().getParcelableExtra("usuario");
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
        usuarios= new ArrayList<>();
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    usuarios.add(d.getValue(Usuario.class));
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
            checked= new boolean[usuarios.size()];
            String[] participantes = new String[usuarios.size()];
            for(int i=0; i<participantes.length;i++){
                Usuario dummy = usuarios.get(i);
                participantes[i]=dummy.getNombre()+" "+dummy.getApellidos();
            }
            builder.setMultiChoiceItems(participantes, checked, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked){
                        for(int i=0; i<usuarios.size();i++){
                            String[] nombreApellidos= participantes[which].split(" ");
                            if(nombreApellidos.length==3){
                                if(usuarios.get(i).getNombre().equals(nombreApellidos[0])&& usuarios.get(i).getApellidos().equals(nombreApellidos[1]+" "+nombreApellidos[2])){
                                    profesorado.add(usuarios.get(i));
                                    checked[which]=true;
                                }
                            }
                            else{
                                if(usuarios.get(i).getNombre().equals(nombreApellidos[0])&& usuarios.get(i).getApellidos().equals(nombreApellidos[1])){
                                    profesorado.add(usuarios.get(i));
                                    checked[which]=true;
                                }
                            }

                        }
                    }
                    else{
                        for(int i=0; i<usuarios.size();i++){
                            String[] nombreApellidos= participantes[which].split(" ");
                            if(nombreApellidos.length==3){
                                if(usuarios.get(i).getNombre().equals(nombreApellidos[0])&& usuarios.get(i).getApellidos().equals(nombreApellidos[1]+" "+nombreApellidos[2])){
                                    profesorado.remove(usuarios.get(i));
                                    checked[which]=false;
                                }
                            }
                            else{
                                if(usuarios.get(i).getNombre().equals(nombreApellidos[0])&& usuarios.get(i).getApellidos().equals(nombreApellidos[1])){
                                    profesorado.remove(usuarios.get(i));
                                    checked[which]=false;
                                }
                            }
                        }
                    }

                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    usuariosAvisos= new ArrayList<>(profesorado);
                    String recibidores="[";
                    for(int i=0;i<usuariosAvisos.size()-1;i++){
                        recibidores+=usuariosAvisos.get(i).nombreApellidos()+",";
                    }
                    recibidores+=usuariosAvisos.get(usuariosAvisos.size()-1).nombreApellidos()+"]";
                    participantess.setText(recibidores);
                }
            });
            builder.setNegativeButton(R.string.cancelar, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            ref= FirebaseDatabase.getInstance().getReference(getString(R.string.reuniones));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String date= dtf.format(now);
            Reunion reunion= new Reunion(usuariosAvisos,date,horaa.getText().toString(),observaciones.getText().toString());
            ref.push().setValue(reunion);
            ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
            for(Usuario u:usuariosAvisos){
                Notificacion notificacion = new Notificacion(usuario, u,getString(R.string.reunion),observaciones.getText().toString(),date,false);
                ref.push().setValue(notificacion);
            }
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