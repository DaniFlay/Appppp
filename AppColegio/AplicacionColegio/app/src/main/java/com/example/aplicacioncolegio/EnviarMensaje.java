package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.aplicacioncolegio.clases.Aviso;
import com.example.aplicacioncolegio.clases.Mensaje;
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
import java.util.HashSet;
import java.util.Set;

public class EnviarMensaje extends AppCompatActivity implements View.OnClickListener {
    ImageButton añadir;
    Button enviar;
    EditText part, asunto, mensaje;
    HashSet<Usuario> profesorado= new HashSet<Usuario>();
    boolean[] checked;
    int[] botones;
    String[] texto;
    Usuario usuario;
    DatabaseReference ref;
    ArrayList<Usuario> usuarios,usuariosAvisos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensaje);
        texto= getIntent().getStringArrayExtra("texto");
        botones= getIntent().getIntArrayExtra("botones");
        añadir= findViewById(R.id.botonAñadir);
        enviar= findViewById(R.id.boton_enviar);
        part= findViewById(R.id.participantes);
        asunto= findViewById(R.id.asunto);
        mensaje= findViewById(R.id.mensaje);
        añadir.setOnClickListener(this);
        enviar.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.Mensaje);
        usuario= getIntent().getParcelableExtra("usuario");
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
        if(v.getId()==R.id.botonAñadir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.eligeAlosParticipantes));
            String[] participantes = new String[usuarios.size()];
            checked = new boolean[usuarios.size()];
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
        }else{
            ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String fecha= dtf.format(now);
            for(int i=0;i<usuariosAvisos.size();i++){
                Notificacion notificacion= new Notificacion(usuario,usuariosAvisos.get(i),getString(R.string.aviso),mensaje.getText().toString(),fecha,false);
                ref.push().setValue(notificacion);
            }
            Mensaje m= new Mensaje(usuariosAvisos,mensaje.getText().toString());
            ref=FirebaseDatabase.getInstance().getReference(getString(R.string.avisos));
            ref.push().setValue(m);
            Snackbar.make(v, R.string.sehaenviadoconexito,Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            part.setText("");
                            asunto.setText("");
                            mensaje.setText("");
                            Intent intent= new Intent (EnviarMensaje.this, MenuPrincipal.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                    })
                    .show();


        }
    }
}