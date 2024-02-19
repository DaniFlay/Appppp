package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aplicacioncolegio.clases.Clase;
import com.example.aplicacioncolegio.clases.Modulo;
import com.example.aplicacioncolegio.clases.Notificacion;
import com.example.aplicacioncolegio.clases.Tarea;
import com.example.aplicacioncolegio.clases.Usuario;
import com.example.aplicacioncolegio.encapsuladores.EncapsuladorNotificaciones;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    DatabaseReference ref;
    ArrayList<EncapsuladorNotificaciones> nots;
    ArrayList<Notificacion> notifs;
    Usuario usuario;
    ArrayList<Clase> clases, clasesUsuario;
    ArrayList<Tarea> tareas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tareas= new ArrayList<>();
        nots= new ArrayList<>();
        notifs= new ArrayList<>();
        usuario= getIntent().getParcelableExtra("usuario");
        ref= FirebaseDatabase.getInstance().getReference("Clase");
        clases= new ArrayList<>();
        clasesUsuario= new ArrayList<>();

       ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Clase c= d.getValue(Clase.class);
                    clases.add(c);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref= FirebaseDatabase.getInstance().getReference("Tarea Administrativa");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    Tarea t= d.getValue(Tarea.class);
                    if(t.getUsuario().getCorreo().equals(usuario.getCorreo())&&!t.isCompletado()){
                        tareas.add(t);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.notificacion));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    Notificacion dummy= d.getValue(Notificacion.class);
                    Log.d("notificacion",dummy.toString());
                    if(dummy.getUsuario().getCorreo().equals(usuario.getCorreo())&&!dummy.isVisto()){
                        nots.add(new EncapsuladorNotificaciones(R.drawable.notification,dummy.getName(),dummy.getFecha(),dummy.getDescripcion()));
                        notifs.add(dummy);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            if(usuario.getPuesto().equals(getString(R.string.jefe))){
                setContentView(R.layout.activity_menu_principal_jefe);
                NavigationView nav_view= findViewById(R.id.nav_view);
                drawerLayout=findViewById(R.id.drawer_layout_jefe);
                actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.abrir_menu_deslizante,R.string.cerrar_menu_deslizante);
                drawerLayout.addDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.menu_principal);


                nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent intent=null;

                        if(item.getItemId()==R.id.nav_cerrarSesion){
                            intent= new Intent(MenuPrincipal.this, LauncherActivity.class);
                            startActivity(intent);
                        }
                        else if (item.getItemId()==R.id.nav_micuenta) {
                            intent = new Intent(MenuPrincipal.this, EditarPerfilActivity.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_notificaciones){
                            intent= new Intent(MenuPrincipal.this, Notificaciones.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            intent.putParcelableArrayListExtra("listaEnc",nots);
                            intent.putExtra("lista",notifs);
                            startActivity(intent);
                        }
                        else if (item.getItemId()==R.id.nav_horario) {
                            intent= new Intent(MenuPrincipal.this, Horario.class);
                            intent.putParcelableArrayListExtra("clases",clases);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);

                        }else if (item.getItemId()==R.id.nav_reuniones) {
                            intent = new Intent(MenuPrincipal.this,Reuniones.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()== R.id.nav_guardias){
                            intent= new Intent(MenuPrincipal.this, InformeGuardia.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }else if(item.getItemId()==R.id.nav_mensajes){
                            intent= new Intent(MenuPrincipal.this, EnviarMensaje.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_tareas) {
                            intent= new Intent(MenuPrincipal.this, FijarTareas.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);

                        }

                        return true;
                    }
                });
            } else if (usuario.getPuesto().equals(getString(R.string.coordinador))) {
                setContentView(R.layout.activity_menu_principal_coordinador);
                drawerLayout=findViewById(R.id.drawer_layout_coordinador);
                actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.abrir_menu_deslizante,R.string.cerrar_menu_deslizante);
                drawerLayout.addDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setTitle(R.string.menu_principal);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                NavigationView nav_view= findViewById(R.id.nav_view);


                nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent intent=null;

                        if(item.getItemId()==R.id.nav_cerrarSesion){
                            intent= new Intent(MenuPrincipal.this, LauncherActivity.class);
                            startActivity(intent);
                        }
                        else if (item.getItemId()==R.id.nav_micuenta) {
                            intent = new Intent(MenuPrincipal.this, EditarPerfilActivity.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }else if (item.getItemId()==R.id.nav_permisos) {
                            intent= new Intent(MenuPrincipal.this,PermisosActivity.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_tareas){
                            intent = new Intent(MenuPrincipal.this,TareasAdministrativas.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            intent.putParcelableArrayListExtra("tareas",tareas);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_ausencias) {
                            intent = new Intent(MenuPrincipal.this,Ausencias.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_notificaciones){
                            intent= new Intent(MenuPrincipal.this, Notificaciones.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            intent.putParcelableArrayListExtra("listaEnc",nots);
                            intent.putExtra("lista",notifs);
                            startActivity(intent);
                        }
                        else if (item.getItemId()==R.id.nav_horario) {
                            intent= new Intent(MenuPrincipal.this, Horario.class);
                            intent.putParcelableArrayListExtra("clases",clases);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);

                        } else if (item.getItemId()==R.id.nav_reuniones) {
                            intent = new Intent(MenuPrincipal.this,Reuniones.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_avisos) {
                            intent = new Intent(MenuPrincipal.this,Avisos.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
            } else if (usuario.getPuesto().equals(getString(R.string.docente))) {
                setContentView(R.layout.activity_menu_principal_docente);


                drawerLayout=findViewById(R.id.drawer_layout_docente);
                actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.abrir_menu_deslizante,R.string.cerrar_menu_deslizante);

                drawerLayout.addDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setTitle(R.string.menu_principal);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                NavigationView nav_view= findViewById(R.id.nav_view);

                nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Intent intent=null;

                        if(item.getItemId()==R.id.nav_cerrarSesion){
                            intent= new Intent(MenuPrincipal.this, LauncherActivity.class);
                            startActivity(intent);
                        }
                        else if (item.getItemId()==R.id.nav_micuenta) {
                            intent = new Intent(MenuPrincipal.this, EditarPerfilActivity.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_permisos) {
                            intent= new Intent(MenuPrincipal.this,PermisosActivity.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_tareas){
                            intent = new Intent(MenuPrincipal.this,TareasAdministrativas.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            intent.putParcelableArrayListExtra("tareas",tareas);
                            startActivity(intent);
                        }
                     else if (item.getItemId()==R.id.nav_ausencias) {
                        intent = new Intent(MenuPrincipal.this,Ausencias.class);
                        intent.putExtra("usuario", (Parcelable) usuario);
                        startActivity(intent);
                    }
                        else if(item.getItemId()==R.id.nav_notificaciones){
                            intent= new Intent(MenuPrincipal.this, Notificaciones.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            intent.putParcelableArrayListExtra("listaEnc",nots);
                            intent.putExtra("lista",notifs);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_horario) {
                            intent= new Intent(MenuPrincipal.this, Horario.class);
                            intent.putParcelableArrayListExtra("clases",clases);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);

                        }else if (item.getItemId()==R.id.nav_reuniones) {
                            intent = new Intent(MenuPrincipal.this,Reuniones.class);
                            intent.putExtra("usuario", (Parcelable) usuario);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

            }
        }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}