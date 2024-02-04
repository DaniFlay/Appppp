package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ausencia implements Serializable, Parcelable {
    private Usuario profesor;
    private String razon;
    private String fechaInicio;
    private String fechaFin;
    private String horaInicio;
    private String horaFin;

    public Ausencia(Usuario profesor, String razon, String fechaInicio, String fechaFin, String horaInicio, String horaFin) {
        this.profesor = profesor;
        this.razon = razon;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Ausencia() {
    }

    protected Ausencia(Parcel in) {
        profesor = in.readParcelable(Usuario.class.getClassLoader());
        razon = in.readString();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        horaInicio = in.readString();
        horaFin = in.readString();
    }

    public static final Creator<Ausencia> CREATOR = new Creator<Ausencia>() {
        @Override
        public Ausencia createFromParcel(Parcel in) {
            return new Ausencia(in);
        }

        @Override
        public Ausencia[] newArray(int size) {
            return new Ausencia[size];
        }
    };

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(profesor, flags);
        dest.writeString(razon);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeString(horaInicio);
        dest.writeString(horaFin);
    }
}
