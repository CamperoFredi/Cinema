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
    public Cliente() throws SQLException {
    }

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

        System.out.println("Ingrese un nombre de usuario: \n");
        setNomUsuario (sc.nextLine());
        
        System.out.println("Ingrese una contraseña: \n");
        setContra (sc.nextLine());
        
        System.out.println("Ingrese su nombre y apellido: \n");
        setNyA (sc.nextLine());
        
        System.out.println("Ingrese su DNI: \n");
        setDNI (sc.nextLine());
        
        System.out.println("Ingrese su numero de tarjeta: \n");
        setNroTarjeta (sc.nextLong());

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
    }
}
