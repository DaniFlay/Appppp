package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.example.aplicacioncolegio.clases.Mensaje;
import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Tarea;
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
    HashSet<Usuario> profesorado= new HashSet<Usuario>();
    boolean[] checked;
    DatabaseReference ref;
    ArrayList<Usuario> usuarios, usuariosAvisos;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fijar_tareas);
        usuario=getIntent().getParcelableExtra("usuario");
        usuarios= new ArrayList<>();
        getSupportActionBar().setTitle(R.string.fijarTarea);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner= findViewById(R.id.spinnerTarea);
        enviar= findViewById(R.id.boton_enviar);
        botonPart= findViewById(R.id.botonAñadir);
        botonFecha= findViewById(R.id.botonFecha);
        part= findViewById(R.id.participantes);
        fecha= findViewById(R.id.fecha);
        ArrayList<String> tareas= new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.tareas)));
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,tareas);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        enviar.setOnClickListener(this);
        botonPart.setOnClickListener(this);
        botonFecha.setOnClickListener(this);
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
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
                    part.setText(recibidores);
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
            ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String date= dtf.format(now);
            for(int i=0;i<usuariosAvisos.size();i++){
                Notificacion notificacion= new Notificacion(usuario,usuariosAvisos.get(i),getString(R.string.tarea),spinner.getSelectedItem().toString(),date,false);
                ref.push().setValue(notificacion);
            }
            ref=FirebaseDatabase.getInstance().getReference("Tarea Administrativa");


            Snackbar.make(v.getContext(),v,getString( R.string.sehaenviadoconexito),Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i=0; i<usuariosAvisos.size();i++){
                                Tarea m= new Tarea(usuariosAvisos.get(i),spinner.getSelectedItem().toString(),fecha.getText().toString(),false);
                                ref.push().setValue(m);
                            }
                            Intent intent= new Intent (FijarTareas.this, MenuPrincipal.class);
                            intent.putExtra("usuario",(Parcelable) usuario);
                            startActivity(intent);
                        }
                    }).show();
        }
    }
}