package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.aplicacioncolegio.R;

public class PermisosActivity extends AppCompatActivity implements View.OnClickListener {
    int[] botones;
    String[] texto ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos);
        botones= getIntent().getIntArrayExtra("botones");
        texto= getIntent().getStringArrayExtra("texto");
        getSupportActionBar().setTitle(R.string.mispaermisos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button b3= findViewById(R.id.permisosSolicitar);
        Button b2=  findViewById(R.id.estados);
        Button b=  findViewById(R.id.atras);

        b.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.atras){
            Intent intent = new Intent(PermisosActivity.this,MenuPrincipal.class);
            intent.putExtra("texto",texto);
            intent.putExtra("botones",botones);
            startActivity(intent);

        }
        else if(v.getId()== R.id.estados ){
            Intent intent = new Intent(PermisosActivity.this, EstadosPermisosActivity.class);
            startActivity(intent);
        }
        else if (v.getId()== R.id.permisosSolicitar) {
            Intent intent= new Intent(PermisosActivity.this,NuevoPermiso.class);
            startActivity(intent);
        }
    }
}
