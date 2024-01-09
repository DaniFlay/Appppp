package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.aplicacioncolegio.clases.Permiso;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class NuevoPermiso extends AppCompatActivity implements  View.OnClickListener {
    Spinner sp2 = null;

    Spinner sp1 = null;
    Permiso p= new Permiso();
    String permiso = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_permiso);
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
        p.setEstado("pendiente");
       // p.setNombre(permiso);
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