package com.example.aplicacioncolegio;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashSet;

public class Aviso implements Serializable, Parcelable {
    private HashSet<Usuario> usuarios;
    private String mensaje;

    public Aviso() {
    }

    public Aviso(HashSet<Usuario> usuarios, String mensaje) {
        this.usuarios = usuarios;
        this.mensaje = mensaje;
    }

    protected Aviso(Parcel in) {
        mensaje = in.readString();
    }

    public static final Creator<Aviso> CREATOR = new Creator<Aviso>() {
        @Override
        public Aviso createFromParcel(Parcel in) {
            return new Aviso(in);
        }

        @Override
        public Aviso[] newArray(int size) {
            return new Aviso[size];
        }
    };

    public HashSet<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(HashSet<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(mensaje);
    }
}
