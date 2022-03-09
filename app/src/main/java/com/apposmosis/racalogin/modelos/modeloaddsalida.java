package com.apposmosis.racalogin.modelos;

import android.widget.Button;

import com.google.firebase.Timestamp;

public class modeloaddsalida {
    public Timestamp fecha;
    public String codigo;
    public String horadesalida;
    public Double horasextras;
    public String observacion;
    public Boolean agregar;
    public String nombres,apellidos;


    public modeloaddsalida(){}

    public modeloaddsalida(Timestamp fecha, String codigo, String horadesalida, Double horasextras, String observacion, Boolean agregar, String nombres, String apellidos) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.horadesalida = horadesalida;
        this.horasextras = horasextras;
        this.observacion = observacion;
        this.agregar = agregar;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getHoradesalida() {
        return horadesalida;
    }

    public void setHoradesalida(String horadesalida) {
        this.horadesalida = horadesalida;
    }

    public Double getHorasextras() {
        return horasextras;
    }

    public void setHorasextras(Double horasextras) {
        this.horasextras = horasextras;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isAgregar() {
        return agregar;
    }

    public void setAgregar(boolean agregar) {
        this.agregar = agregar;
    }

}
