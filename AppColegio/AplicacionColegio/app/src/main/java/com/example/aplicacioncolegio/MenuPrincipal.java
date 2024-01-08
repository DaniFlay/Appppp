package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

public class MenuPrincipal extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Usuario usuario= getIntent().getParcelableExtra(getString(R.string.usuario));
            if(usuario.getPuesto().equals(getString(R.string.jefe))){
                setContentView(R.layout.activity_menu_principal_jefe);
                drawerLayout=findViewById(R.id.drawer_layout_jefe);
                actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.abrir_menu_deslizante,R.string.cerrar_menu_deslizante);
                drawerLayout.addDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(R.string.menu_principal);
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
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_notificaciones){
                            intent= new Intent(MenuPrincipal.this, Notificaciones.class);
                            startActivity(intent);
                        }
                        else if (item.getItemId()==R.id.nav_horario) {
                            intent= new Intent(MenuPrincipal.this, Horario.class);
                            startActivity(intent);

                        }else if (item.getItemId()==R.id.nav_reuniones) {
                            intent = new Intent(MenuPrincipal.this,Reuniones.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);

                            startActivity(intent);
                        }
                        else if(item.getItemId()== R.id.nav_guardias){
                            intent= new Intent(MenuPrincipal.this, InformeGuardia.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }else if(item.getItemId()==R.id.nav_mensajes){
                            intent= new Intent(MenuPrincipal.this, EnviarMensaje.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_tareas) {
                            intent= new Intent(MenuPrincipal.this, FijarTareas.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
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
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }else if (item.getItemId()==R.id.nav_permisos) {
                            intent= new Intent(MenuPrincipal.this,PermisosActivity.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_tareas){
                            intent = new Intent(MenuPrincipal.this,TareasAdministrativas.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_ausencias) {
                            intent = new Intent(MenuPrincipal.this,Ausencias.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_notificaciones){
                            intent= new Intent(MenuPrincipal.this, Notificaciones.class);
                            startActivity(intent);
                        }
                        else if (item.getItemId()==R.id.nav_horario) {
                            intent= new Intent(MenuPrincipal.this, Horario.class);
                            startActivity(intent);

                        } else if (item.getItemId()==R.id.nav_reuniones) {
                            intent = new Intent(MenuPrincipal.this,Reuniones.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_avisos) {
                            intent = new Intent(MenuPrincipal.this,Avisos.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
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
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_permisos) {
                            intent= new Intent(MenuPrincipal.this,PermisosActivity.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.nav_tareas){
                            intent = new Intent(MenuPrincipal.this,TareasAdministrativas.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                            startActivity(intent);
                        }
                     else if (item.getItemId()==R.id.nav_ausencias) {
                        intent = new Intent(MenuPrincipal.this,Ausencias.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
                        startActivity(intent);
                    }
                        else if(item.getItemId()==R.id.nav_notificaciones){
                            intent= new Intent(MenuPrincipal.this, Notificaciones.class);
                            startActivity(intent);
                        } else if (item.getItemId()==R.id.nav_horario) {
                            intent= new Intent(MenuPrincipal.this, Horario.class);
                            startActivity(intent);

                        }else if (item.getItemId()==R.id.nav_reuniones) {
                            intent = new Intent(MenuPrincipal.this,Reuniones.class);
                            intent.putExtra(getString(R.string.usuario), (Parcelable) usuario);
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