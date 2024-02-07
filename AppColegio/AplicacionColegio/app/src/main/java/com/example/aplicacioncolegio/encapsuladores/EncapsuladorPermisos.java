package com.example.aplicacioncolegio.encapsuladores;

public class EncapsuladorPermisos {
    int imagen;
    String razon,estado;

    public EncapsuladorPermisos(int imagen, String razon, String estado) {
        this.imagen = imagen;
        this.razon = razon;
        this.estado = estado;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
