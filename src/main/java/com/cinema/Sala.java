package com.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sala {

    private Integer Id;
    private String SalaNro;
    private String NombreSala;
    private String PeliculaId;
    private String Horario;
    private Integer Capacidad;
    private String Tipo;
    private String Precio;
    private String FechaCreacion;
    Statement conexionDB = ConexionMysql.getStatement();

    public Sala(String salaNro, String nombreSala, String peliculaId, String horario, Integer capacidad, String tipo,
            String precio) {
        this.SalaNro = salaNro;
        this.NombreSala = nombreSala;
        this.PeliculaId = peliculaId;
        this.Horario = horario;
        this.Capacidad = capacidad;
        this.Tipo = tipo;
    }

    public Sala() {
    }

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getNroSala() {
        return this.SalaNro;
    }

    public void setNroSala(String nroSala) {
        this.SalaNro = nroSala;
    }

    public String getNombreSala() {
        return this.NombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.NombreSala = nombreSala;
    }

    public String getPeliculaId() {
        return this.PeliculaId;
    }

    public void setPeliculaId(String pId) {
        this.PeliculaId = pId;
    }

    public String getHorario() {
        return this.Horario;
    }

    public String getHorarioById(int idSala) {
        try {
            ResultSet rs = conexionDB.executeQuery("SELECT * FROM Salas WHERE Id = '" + idSala + "'");
            while (rs.next()) {
                this.Horario = rs.getString("Horario");
                return String.valueOf(rs.getString("Horario"));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return null;
    }

    public void setHorario(String horario) {
        this.Horario = horario;
    }

    public Integer getCapacidad() {
        return this.Capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.Capacidad = capacidad;
    }

    public String getTipo() {
        return this.Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public String getFechaCreacion() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCreate = localDate.format(formatterDate);
        return this.FechaCreacion = dateCreate;
    }

    public void setFechaCreacion() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCreate = localDate.format(formatterDate);
        this.FechaCreacion = dateCreate;
    }

    public String getPrecio() {
        return this.Precio;
    }

    public void setPrecio(String precio) {
        this.Precio = precio;
    }

}
