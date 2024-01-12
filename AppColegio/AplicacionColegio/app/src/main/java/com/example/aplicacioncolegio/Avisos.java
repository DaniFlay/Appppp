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

import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Avisos extends AppCompatActivity implements View.OnClickListener{
    ImageButton añadir;
    Button enviar;
    EditText part, mensaje;
    Set<String> profesorado= new HashSet<String>();
    Usuario usuario;
    ArrayList<Usuario> usuarios;
    HashSet<Usuario> usuariosAvisos;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);
        usuario= getIntent().getParcelableExtra(getString(R.string.usuario));
        añadir= findViewById(R.id.botonAñadir);
        enviar= findViewById(R.id.boton_enviar);
        part= findViewById(R.id.participantes);
        mensaje= findViewById(R.id.mensaje);
        añadir.setOnClickListener(this);
        enviar.setOnClickListener(this);
        usuarios = new ArrayList<>();
        usuariosAvisos= new HashSet<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.avisoNuevo);
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("PPPPPPPP",String.valueOf(snapshot.getChildrenCount()));
                for (DataSnapshot d: snapshot.getChildren()){
                    Usuario dummy= d.getValue(Usuario.class);
                    Log.d("LAAAAAAAAAAAAAAAAA",dummy.getNombre()+"");
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
        if(v.getId()==R.id.botonAñadir){
            Log.d("PPPPPPPP","pRUEBAS");
            Log.d("PPPPPPPP",ref.getKey()+"");

            Log.d("poqwoipfiasof",usuarios.toString()+"");
            for(Usuario u: usuarios){
                Log.d("qqqqqqqqqqqq",u.getNombre()+"");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.eligeAlosParticipantes));
            boolean[] checked= new boolean[usuarios.size()];
            String[] participantes= new String[usuarios.size()];
            for(int i=0; i<participantes.length;i++){
                String dummy = usuarios.get(i).getNombre() +" "+usuarios.get(i).getApellidos();
                participantes[i]=dummy;
            }
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
        }else{
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