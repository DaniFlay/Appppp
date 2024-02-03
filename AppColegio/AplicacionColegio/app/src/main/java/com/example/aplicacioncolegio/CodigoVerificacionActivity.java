package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.snackbar.Snackbar;

public class CodigoVerificacionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText codigoTexto;
    Button boton;
    String codigo;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_verificacion);
        codigoTexto= findViewById(R.id.codigo);
        boton= findViewById(R.id.botonComprobar);
        codigo= getIntent().getStringExtra("codigo");
        usuario= getIntent().getParcelableExtra("usuario");
        boton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(codigo.equals(codigoTexto.getText().toString())){
            Intent intent= new Intent(CodigoVerificacionActivity.this, CambioPassword_2.class);
            intent.putExtra("usuario",(Parcelable) usuario);
            startActivity(intent);
        }
        else{
            Snackbar.make(v,getString(R.string.codigoIncorrecto),Snackbar.LENGTH_SHORT).show();
        }
    }
}