package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CambioPassword extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference ref;
    Button comprobar;
    EditText correo;
    boolean existe;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_password);
        existe=false;
        correo= findViewById(R.id.correo);
        comprobar= findViewById(R.id.botonComprobar);
        comprobar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==comprobar.getId()){
            ref= FirebaseDatabase.getInstance().getReference().child(getString(R.string.usuario));
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot d:snapshot.getChildren()){
                        usuario= d.getValue(Usuario.class);
                        if(usuario.getCorreo().equals(correo.getText().toString())){
                            existe=true;
                        }
                    }
                    if(existe){
                        Intent intent= new Intent(CambioPassword.this, CambioPassword_2.class);
                        intent.putExtra(getString(R.string.usuario),(Parcelable) usuario);
                        startActivity(intent);
                    }else{
                        new MaterialAlertDialogBuilder(CambioPassword.this)
                                .setMessage(getString(R.string.usuarioNoExiste))
                                .setPositiveButton(getString(R.string.ok),null)
                                .show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}