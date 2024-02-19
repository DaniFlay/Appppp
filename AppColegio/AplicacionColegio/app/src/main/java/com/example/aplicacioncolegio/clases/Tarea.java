package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Tarea implements Serializable, Parcelable {
    private Usuario usuario;
    private String tipo;
    private String fechaFin;
    private boolean completado;


    protected Tarea(Parcel in) {
        usuario = in.readParcelable(Usuario.class.getClassLoader());
        tipo = in.readString();
        fechaFin = in.readString();
        completado = in.readByte() != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return completado == tarea.completado && Objects.equals(usuario.getCorreo(), tarea.usuario.getCorreo()) && Objects.equals(tipo, tarea.tipo) && Objects.equals(fechaFin, tarea.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, tipo, fechaFin, completado);
    }

    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        @Override
        public Tarea createFromParcel(Parcel in) {
            return new Tarea(in);
        }

        @Override
        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(usuario, flags);
        dest.writeString(tipo);
        dest.writeString(fechaFin);
        dest.writeByte((byte) (completado ? 1 : 0));
    }

    public Tarea(Usuario usuario, String tipo, String fechaFin, boolean completado) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.fechaFin = fechaFin;
        this.completado = completado;
    }

    public Tarea() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
