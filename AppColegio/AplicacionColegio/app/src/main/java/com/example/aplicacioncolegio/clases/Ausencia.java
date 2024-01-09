package com.example.aplicacioncolegio.clases;

import java.io.Serializable;

public class Ausencia implements Serializable {
    private Usuario profesor;
    private String razon;
    private String fechaInicio;
    private String fechaFin;
    private String horaInicio;
    private String horaFin;

    public Ausencia( Usuario profesor, String razon, String fechaInicio, String fechaFin, String horaInicio, String horaFin) {
        this.profesor = profesor;
        this.razon = razon;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Ausencia() {
    }


    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
