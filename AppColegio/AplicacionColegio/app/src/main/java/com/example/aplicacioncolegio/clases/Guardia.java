package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Guardia implements Serializable, Parcelable {
    private int id;
    private String nombreProfesor;
    private String apellidosProfesor;
    private String fecha;
    private String observaciones;

    public Guardia(int id, String nombreProfesor, String apellidosProfesor, String fecha, String observaciones) {
        this.id = id;
        this.nombreProfesor = nombreProfesor;
        this.apellidosProfesor = apellidosProfesor;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public Guardia() {
    }

    protected Guardia(Parcel in) {
        id = in.readInt();
        nombreProfesor = in.readString();
        apellidosProfesor = in.readString();
        fecha = in.readString();
        observaciones = in.readString();
    }

    public static final Creator<Guardia> CREATOR = new Creator<Guardia>() {
        @Override
        public Guardia createFromParcel(Parcel in) {
            return new Guardia(in);
        }

        @Override
        public Guardia[] newArray(int size) {
            return new Guardia[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getApellidosProfesor() {
        return apellidosProfesor;
    }

    public void setApellidosProfesor(String apellidosProfesor) {
        this.apellidosProfesor = apellidosProfesor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombreProfesor);
        dest.writeString(apellidosProfesor);
        dest.writeString(fecha);
        dest.writeString(observaciones);
    }
}
