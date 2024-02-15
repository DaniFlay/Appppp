package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditarPerfilActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private RadioGroup rd;
    private RadioGroup rd2;

    private Button b;
    private EditText nombre;
    private EditText apellidos;
    private EditText correo;
    Usuario usuario;
    RadioButton puesto;
    RadioButton sexo;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        getSupportActionBar().setTitle(R.string.miCuenta);
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        usuario= getIntent().getParcelableExtra("usuario");
        nombre= (EditText) findViewById(R.id.nombreRegistro);
        apellidos= (EditText) findViewById(R.id.apellidosRegistro);
        correo= (EditText) findViewById(R.id.correoRegistro);
        nombre.setText(usuario.getNombre());
        apellidos.setText(usuario.getApellidos());
        correo.setText(usuario.getCorreo());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editarPerfil);
        if(usuario.getSexo().equals(getString(R.string.hombre))){
            sexo= (RadioButton) findViewById(R.id.radioButtonHombre);
        }
        else{
           sexo= (RadioButton) findViewById(R.id.radioButtonMujer);
        }
        if(usuario.getPuesto().equals(getString(R.string.jefe))){
            puesto= (RadioButton) findViewById(R.id.radioButtonJefe);
        }
        else if(usuario.getPuesto().equals(getString(R.string.coordinador))){
           puesto= (RadioButton) findViewById(R.id.radioButtonCoordinador);
        }
        else{
            puesto= (RadioButton) findViewById(R.id.radioButtonDocente);
        }
        puesto.setChecked(true);
        sexo.setChecked(true);
        rd= findViewById(R.id.radioGroupPuestos);
        rd2= findViewById(R.id.radioGroupSexo);

        rd.setOnCheckedChangeListener(this);
        rd2.setOnCheckedChangeListener(this);

        b= findViewById(R.id.botonRegistro);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        usuario.setNombre(nombre.getText().toString());
        usuario.setApellidos(apellidos.getText().toString());
        usuario.setCorreo(correo.getText().toString());
        if(rd2.getCheckedRadioButtonId()==R.id.radioButtonHombre){
            usuario.setSexo(getString(R.string.hombre));
        }
        else if(rd2.getCheckedRadioButtonId()==R.id.radioButtonMujer){
            usuario.setSexo(getString(R.string.mujer));
        }
        if(rd.getCheckedRadioButtonId()==R.id.radioButtonCoordinador){
            usuario.setPuesto(getString(R.string.coordinador));
        }
        else if(rd.getCheckedRadioButtonId()==R.id.radioButtonJefe){
            usuario.setPuesto(getString(R.string.jefe));
        }
        else if (rd.getCheckedRadioButtonId()==R.id.radioButtonDocente){
            usuario.setPuesto(getString(R.string.docente));
        }


        if(nombre.getText().toString().equals("")){
            nombre.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            new MaterialAlertDialogBuilder(EditarPerfilActivity.this)
                    .setMessage(getString(R.string.nombreIncompleto))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else if(apellidos.getText().toString().equals("")){
            nombre.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            new MaterialAlertDialogBuilder(EditarPerfilActivity.this)
                    .setMessage(getString(R.string.apellidoIncompleto))
                    .setPositiveButton(R.string.ok,null)
                    .show();
        }
        else{
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot d: snapshot.getChildren()){
                        Usuario dummy=  d.getValue(Usuario.class);
                        if(usuario.getCorreo().equals(dummy.getCorreo())){
                            usuario.setPassword(dummy.getPassword());
                            usuario.setAusente(dummy.isAusente());
                            d.getRef().setValue(usuario);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent intent;
            intent= new Intent(EditarPerfilActivity.this, MenuPrincipal.class);
            intent.putExtra("usuario", (Parcelable) usuario);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}