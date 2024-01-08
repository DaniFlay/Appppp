package com.example.aplicacioncolegio;

import android.bluetooth.BluetoothProfile;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable, Parcelable {
    private String tipo;
    private String fechaFin;

    public Tarea(String tipo, String fechaFin) {
        this.tipo = tipo;
        this.fechaFin = fechaFin;
    }

    public Tarea() {
    }


    protected Tarea(Parcel in) {
        tipo = in.readString();
        fechaFin = in.readString();
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String  getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @NonNull
    @Override
    public String toString() {
        return this.tipo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(tipo);
        dest.writeString(fechaFin);
    }
}
