package com.example.aplicacioncolegio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Horario extends AppCompatActivity {
    TextView L1,L2,L3,L4,L5,L6,M1,M2,M3,M4,M5,M6,X1,X2,X3,X4,X5,X6,J1,J2,J3,J4,J5,J6,V1,V2,V3,V4,V5,V6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        getSupportActionBar().setTitle(R.string.horario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String[]  tipos= {"Guardia","Libre","Coord.","Clase"};
        L1= findViewById(R.id.L1);
        L2= findViewById(R.id.L2);
        L3= findViewById(R.id.L3);
        L4= findViewById(R.id.L4);
        L5= findViewById(R.id.L5);
        L6= findViewById(R.id.L6);
        M1= findViewById(R.id.M1);
        M2= findViewById(R.id.M2);
        M3= findViewById(R.id.M3);
        M4= findViewById(R.id.M4);
        M5= findViewById(R.id.M5);
        M6= findViewById(R.id.M6);
        X1= findViewById(R.id.X1);
        X2= findViewById(R.id.X2);
        X3= findViewById(R.id.X3);
        X4= findViewById(R.id.X4);
        X5= findViewById(R.id.X5);
        X6= findViewById(R.id.X6);
        J1= findViewById(R.id.J1);
        J2= findViewById(R.id.J2);
        J3= findViewById(R.id.J3);
        J4= findViewById(R.id.J4);
        J5= findViewById(R.id.J5);
        J6= findViewById(R.id.J6);
        V1= findViewById(R.id.V1);
        V2= findViewById(R.id.V2);
        V3= findViewById(R.id.V3);
        V4= findViewById(R.id.V4);
        V5= findViewById(R.id.V5);
        V6= findViewById(R.id.V6);
        ArrayList<TextView> views= new ArrayList<>(Arrays.asList(L1, L2, L3, L4, L5, L6, M1, M2, M3, M4, M5, M6, X1, X2, X3, X4, X5, X6, J1, J2, J3, J4, J5, J6, V1, V2, V3, V4, V5, V6));
        Random rand = new Random();
        String tiempo="";
        int num, counter=0;
        for(int i= 0; i<views.size();i++){
            if(counter==0){
                tiempo= "8:00-9:00";
                counter++;
            }
           else if(counter==1){
                tiempo= "9:00-10:00";
               counter++;
            }
            else if(counter==2){
                tiempo= "10:00-11:00";
                counter++;
            }
            else if(counter==3){
                tiempo= "11:30-12:30";
                counter++;
            }
            else if(counter==4){
                tiempo= "12:30-13:30";
                counter++;
            }
            else if(counter==5){
                tiempo= "13:30-14:30";
                counter=0;
            }

            num= rand.nextInt(4);
            TextView view= views.get(i);
            if(tipos[num].toLowerCase().contains("guardia")){
                view.setBackgroundResource(R.color.guardia);
            }
            else if(tipos[num].toLowerCase().contains("libre")){
                view.setBackgroundResource(R.color.libre);
            }
            else if(tipos[num].toLowerCase().contains("coord")){
                view.setBackgroundResource(R.color.coordinacion);
            }
            else if(tipos[num].toLowerCase().contains("clase")){
                view.setBackgroundResource(R.color.clase);
            }
            view.setText(tipos[num]+"\n"+tiempo);
        }
    }
}