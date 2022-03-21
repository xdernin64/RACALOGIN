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
    public String apellidosynombres,area,labor;


    public modeloaddsalida(){}

    public modeloaddsalida(Timestamp fecha, String codigo, String horadesalida, Double horasextras, String observacion, Boolean agregar, String nombres, String apellidos, String apellidosynombres, String area, String labor) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.horadesalida = horadesalida;
        this.horasextras = horasextras;
        this.observacion = observacion;
        this.agregar = agregar;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.apellidosynombres = apellidosynombres;
        this.area = area;
        this.labor = labor;
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

    public String getApellidosynombres() {
        return apellidosynombres;
    }

    public void setApellidosynombres(String apellidosynombres) {
        this.apellidosynombres = apellidosynombres;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }
}
