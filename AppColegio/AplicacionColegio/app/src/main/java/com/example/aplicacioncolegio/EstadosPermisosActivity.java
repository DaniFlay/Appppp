package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
    ArrayList<Permiso> permisos2;
    AdaptadorPermisos adapter;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference ref;
    Usuario user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permisos2 = getIntent().getParcelableArrayListExtra("permisos");
        setContentView(R.layout.activity_estados_permisos);
        user = getIntent().getParcelableExtra("usuario");
        permisos = new ArrayList<>();
        getSupportActionBar().setTitle(R.string.tusPermisos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for (Permiso p : permisos2) {
            Log.d("permiso",p.toString());
            int imagen = -1;
            if (p.getProfesor().getCorreo().equals(user.getCorreo())) {
                if (p.getEstado().equals("rechazado")) {
                    imagen = R.drawable.rechazado;
                } else if (p.getEstado().equals("aceptado")) {
                    imagen = R.drawable.aceptado;
                } else if (p.getEstado().equals("pendiente")) {
                    imagen = R.drawable.pendiente;
                }

                permisos.add(new EncapsuladorPermisos(imagen, p.getRazon(), p.getEstado()));
            }

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
}