package com.apposmosis.racalogin;


import android.widget.Button;

import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class model {
      Timestamp fecha;
      String codigo;
      String horadesalida;
      Double horasextra;
      public String observacion, apellidosynombres;
      String docid;
      Button elimnar;

      public model(){}

    public model(Timestamp fecha, String codigo, String horadesalida, Double horasextra, String observacion, String apellidosynombres, String docid, Button elimnar) {
        this.fecha = fecha;
        this.codigo = codigo;
        this.horadesalida = horadesalida;
        this.horasextra = horasextra;
        this.observacion = observacion;
        this.apellidosynombres = apellidosynombres;
        this.docid = docid;
        this.elimnar = elimnar;
    }

    public model(Timestamp fecha, String codigo, String horadesalida, Double horasextra){
        this.fecha=fecha;
        this.codigo=codigo;
        this.horadesalida=horadesalida;
        this.horasextra=horasextra;
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

    public Double getHorasextra() {
        return horasextra;
    }

    public void setHorasextra(Double horasextra) {
        this.horasextra = horasextra;

    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setElimnar(Button elimnar) {
        this.elimnar = elimnar;
    }
}
