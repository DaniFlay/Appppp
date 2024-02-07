package com.example.aplicacioncolegio.encapsuladores;

public class EncapsuladorNotificaciones {
    int imagen;
    String nombre,fecha, descripcion;

    public EncapsuladorNotificaciones(int imagen, String nombre, String fecha, String descripcion) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion= descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
