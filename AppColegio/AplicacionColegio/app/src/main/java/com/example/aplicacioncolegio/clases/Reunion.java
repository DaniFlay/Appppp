package com.example.aplicacioncolegio.clases;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashSet;

public class Reunion implements Serializable, Parcelable {
    private HashSet<Usuario> usuarios;
    private String fecha;
    private String horaInicio;
    private String Observaciones;

    public Reunion(HashSet<Usuario> usuarios, String fecha, String horaInicio, String observaciones) {
        this.usuarios = usuarios;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        Observaciones = observaciones;
    }

    public Reunion() {
    }

    protected Reunion(Parcel in) {
        fecha = in.readString();
        horaInicio = in.readString();
        Observaciones = in.readString();
    }

    public static final Creator<Reunion> CREATOR = new Creator<Reunion>() {
        @Override
        public Reunion createFromParcel(Parcel in) {
            return new Reunion(in);
        }

        @Override
        public Reunion[] newArray(int size) {
            return new Reunion[size];
        }
    };

    public HashSet<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(HashSet<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(fecha);
        dest.writeString(horaInicio);
        dest.writeString(Observaciones);
    }
}
