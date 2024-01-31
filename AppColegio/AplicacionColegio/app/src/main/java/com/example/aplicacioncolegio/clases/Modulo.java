package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Modulo implements Serializable, Parcelable {
    public String nombre;
    public String nombreCiclo;
    public int curso;

    public Modulo(String nombre, String nombreCiclo, int curso) {
        this.nombre = nombre;
        this.nombreCiclo = nombreCiclo;
        this.curso = curso;
    }

    public Modulo() {
    }

    protected Modulo(Parcel in) {
        nombre = in.readString();
        nombreCiclo = in.readString();
        curso = in.readInt();
    }

    public static final Creator<Modulo> CREATOR = new Creator<Modulo>() {
        @Override
        public Modulo createFromParcel(Parcel in) {
            return new Modulo(in);
        }

        @Override
        public Modulo[] newArray(int size) {
            return new Modulo[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiclo() {
        return nombreCiclo;
    }

    public void setCiclo(String ciclo) {
        this.nombreCiclo = ciclo;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Modulo{" +
                "nombre='" + nombre + '\'' +
                ", nombreCiclo='" + nombreCiclo + '\'' +
                ", curso=" + curso +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(nombreCiclo);
        dest.writeInt(curso);
    }
}
