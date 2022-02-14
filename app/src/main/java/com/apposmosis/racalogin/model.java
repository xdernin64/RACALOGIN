package com.apposmosis.racalogin;

public class model {
    private  String fecha;
    private  String nombres;
    private  String horasalida;
    private  String horasextras;

    public model(){}
    public model(String fecha, String nombres,String horasalida,String horasextras){
        this.fecha=fecha;
        this.nombres=nombres;
        this.horasalida=horasalida;
        this.horasextras=horasextras;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(String horasalida) {
        this.horasalida = horasalida;
    }

    public String getHorasextras() {
        return horasextras;
    }

    public void setHorasextras(String horasextras) {
        this.horasextras = horasextras;
    }
}
