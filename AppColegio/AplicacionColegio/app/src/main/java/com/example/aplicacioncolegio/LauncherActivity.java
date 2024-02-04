package com.example.aplicacioncolegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacioncolegio.clases.Ciclo;
import com.example.aplicacioncolegio.clases.Modulo;
import com.example.aplicacioncolegio.clases.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Usuario dummy;
    DatabaseReference ref;
    Button botonInicio, botonRegistro;
    Context context;
    TextView passwordOlvidado;
    CheckBox checkbox;
    EditText nombre, pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
         nombre= (EditText) findViewById(R.id.contentUsername);
         pwd= (EditText) findViewById(R.id.contentPassword);
        checkbox= findViewById(R.id.checkBox);
        checkbox.setOnClickListener(this);
        ref= FirebaseDatabase.getInstance().getReference(getString(R.string.modulos));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0){
                    int num=0;
                    String[] dam1= getResources().getStringArray(R.array.dam1);
                    String[] dam2= getResources().getStringArray(R.array.dam2);
                    String[] inf1= getResources().getStringArray(R.array.infantil1);
                    String[] inf2= getResources().getStringArray(R.array.infantil2);
                    String[] integ1= getResources().getStringArray(R.array.inter1);
                    String[] integ2= getResources().getStringArray(R.array.inter2);
                    String[] dien1= getResources().getStringArray(R.array.dientes1);
                    String[] dien2= getResources().getStringArray(R.array.dientes2);
                    String[] med1= getResources().getStringArray(R.array.mediacion1);
                    String[] med2= getResources().getStringArray(R.array.mediacion2);
                    String[][] modulos1= {dam1,dien1,inf1,integ1,med1};
                    String[][] modulos2={dam2,dien2,inf2,integ2,med2};
                    String[] ciclos= getResources()
                            .getStringArray(R.array.ciclos);
                    for(String[] mods: modulos1){
                        for(String mod:mods){
                            if(num>4){
                                num=0;
                            }
                            Modulo modulo= new Modulo(mod,ciclos[num],1);

                            ref.push().setValue(modulo);
                        }
                        num++;
                    }
                    for(String[] mods: modulos2){
                        for(String mod:mods){
                            if(num>4){
                                num=0;
                            }
                            Modulo modulo= new Modulo(mod,ciclos[num],2);
                            num++;
                            ref.push().setValue(modulo);
                        }
                    }
                    ref=FirebaseDatabase.getInstance().getReference(getString(R.string.ciclos));
                    for(int i=0;i<1;i++){
                        ArrayList<Modulo> modulosAnadir= new ArrayList<>();
                        for(int j=0;j<dam1.length;j++){
                            modulosAnadir.add(new Modulo(dam1[j],ciclos[i],1));
                        }
                        for(int j=0;j<dam2.length;j++){
                            modulosAnadir.add(new Modulo(dam2[j],ciclos[i],1));
                        }
                        ref.push().setValue(new Ciclo(ciclos[i],modulosAnadir));
                    }
                    for(int i=1;i<2;i++){
                        ArrayList<Modulo> modulosAnadir= new ArrayList<>();
                        for(int j=0;j<dien1.length;j++){
                            modulosAnadir.add(new Modulo(dien1[j],ciclos[i],1));
                        }
                        for(int j=0;j<dien2.length;j++){
                            modulosAnadir.add(new Modulo(dien2[j],ciclos[i],1));
                        }
                        ref.push().setValue(new Ciclo(ciclos[i],modulosAnadir));
                    }
                    for(int i=2;i<3;i++){
                        ArrayList<Modulo> modulosAnadir= new ArrayList<>();
                        for(int j=0;j<inf1.length;j++){
                            modulosAnadir.add(new Modulo(inf1[j],ciclos[i],1));
                        }
                        for(int j=0;j<inf2.length;j++){
                            modulosAnadir.add(new Modulo(inf2[j],ciclos[i],1));
                        }
                        ref.push().setValue(new Ciclo(ciclos[i],modulosAnadir));
                    }
                    for(int i=3;i<4;i++){
                        ArrayList<Modulo> modulosAnadir= new ArrayList<>();
                        for(int j=0;j<integ1.length;j++){
                            modulosAnadir.add(new Modulo(integ1[j],ciclos[i],1));
                        }
                        for(int j=0;j<integ2.length;j++){
                            modulosAnadir.add(new Modulo(integ2[j],ciclos[i],1));
                        }
                        ref.push().setValue(new Ciclo(ciclos[i],modulosAnadir));
                    }
                    for(int i=4;i<5;i++){
                        ArrayList<Modulo> modulosAnadir= new ArrayList<>();
                        for(int j=0;j<med1.length;j++){
                            modulosAnadir.add(new Modulo(med1[j],ciclos[i],1));
                        }
                        for(int j=0;j<med2.length;j++){
                            modulosAnadir.add(new Modulo(med2[j],ciclos[i],1));
                        }
                        ref.push().setValue(new Ciclo(ciclos[i],modulosAnadir));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        botonInicio= findViewById(R.id.botonInicioSesion);
        botonRegistro= findViewById(R.id.botonRegistro);
        passwordOlvidado= findViewById(R.id.textoSeMeHaOlvidadoLaContraseña);
        passwordOlvidado.setOnClickListener(this);
        botonRegistro.setOnClickListener(this);
        botonInicio.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if(view.getId()==botonRegistro.getId()){
            Intent registro = new Intent(LauncherActivity.this, RegistroActivity.class);
            startActivity(registro);
        }else if(view.getId()==checkbox.getId()){
            if(checkbox.isChecked()){
                pwd.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            else{
                pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

            }
        }
        else if(view.getId()==botonInicio.getId()){

            if(nombre.getText().toString().equals("")){
                nombre.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                new MaterialAlertDialogBuilder(context)
                        .setMessage(getString(R.string.nombreIncompleto))
                        .setPositiveButton(R.string.ok,null)
                        .show();
            }
            else if(pwd.getText().toString().equals("")){
                pwd.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                new MaterialAlertDialogBuilder(context)
                        .setMessage(getString(R.string.contraseñaIncompleta))
                        .setPositiveButton(R.string.ok,null)
                        .show();
            }
            else{
                ref= FirebaseDatabase.getInstance().getReference(getString(R.string.usuario));
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int num=0;
                        for(DataSnapshot d: snapshot.getChildren()){
                            dummy=d.getValue(Usuario.class);
                            if(dummy.getCorreo().equals(nombre.getText().toString()) && dummy.getPassword().equals(pwd.getText().toString())){
                                num++;
                                break;
                            }
                        }
                        if(num==0){
                            Toast.makeText(LauncherActivity.this, "El usuario o contraseña no son correctos",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            entrar(dummy);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        }
        else if(view.getId()== passwordOlvidado.getId()){
            Intent intent= new Intent(LauncherActivity.this,CambioPassword.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb= findViewById(checkedId);
    }
    public void entrar(Usuario dummy){
        Intent inicioSesion= new Intent(LauncherActivity.this,MenuPrincipal.class);
        inicioSesion.putExtra("usuario", (Parcelable) dummy);
        startActivity(inicioSesion);
    }
}