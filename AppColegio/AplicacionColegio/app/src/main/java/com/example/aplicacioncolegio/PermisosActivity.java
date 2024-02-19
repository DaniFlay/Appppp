package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.aplicacioncolegio.R;
import com.example.aplicacioncolegio.clases.Permiso;
import com.example.aplicacioncolegio.clases.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PermisosActivity extends AppCompatActivity implements View.OnClickListener {
Usuario usuario;
DatabaseReference ref;
ArrayList<Permiso> permisos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos);
        permisos= new ArrayList<>();
        usuario= getIntent().getParcelableExtra("usuario");
        getSupportActionBar().setTitle(R.string.mispaermisos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button b3= findViewById(R.id.permisosSolicitar);
        Button b2=  findViewById(R.id.estados);
        Button b=  findViewById(R.id.atras);
        ref= FirebaseDatabase.getInstance().getReference("Permiso");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    Permiso p= d.getValue(Permiso.class);
                    permisos.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        b.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.atras){
            Intent intent = new Intent(PermisosActivity.this,MenuPrincipal.class);
            intent.putExtra("usuario",(Parcelable) usuario);
            startActivity(intent);

        }
        else if(v.getId()== R.id.estados ){
            Intent intent = new Intent(PermisosActivity.this, EstadosPermisosActivity.class);
            intent.putExtra("usuario",(Parcelable) usuario);
            intent.putParcelableArrayListExtra("permisos", permisos);
            startActivity(intent);
        }
        else if (v.getId()== R.id.permisosSolicitar) {
            Intent intent= new Intent(PermisosActivity.this,NuevoPermiso.class);
            intent.putExtra("usuario",(Parcelable) usuario);
            startActivity(intent);
        }
    }
}
