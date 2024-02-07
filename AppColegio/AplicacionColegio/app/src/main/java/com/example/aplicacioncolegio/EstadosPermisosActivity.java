package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.aplicacioncolegio.adaptadores.AdaptadorPermisos;
import com.example.aplicacioncolegio.encapsuladores.EncapsuladorPermisos;
import com.example.aplicacioncolegio.clases.Permiso;
import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EstadosPermisosActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<EncapsuladorPermisos> permisos;
    Permiso p;
    AdaptadorPermisos adapter;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference ref;
    Usuario user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados_permisos);
        user = getIntent().getParcelableExtra("usuario");
        permisos = new ArrayList<>();
        getSupportActionBar().setTitle(R.string.tusPermisos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference(getString(R.string.permiso));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    p = d.getValue(Permiso.class);
                    int imagen = -1;
                    if (p.getProfesor().getCorreo().equals(user.getCorreo())) {
                        if (p.getEstado().equals(getString(R.string.rechazadoEstado))) {
                            imagen = R.drawable.rechazado;
                        } else if (p.getEstado().equals(getString(R.string.aceptadoEstado))) {
                            imagen = R.drawable.aceptado;
                        } else if (p.getEstado().equals(getString(R.string.pendienteEstado))) {
                            imagen = R.drawable.pendiente;
                        }

                        permisos.add(new EncapsuladorPermisos(imagen, p.getRazon(), p.getEstado()));
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new AdaptadorPermisos(this, permisos, R.layout.item_permiso);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = recyclerView.getChildAdapterPosition(v);
                Snackbar.make(findViewById(R.id.vistaGeneral), "Estado del permiso: " + permisos.get(posicion).getEstado(), Snackbar.LENGTH_SHORT).show();


            }
        });
        recyclerView = findViewById(R.id.RecyclerViewPermisos);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }
}