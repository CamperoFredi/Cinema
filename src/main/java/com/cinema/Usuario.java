package com.cinema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Usuario {
    private String Nombre;
    private String Apellido;
    private String Documento;
    private String Rol;
    private Boolean Activo;
    private String Password;
    private String FechaRegistro;

    public Usuario(String nombre, String apellido, String documento, String password) {
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Documento = documento;
        this.Password = password;
    }

    public String getNombre() {
        return this.Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getApellido() {
        return this.Apellido;
    }

    public void setApellido(String apellido) {
        this.Apellido = apellido;
    }

    public String getDocumento() {
        return this.Documento;
    }

    public void setDocumento(String documento) {
        this.Documento = documento;
    }

    public String getRol() {
        return this.Rol = "Cliente";
    }

    public void setRol() {
        this.Rol = "Cliente";
    }

    public Boolean getActivo() {
        return this.Activo = true;
    }

    public void setActivo() {
        this.Activo = true;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getFechaRegistro() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCreate = localDate.format(formatterDate);
        return this.FechaRegistro = dateCreate;
    }

    public void setFechaRegistro(String fechaRegistro) {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCreate = localDate.format(formatterDate);
        this.FechaRegistro = dateCreate;
    }

}
