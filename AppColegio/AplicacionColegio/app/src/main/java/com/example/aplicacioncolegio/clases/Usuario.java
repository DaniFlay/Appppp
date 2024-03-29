package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable, Parcelable {
    public String nombre;
    public String apellidos;
    public String correo;
    public String puesto;
    public String sexo;
    public String password;
    public boolean ausente;
    public Ciclo ciclo;
    public ArrayList<Modulo> modulos;

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String correo, String puesto, String sexo, String password, boolean ausente, Ciclo ciclo, ArrayList<Modulo> modulos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.puesto = puesto;
        this.sexo = sexo;
        this.password = password;
        this.ausente = ausente;
        this.ciclo = ciclo;
        this.modulos = modulos;
    }

    protected Usuario(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        correo = in.readString();
        puesto = in.readString();
        sexo = in.readString();
        password = in.readString();
        ausente = in.readByte() != 0;
        ciclo = in.readParcelable(Ciclo.class.getClassLoader());
        modulos = in.createTypedArrayList(Modulo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(correo);
        dest.writeString(puesto);
        dest.writeString(sexo);
        dest.writeString(password);
        dest.writeByte((byte) (ausente ? 1 : 0));
        dest.writeParcelable(ciclo, flags);
        dest.writeTypedList(modulos);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public boolean isAusente() {
        return ausente;
    }

    public void setAusente(boolean ausente) {
        this.ausente = ausente;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public ArrayList<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(ArrayList<Modulo> modulos) {
        this.modulos = modulos;
    }

    public String nombreApellidos() {
        return this.getNombre()+" "+this.getApellidos();
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
                ", ausente=" + ausente +
                ", ciclo=" + ciclo +
                ", modulos=" +modulos+
                '}';
    }
}
