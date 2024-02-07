package com.example.aplicacioncolegio.clases;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Notificacion implements Serializable, Parcelable {
    private Usuario emisor;
    private Usuario usuario;
    private String name;
    private String descripcion;
    private String fecha;
    private boolean visto;

    public Notificacion(Usuario emisor,Usuario usuario, String name, String descripcion, String fecha, boolean visto) {
        this.emisor=emisor;
        this.usuario = usuario;
        this.name = name;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.visto = visto;
    }

    public Notificacion() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notificacion)) return false;
        Notificacion that = (Notificacion) o;
        return isVisto() == that.isVisto() && getEmisor().getCorreo().equals(that.getEmisor().getCorreo()) && getUsuario().getCorreo().equals(that.getUsuario().getCorreo()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescripcion(), that.getDescripcion()) && Objects.equals(getFecha(), that.getFecha());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmisor(), getUsuario(), getName(), getDescripcion(), getFecha(), isVisto());
    }

    protected Notificacion(Parcel in) {
        emisor= in.readParcelable(Usuario.class.getClassLoader());
        usuario = in.readParcelable(Usuario.class.getClassLoader());
        name = in.readString();
        descripcion = in.readString();
        fecha = in.readString();
        visto = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(emisor,flags);
        dest.writeParcelable(usuario, flags);
        dest.writeString(name);
        dest.writeString(descripcion);
        dest.writeString(fecha);
        dest.writeByte((byte) (visto ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notificacion> CREATOR = new Creator<Notificacion>() {
        @Override
        public Notificacion createFromParcel(Parcel in) {
            return new Notificacion(in);
        }

        @Override
        public Notificacion[] newArray(int size) {
            return new Notificacion[size];
        }
    };

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "emisor=" + emisor +
                ", usuario=" + usuario +
                ", name='" + name + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", visto=" + visto +
                '}';
    }
}
