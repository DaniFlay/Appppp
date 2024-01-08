package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EnviarMensaje extends AppCompatActivity implements View.OnClickListener {
    ImageButton añadir;
    Button enviar;
    EditText part, asunto, mensaje;
    Set<String> profesorado= new HashSet<String>();
    boolean[] checked= {false,false,false,false,false};
    int[] botones;
    String[] texto;
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


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.botonAñadir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.eligeAlosParticipantes));
            String[] participantes = {"Jose Antonio Perez", "Miguel Rojo", "Jose Maria Tejero", "Manuel Jose Gonzalez", "Leonardo Bosques"};
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
            Snackbar.make(v, R.string.sehaenviadoconexito,Snackbar.LENGTH_LONG)
                    .setAction(R.string.aceptar, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            part.setText("");
                            asunto.setText("");
                            mensaje.setText("");
                            Intent intent= new Intent (EnviarMensaje.this, MenuPrincipal.class);
                            intent.putExtra("botones", botones);
                            intent.putExtra("texto",texto);
                            startActivity(intent);
                        }
                    })
                    .show();


        }
    }
}