package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup rd;
    private RadioGroup rd2;
    private Usuario u;
    RadioButton r1,r2;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        rd= findViewById(R.id.radioGroupPuestos);
        rd2= findViewById(R.id.radioGroupSexo);

        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= findViewById(checkedId);
            }
        });

        rd2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= findViewById(checkedId);

            }
        });

        Button b = findViewById(R.id.botonRegistro);
        b.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String nombre;
        String apellidos;
        String correo;
        String contraseña;
        String contraseña2;
        Intent intent = null;
        EditText n= (EditText) findViewById(R.id.nombreRegistro);
        EditText a= (EditText) findViewById(R.id.apellidosRegistro);
        EditText c= (EditText) findViewById(R.id.correoRegistro);
        EditText pwd= (EditText) findViewById(R.id.contraseñaRegistro);
        EditText pwd2= findViewById(R.id.contraseña2Registro);
        nombre= n.getText().toString();
        apellidos= a.getText().toString();
        correo= c.getText().toString();
        contraseña= pwd.getText().toString();
        contraseña2= pwd2.getText().toString();
        if(nombre.equals("")){
            n.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            new MaterialAlertDialogBuilder(RegistroActivity.this)

                    .setMessage(getString(R.string.nombreIncompleto))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else if(apellidos.equals("")){
            a.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            new MaterialAlertDialogBuilder(RegistroActivity.this)
                    .setMessage(getString(R.string.nombreIncompleto))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else if(contraseña.equals("")){
            pwd.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            new MaterialAlertDialogBuilder(RegistroActivity.this)
                    .setMessage(getString(R.string.contraseñaIncompleta))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else if(contraseña2.equals("")){
            pwd2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            new MaterialAlertDialogBuilder(RegistroActivity.this)
                    .setMessage(getString(R.string.contraseñaIncompleta2))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else if(!contraseña2.equals(contraseña)){
            pwd2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            pwd.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            new MaterialAlertDialogBuilder(RegistroActivity.this)
                    .setMessage(getString(R.string.contraseñaNoCoincide))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else if(rd.getCheckedRadioButtonId()==-1){
            new MaterialAlertDialogBuilder(RegistroActivity.this)
                    .setMessage(getString(R.string.puestoIncompleto))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        } else if(rd2.getCheckedRadioButtonId()==-1){
            new MaterialAlertDialogBuilder(RegistroActivity.this)
                    .setMessage(getString(R.string.sexoIncompleto))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else {
            String[] datosTextuales = {nombre, apellidos, correo, contraseña};
            int[] botones = {rd.getCheckedRadioButtonId(), rd2.getCheckedRadioButtonId()};
            r1= (RadioButton) findViewById(rd.getCheckedRadioButtonId());
            r2= (RadioButton) findViewById(rd2.getCheckedRadioButtonId());
            u= new Usuario(nombre, apellidos, correo, r1.getText().toString(), r2.getText().toString(), contraseña,true);
            ref= FirebaseDatabase.getInstance().getReference().child("Usuario");
            ref.push().setValue(u);
            intent = new Intent(RegistroActivity.this, MenuPrincipal.class);
            intent.putExtra("botones", botones);
            intent.putExtra("texto", datosTextuales);
            startActivity(intent);
        }
    }
}