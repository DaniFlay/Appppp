package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Usuario dummy;
    DatabaseReference ref;
    Button botonInicio, botonRegistro;
    Context context;
    TextView passwordOlvidado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        botonInicio= findViewById(R.id.botonInicioSesion);
        botonRegistro= findViewById(R.id.botonRegistro);
        passwordOlvidado= findViewById(R.id.textoSeMeHaOlvidadoLaContraseña);
        passwordOlvidado.setOnClickListener(this);
        botonRegistro.setOnClickListener(this);
        botonInicio.setOnClickListener(this);
        context= new ContextThemeWrapper(LauncherActivity.this, R.style.Theme_AplicacionColegio);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==botonRegistro.getId()){
            Intent registro = new Intent(LauncherActivity.this, RegistroActivity.class);
            startActivity(registro);
        }else if(view.getId()==botonInicio.getId()){
            EditText nombre= (EditText) findViewById(R.id.contentUsername);
            EditText pwd= (EditText) findViewById(R.id.contentPassword);
            if(nombre.getText().toString().equals("")){
                nombre.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                new MaterialAlertDialogBuilder(context)
                        .setMessage(getString(R.string.nombreIncompleto))
                        .setPositiveButton(R.string.ok,null)
                        .show();
            }
            else if(pwd.getText().toString().equals("")){
                pwd.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                new MaterialAlertDialogBuilder(context)
                        .setMessage(getString(R.string.contraseñaIncompleta))
                        .setPositiveButton(R.string.ok,null)
                        .show();
            }
            else{
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int num=0;
                        for(DataSnapshot d: snapshot.getChildren()){
                            dummy=d.getValue(Usuario.class);
                            if(dummy.getCorreo().equals(nombre.getText().toString()) && dummy.getPassword().equals(pwd.getText().toString())){
                                num++;
                                break;
                            }
                        }
                        if(num==0){
                            Toast.makeText(LauncherActivity.this, "El usuario o contraseña no son correctos",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            entrar(dummy);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        }
        else if(view.getId()== passwordOlvidado.getId()){
            Intent intent= new Intent(LauncherActivity.this,CambioPassword.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb= findViewById(checkedId);
    }
    public void entrar(Usuario dummy){
        Intent inicioSesion= new Intent(LauncherActivity.this,MenuPrincipal.class);
        inicioSesion.putExtra(getString(R.string.usuario), (Parcelable) dummy);
        startActivity(inicioSesion);
    }
}