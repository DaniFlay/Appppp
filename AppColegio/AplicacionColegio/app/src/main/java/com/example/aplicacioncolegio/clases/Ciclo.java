package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Ciclo implements Serializable, Parcelable {
    private String nombre;
    private ArrayList<Modulo> modulo;

    public Ciclo(String nombre, ArrayList<Modulo> modulo) {

        this.nombre = nombre;
        this.modulo = modulo;
    }

    public Ciclo() {
    }

    protected Ciclo(Parcel in) {
        nombre = in.readString();
        modulo = in.createTypedArrayList(Modulo.CREATOR);
    }

    public static final Creator<Ciclo> CREATOR = new Creator<Ciclo>() {
        @Override
        public Ciclo createFromParcel(Parcel in) {
            return new Ciclo(in);
        }

        @Override
        public Ciclo[] newArray(int size) {
            return new Ciclo[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Modulo> getModulo() {
        return modulo;
    }

    public void setModulo(ArrayList<Modulo> modulo) {
        this.modulo = modulo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeTypedList(modulo);
    }
}
