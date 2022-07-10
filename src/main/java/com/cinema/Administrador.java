package com.cinema;

import java.sql.SQLException;

public class Administrador extends Usuario {

    public Administrador(String nomUsuario, String nya, String dni, String contra) throws SQLException {
        super(nomUsuario, nya, dni, contra);
        this.admin = true;

    }

    public void RegistroDeAdmin() throws SQLException {
        String insertar;

        if (!this.SesionIniciada) {
            // Aqui se crea una cadena con todos los datos pasados al constructor
            insertar = "";
            insertar += "INSERT INTO USUARIO (nomUsuario, nya, dni, contra, nroTarjeta, fecha) VALUE (";
            insertar += ("\'" + getNomUsuario() + "\',");
            insertar += ("\'" + getnya() + "\',");
            insertar += (getDNI() + ",");
            insertar += ("\'" + getcontra() + "\'" + ",");
            insertar += (null + ",");
            insertar += ("\'" + null + "\')");

            // Con esta sentencia se sube la cadena a la base de datos
            stm.executeUpdate(insertar);

            System.out.println("Se registro el usuario: " + this.nomUsuario);
        } else {
            System.out.println("No puede registrar el usuario porque hay una sesion abierta.");
        }
    }

    public void CrearPelicula(String nombrePelicula) throws SQLException {
        new Pelicula(nombrePelicula, EsAdmin());
    }
}
