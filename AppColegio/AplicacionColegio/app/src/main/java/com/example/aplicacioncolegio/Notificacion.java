package com.example.aplicacioncolegio;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Notificacion implements Serializable, Parcelable {
    private String nombre;
    private String fecha;
    private Object tipo;

    public Notificacion(String nombre, String fecha, Object tipo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo= tipo;
    }

    public Notificacion() {
    }

    protected Notificacion(Parcel in) {
        nombre = in.readString();
        fecha = in.readString();
    }

    public static final Creator<Notificacion> CREATOR = new Creator<Notificacion>() {
        @Override
        public Notificacion createFromParcel(Parcel in) {
            return new Notificacion(in);
        }

        @Override
        public Notificacion[] newArray(int size) {
            return new Notificacion[size];
        }
    };

    public Object getTipo() {
        return tipo;
    }

    public void setTipo(Object tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(fecha);
    }
}
