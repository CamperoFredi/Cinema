package com.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cliente extends Usuario {
    private long nroTarjeta; // Numero de Tarjeta
    private String fecha; // Fecha de registro en el formato "dd-MM-yyyy"

    Statement stm = ConexionMysql.getStatement();

    public Cliente() {
    }

    // Constructor para el cliente
    public Cliente(String nomUsuario, String nya, long dni, String contra, long nroTarjeta, String fecha)
            throws SQLException {
        super(nomUsuario, nya, dni, contra);
        this.nroTarjeta = nroTarjeta;
        this.fecha = fecha;
        RegistroDeUsuario();
    }

    // Constructor para el administrador
    /*
     * public Cliente (String nomUsuario, String nya, long dni, String contra,
     * String fecha, boolean admin) throws SQLException
     * {
     * this.nomUsuario = nomUsuario;
     * this.nya = nya;
     * this.dni = dni;
     * this.contra = contra;
     * this.fecha = fecha;
     * this.admin = true;
     * RegistroDeUsuario ();
     * }
     */

    // Getters

    public long getNroTarjeta() {
        return this.nroTarjeta;
    }

    public String getFecha() {
        return this.fecha;
    }

    // Registro de usuario
    public void RegistroDeUsuario() throws SQLException {
        String insertar;

        if (!this.SesionIniciada) {
            // Aqui se crea una cadena con todos los datos pasados al constructor
            insertar = "";
            insertar += "INSERT INTO Usuarios (nomUsuario, nya, dni, contra, nroTarjeta, fecha) VALUE (";
            insertar += ("\'" + getNomUsuario() + "\',");
            insertar += ("\'" + getnya() + "\',");
            insertar += (getDNI() + ",");
            insertar += ("\'" + getcontra() + "\'" + ",");
            insertar += (getNroTarjeta() + ",");
            insertar += ("\'" + getFecha() + "\')");

            // Con esta sentencia se sube la cadena a la base de datos
            stm.executeUpdate(insertar);

            System.out.println("Se registro el usuario: " + this.nomUsuario);
        } else {
            System.out.println("No puede registrar el usuario porque hay una sesion abierta.");
        }
    }
}
