package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aplicacioncolegio.clases.Ciclo;
import com.example.aplicacioncolegio.clases.Modulo;
import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup rd;
    private RadioGroup rd2;
    private Usuario u;
    RadioButton r1,r2;
    DatabaseReference ref;
    ArrayList<Modulo> modulos;
    boolean existe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        existe=false;
        rd= findViewById(R.id.radioGroupPuestos);
        rd2= findViewById(R.id.radioGroupSexo);
        modulos= new ArrayList<>();
        ref=FirebaseDatabase.getInstance().getReference(getString(R.string.modulos));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Modulo mod= d.getValue(Modulo.class);
                    if(mod.getCiclo().equals("Desarrollo de Aplicaciones Multiplataforma")){
                        modulos.add(mod);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        ref=FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Usuario dummy= d.getValue(Usuario.class);
                    if(dummy.getCorreo().equals(correo)){
                        existe=true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        else if(existe){
            new MaterialAlertDialogBuilder(RegistroActivity.this)
                    .setMessage(getString(R.string.correoExiste))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else {
            r1= (RadioButton) findViewById(rd.getCheckedRadioButtonId());
            r2= (RadioButton) findViewById(rd2.getCheckedRadioButtonId());


            Ciclo ciclo=new Ciclo("Desarrollo de Aplicaciones Multiplataforma",modulos);
            u= new Usuario(nombre, apellidos, correo, r1.getText().toString(), r2.getText().toString(), contraseña,false,ciclo,modulos);
            ref= FirebaseDatabase.getInstance().getReference("Usuario");
            ref.push().setValue(u);
            intent = new Intent(RegistroActivity.this, MenuPrincipal.class);
            intent.putExtra(getString(R.string.usuario), (Parcelable) u);
            startActivity(intent);
        }
    }
}