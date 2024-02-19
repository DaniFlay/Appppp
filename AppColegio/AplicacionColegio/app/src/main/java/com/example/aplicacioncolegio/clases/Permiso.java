package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Permiso implements Serializable, Parcelable {
    private Usuario profesor;
    private String razon;
    private String tipoPermiso;
    private String estado;

    public Permiso(Usuario profesor, String razon, String tipoPermiso, String estado) {
        this.profesor = profesor;
        this.razon = razon;
        this.tipoPermiso = tipoPermiso;
        this.estado = estado;
    }

    public Permiso() {
    }

    protected Permiso(Parcel in) {
        profesor = in.readParcelable(Usuario.class.getClassLoader());
        razon = in.readString();
        tipoPermiso = in.readString();
        estado = in.readString();
    }

    public static final Creator<Permiso> CREATOR = new Creator<Permiso>() {
        @Override
        public Permiso createFromParcel(Parcel in) {
            return new Permiso(in);
        }

        @Override
        public Permiso[] newArray(int size) {
            return new Permiso[size];
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

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(profesor, flags);
        dest.writeString(razon);
        dest.writeString(tipoPermiso);
        dest.writeString(estado);
    }

    @Override
    public String toString() {
        return "Permiso{" +
                "profesor=" + profesor +
                ", razon='" + razon + '\'' +
                ", tipoPermiso='" + tipoPermiso + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
