package com.example.aplicacioncolegio.clases;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Reunion implements Serializable, Parcelable {
    private Usuario usuario;
    private String fecha;
    private String horaInicio;
    private String Observaciones;
    private String estado;

    public Reunion(Usuario usuario, String fecha, String horaInicio, String observaciones, String estado) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        Observaciones = observaciones;
        this.estado = estado;
    }

    public Reunion() {
    }

    protected Reunion(Parcel in) {
        usuario = in.readParcelable(Usuario.class.getClassLoader());
        fecha = in.readString();
        horaInicio = in.readString();
        Observaciones = in.readString();
        estado = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion reunion = (Reunion) o;
        return Objects.equals(usuario.getCorreo(), reunion.usuario.getCorreo()) && Objects.equals(fecha, reunion.fecha) && Objects.equals(horaInicio, reunion.horaInicio) && Objects.equals(Observaciones, reunion.Observaciones) && Objects.equals(estado, reunion.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, fecha, horaInicio, Observaciones, estado);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(usuario, flags);
        dest.writeString(fecha);
        dest.writeString(horaInicio);
        dest.writeString(Observaciones);
        dest.writeString(estado);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
