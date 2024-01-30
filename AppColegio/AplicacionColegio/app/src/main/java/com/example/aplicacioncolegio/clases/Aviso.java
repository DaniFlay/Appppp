package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Aviso implements Serializable, Parcelable {
    private Usuario emisor;
    private ArrayList<Usuario> usuarios;
    private String mensaje;

    public Aviso(Usuario emisor, ArrayList<Usuario> usuarios, String mensaje) {
        this.emisor = emisor;
        this.usuarios = usuarios;
        this.mensaje = mensaje;
    }

    public Aviso() {
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

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

    protected Aviso(Parcel in) {
        emisor = in.readParcelable(Usuario.class.getClassLoader());
        usuarios = in.createTypedArrayList(Usuario.CREATOR);
        mensaje = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(emisor, flags);
        dest.writeTypedList(usuarios);
        dest.writeString(mensaje);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
