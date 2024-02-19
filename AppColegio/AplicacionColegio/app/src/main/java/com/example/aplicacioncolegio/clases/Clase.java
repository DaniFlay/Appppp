package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

public class Clase implements Serializable, Parcelable {
    int id;
    Modulo modulo;

    public Clase(int id, Modulo modulo) {
        this.id = id;
        this.modulo = modulo;
    }

    public Clase() {
    }

    @Override
    public String toString() {
        return "Clase{" +
                "id=" + id +
                ", modulo=" + modulo +
                '}';
    }

    protected Clase(Parcel in) {
        id = in.readInt();
        modulo = in.readParcelable(Modulo.class.getClassLoader());
    }

    public static final Creator<Clase> CREATOR = new Creator<Clase>() {
        @Override
        public Clase createFromParcel(Parcel in) {
            return new Clase(in);
        }

        @Override
        public Clase[] newArray(int size) {
            return new Clase[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(modulo, flags);
    }



}
