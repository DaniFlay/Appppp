package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Mensaje implements Serializable, Parcelable {
    private ArrayList<Usuario> usuarios;
    private String mensaje;

    public Mensaje(ArrayList<Usuario> usuarios, String mensaje) {
        this.usuarios = usuarios;
        this.mensaje = mensaje;
    }

    public Mensaje() {
    }

    protected Mensaje(Parcel in) {
        usuarios = in.createTypedArrayList(Usuario.CREATOR);
        mensaje = in.readString();
    }

    public static final Creator<Mensaje> CREATOR = new Creator<Mensaje>() {
        @Override
        public Mensaje createFromParcel(Parcel in) {
            return new Mensaje(in);
        }

        @Override
        public Mensaje[] newArray(int size) {
            return new Mensaje[size];
        }
    };

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
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
        dest.writeTypedList(usuarios);
        dest.writeString(mensaje);
    }
}
