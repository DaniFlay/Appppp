package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Permiso;
import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NuevoPermiso extends AppCompatActivity implements  View.OnClickListener {
    Spinner sp2 = null;

    Spinner sp1 = null;
    Permiso p= new Permiso();
    String permiso = "";
    Usuario usuario, jefe;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_permiso);
        usuario= getIntent().getParcelableExtra("usuario");
        sp2 = (Spinner) findViewById(R.id.spinner_tipo_permiso);
        sp1 = (Spinner) findViewById(R.id.spinner_razon_permiso);
        sp2.setEnabled(false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.permisos_razones, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                permiso = adapterView.getSelectedItem().toString();
                ArrayAdapter<CharSequence> adapter = null;
                sp2.setEnabled(true);
                if (position == 0) {
                    adapter = ArrayAdapter.createFromResource(NuevoPermiso.this, R.array.permisos_familia, android.R.layout.simple_spinner_dropdown_item);

                } else if (position == 1) {
                    adapter = ArrayAdapter.createFromResource(NuevoPermiso.this, R.array.permisos_naa, android.R.layout.simple_spinner_dropdown_item);


                } else if (position == 2) {
                    adapter = ArrayAdapter.createFromResource(NuevoPermiso.this, R.array.permisos_asuntos_personales, android.R.layout.simple_spinner_dropdown_item);

                } else if (position == 3) {
                    adapter = ArrayAdapter.createFromResource(NuevoPermiso.this, R.array.permisos_salud_propia, android.R.layout.simple_spinner_dropdown_item);

                } else if (position == 4) {
                    adapter = ArrayAdapter.createFromResource(NuevoPermiso.this, R.array.permisos_formacion, android.R.layout.simple_spinner_dropdown_item);

                } else if (position == 5) {
                    adapter = ArrayAdapter.createFromResource(NuevoPermiso.this, R.array.permisos_deberes_civiles, android.R.layout.simple_spinner_dropdown_item);

                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp2.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //aquí fijas el valor de la variable Permiso que tenías antes
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button b = (Button) findViewById(R.id.boton_guardar);
        b.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        p.setEstado(getString(R.string.pendienteEstado));
        p.setProfesor(usuario);
        p.setTipoPermiso(sp2.getSelectedItem().toString());
        p.setRazon(sp1.getSelectedItem().toString());
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.permiso));
        ref.push().setValue(permiso);
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Usuario u= d.getValue(Usuario.class);
                    if(u.getPuesto().equals(getString(R.string.jefe))){
                        jefe= u;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String fecha= dtf.format(now);
        Notificacion notificacion= new Notificacion(usuario,jefe,getString(R.string.permiso),p.getTipoPermiso(),fecha,false);
        ref.push().setValue(notificacion);
        if (!sp2.isActivated()) {
            new MaterialAlertDialogBuilder(NuevoPermiso.this)
                    .setMessage(R.string.rellenarPermiso)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        } else  {
            Intent intent = new Intent(NuevoPermiso.this, EstadosPermisosActivity.class);
            intent.putExtra("permiso", permiso);
            startActivity(intent);

        }
    }
}