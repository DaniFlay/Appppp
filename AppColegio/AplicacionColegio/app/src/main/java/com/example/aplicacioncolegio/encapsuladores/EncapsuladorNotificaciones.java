package com.example.aplicacioncolegio.encapsuladores;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class EncapsuladorNotificaciones implements Serializable, Parcelable {
    int imagen;
    String nombre,fecha, descripcion;

    public EncapsuladorNotificaciones(int imagen, String nombre, String fecha, String descripcion) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion= descripcion;
    }

    protected EncapsuladorNotificaciones(Parcel in) {
        imagen = in.readInt();
        nombre = in.readString();
        fecha = in.readString();
        descripcion = in.readString();
    }

    public static final Creator<EncapsuladorNotificaciones> CREATOR = new Creator<EncapsuladorNotificaciones>() {
        @Override
        public EncapsuladorNotificaciones createFromParcel(Parcel in) {
            return new EncapsuladorNotificaciones(in);
        }

        @Override
        public EncapsuladorNotificaciones[] newArray(int size) {
            return new EncapsuladorNotificaciones[size];
        }
    };

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
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
        dest.writeInt(imagen);
        dest.writeString(nombre);
        dest.writeString(fecha);
        dest.writeString(descripcion);
    }
}
