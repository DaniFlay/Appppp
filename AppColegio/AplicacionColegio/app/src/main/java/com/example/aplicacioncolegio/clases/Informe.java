package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Informe implements Serializable, Parcelable {
    private Usuario recibidor;
    private Usuario enviador;
    private String fecha;
    private String observaciones;

    public Informe(Usuario recibidor, Usuario enviador, String fecha, String observaciones) {
        this.recibidor = recibidor;
        this.enviador = enviador;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public Informe() {
    }


    protected Informe(Parcel in) {
        recibidor = in.readParcelable(Usuario.class.getClassLoader());
        enviador = in.readParcelable(Usuario.class.getClassLoader());
        fecha = in.readString();
        observaciones = in.readString();
    }

    public static final Creator<Informe> CREATOR = new Creator<Informe>() {
        @Override
        public Informe createFromParcel(Parcel in) {
            return new Informe(in);
        }

        @Override
        public Informe[] newArray(int size) {
            return new Informe[size];
        }
    };

    public Usuario getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(Usuario recibidor) {
        this.recibidor = recibidor;
    }

    public Usuario getEnviador() {
        return enviador;
    }

    public void setEnviador(Usuario enviador) {
        this.enviador = enviador;
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
        dest.writeParcelable(recibidor, flags);
        dest.writeParcelable(enviador, flags);
        dest.writeString(fecha);
        dest.writeString(observaciones);
    }
}
