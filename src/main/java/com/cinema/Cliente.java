package com.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Cliente extends Usuario {
    private String nroTarjeta; // Numero de Tarjeta
    private String fecha; // Fecha de registro en el formato "dd-MM-yyyy"

    Statement stm = ConexionMysql.getStatement();

    // Constructor para el cliente
    public Cliente() throws SQLException {
    }

    // Getters

    public String getNroTarjeta() {
        return this.nroTarjeta;
    }

    public String getFecha() {
        return this.fecha;
    }

    private void setNroTarjeta(String nuevoNroTarjeta) {
        this.nroTarjeta = nuevoNroTarjeta;
    }

    // Registro de usuario
    public void RegistroDeUsuario() throws SQLException {
        String insertar;

        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese un nombre de usuario: \n");
        setNomUsuario(sc.nextLine());

        System.out.println("Ingrese una contraseña: \n");
        setContra(sc.nextLine());

        System.out.println("Ingrese su nombre y apellido: \n");
        setNyA(sc.nextLine());

        System.out.println("Ingrese su DNI: \n");
        setDNI(sc.nextLine());

        System.out.println("Ingrese su numero de tarjeta: \n");
        setNroTarjeta(sc.next());

        // Aqui se crea una cadena con todos los datos pasados al constructor
        insertar = "";
        insertar += "INSERT INTO USUARIO (nomUsuario, nya, dni, contra, nroTarjeta, fecha) VALUE (";
        insertar += ("\'" + getNomUsuario() + "\',");
        insertar += ("\'" + getnya() + "\',");
        insertar += ("\'" + getDNI() + ",");
        insertar += ("\'" + getcontra() + "\'" + ",");
        insertar += ("\'" + getNroTarjeta() + ",");
        insertar += ("\'" + getFecha() + "\')");

        // Con esta sentencia se sube la cadena a la base de datos
        stm.executeUpdate(insertar);

        System.out.println("Se registro el usuario: " + this.nomUsuario);
    }
}
