package com.apposmosis.racalogin;

public class usuarios {
    private  String codigo;
    private  String apellidos;
    private  String nombres;
    private  String celular;
    private  String correo;
    private  String password;
    private  String uid;
    public usuarios(){

    }
    public usuarios(String codigo,String apellidos,String nombres,String celular,String correo,String password,String uid){
        this.codigo=codigo;
        this.apellidos=apellidos;
        this.nombres=nombres;
        this.celular=celular;
        this.correo=correo;
        this.password=password;
        this.password=uid;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
