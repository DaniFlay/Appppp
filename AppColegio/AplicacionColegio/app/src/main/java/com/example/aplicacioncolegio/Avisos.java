package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.aplicacioncolegio.clases.Aviso;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Avisos extends AppCompatActivity implements View.OnClickListener{
    ImageButton añadir;
    Button enviar;
    EditText part, mensaje;
    HashSet<Usuario> profesorado= new HashSet<Usuario>();
    Usuario usuario;
    ArrayList<Usuario> usuarios;
    ArrayList<Usuario> usuariosAvisos;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);
        usuario= getIntent().getParcelableExtra("usuario");
        añadir= findViewById(R.id.botonAñadir);
        enviar= findViewById(R.id.boton_enviar);
        part= findViewById(R.id.participantes);
        mensaje= findViewById(R.id.mensaje);
        añadir.setOnClickListener(this);
        enviar.setOnClickListener(this);
        usuarios = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.avisoNuevo);
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()){
                    Usuario dummy= d.getValue(Usuario.class);
                    if(dummy.getCiclo()!=null) {
                        if (dummy.getCiclo().getNombre().equals(usuario.getCiclo().getNombre()) && dummy.getPuesto().equals(getString(R.string.docente))) {
                            usuarios.add(dummy);
                        }
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
        if(v.getId()==R.id.botonAñadir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.eligeAlosParticipantes));
            boolean[] checked= new boolean[usuarios.size()];
            String[] participantes= new String[usuarios.size()];
            for(int i=0; i<participantes.length;i++){
                Usuario dummy = usuarios.get(i);
                participantes[i]=dummy.getNombre()+" "+dummy.getApellidos();
            }
            Log.d("setususu", Arrays.toString(participantes));
            Log.d("listususu",usuarios.toString());
            builder.setMultiChoiceItems(participantes, checked, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked){

                            String[] nombreApellidos= participantes[which].split(" ");
                            if(nombreApellidos.length==3){
                                if(usuarios.get(which).getNombre().equals(nombreApellidos[0])&& usuarios.get(which).getApellidos().equals(nombreApellidos[1]+" "+nombreApellidos[2])|| usuarios.get(which).getNombre().equals(nombreApellidos[0]+" "+nombreApellidos[1])&& usuarios.get(which).getApellidos().equals(nombreApellidos[2])){
                                    profesorado.add(usuarios.get(which));

                                    checked[which]=true;

                                }
                            }
                            else if(nombreApellidos.length==2){
                                if(usuarios.get(which).getNombre().equals(nombreApellidos[0])&& usuarios.get(which).getApellidos().equals(nombreApellidos[1])){
                                    profesorado.add(usuarios.get(which));
                                    checked[which]=true;
                                }
                            }
                            else if(nombreApellidos.length==4){
                                if(usuarios.get(which).getNombre().equals(nombreApellidos[0]+" "+nombreApellidos[1])&& usuarios.get(which).getApellidos().equals(nombreApellidos[2]+" "+nombreApellidos[3])){
                                    profesorado.add(usuarios.get(which));
                                    checked[which]=true;
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

                    Log.d("usuarios",usuariosAvisos.toString());
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
        }else{
            if(!profesorado.isEmpty()){
                ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime now = LocalDateTime.now();
                String fecha= dtf.format(now);
                for(int i=0;i<usuariosAvisos.size();i++){
                    Notificacion notificacion= new Notificacion(usuario,usuariosAvisos.get(i),getString(R.string.aviso),mensaje.getText().toString(),fecha,false);
                    ref.push().setValue(notificacion);
                }
                Aviso aviso= new Aviso(usuario,usuariosAvisos,mensaje.getText().toString());
                ref=FirebaseDatabase.getInstance().getReference(getString(R.string.avisos));
                ref.push().setValue(aviso);
            }
            Snackbar.make(v.getContext(),v,getString( R.string.sehaenviadoconexito),Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            part.setText("");
                            mensaje.setText("");
                            Intent intent= new Intent (Avisos.this, MenuPrincipal.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }
                    }).show();



        }
    }
}