package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicacioncolegio.clases.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CambioPassword_2 extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference ref;
    EditText pwd, pwd2;
    Button cambiar;
    Usuario usuario, usuario2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_password2);
        ref= FirebaseDatabase.getInstance().getReference().child(getString(R.string.usuario));
        pwd= findViewById(R.id.nuevaContraseña);
        pwd2= findViewById(R.id.nuevaConstraseña2);
        cambiar= findViewById(R.id.botonCambiar);
        cambiar.setOnClickListener(this);
        usuario= getIntent().getParcelableExtra(getString(R.string.usuario));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==cambiar.getId()){
            if(pwd.getText().toString().equals(pwd2.getText().toString())){
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d: snapshot.getChildren()){
                            usuario2= d.getValue(Usuario.class);
                            if(usuario2.getCorreo().equals(usuario.getCorreo())){
                                usuario.setPassword(pwd.getText().toString());
                                d.getRef().setValue(usuario);
                                Toast.makeText(CambioPassword_2.this, getString(R.string.contraseñaCambiada),Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(CambioPassword_2.this, LauncherActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }
}