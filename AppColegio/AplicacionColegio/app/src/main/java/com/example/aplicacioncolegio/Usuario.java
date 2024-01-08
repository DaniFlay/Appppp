package com.example.aplicacioncolegio;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Usuario implements Serializable, Parcelable {
    private String nombre;
    private String apellidos;
    private String correo;
    private String puesto;
    private String sexo;
    private String password;
    private boolean ausente;

    public Usuario( String nombre, String apellidos, String correo, String puesto, String sexo, String password, boolean ausente) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.puesto = puesto;
        this.sexo = sexo;
        this.password = password;
        this.ausente= ausente;
    }


    public Usuario() {
    }

    protected Usuario(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        correo = in.readString();
        puesto = in.readString();
        sexo = in.readString();
        password = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public boolean isAusente() {
        return ausente;
    }

    public void setAusente(boolean ausente) {
        this.ausente = ausente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", puesto='" + puesto + '\'' +
                ", sexo='" + sexo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(correo);
        dest.writeString(puesto);
        dest.writeString(sexo);
        dest.writeString(password);
    }
}
