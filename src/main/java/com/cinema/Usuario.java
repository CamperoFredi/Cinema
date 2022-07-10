package com.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Usuario {
    protected String nomUsuario; // Nombre de Usuario
    protected String nya; // Nombre y Apellido
    protected String dni;
    protected String contra; // contrasenia
    protected boolean SesionIniciada = false;
    protected boolean admin;

    /*
     * La variable SesionIniciada tiene varias funciones:
     * Controla que no se inicie mas de una sesion
     * Siempre que no haya ninguna sesion abierta se pueden registrar n clientes
     * Cuando este una sesion abierta no se permite el registro de algun cliente
     */

    Statement stm = ConexionMysql.getStatement();
    // ResultSet result = stm.executeQuery("SELECT * from USUARIO");

    public Usuario(String nomUsuario, String nya, String dni, String contra) throws SQLException {
        this.nomUsuario = nomUsuario;
        this.nya = nya;
        this.dni = dni;
        this.contra = contra;
        this.admin = false;
    }

    public Usuario() {
    }

    public String getNomUsuario() {
        return this.nomUsuario;
    }

    public String getnya() {
        return this.nya;
    }

    public String getDNI() {
        return this.dni;
    }

    public String getcontra() {
        return this.contra;
    }

    public void setNomUsuario(String nuevoNombre) {
        this.nomUsuario = nuevoNombre;
    }

    public void setNyA(String nuevoNyA) {
        this.nya = nuevoNyA;
    }

    public void setDNI(String nuevoDNI) {
        this.dni = nuevoDNI;
    }

    public void setContra(String nuevaContra) {
        this.contra = nuevaContra;
    }

    // Inicio de Sesion
    public void IniciarSesion() throws SQLException {

        String nombreUsuario;
        String contra;

        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese nombre de Usuario: \n");
        nombreUsuario = sc.nextLine();

        System.out.println("Ingrese contraseña: \n");
        contra = sc.nextLine();

        ResultSet result = stm.executeQuery("select * from USUARIO");
        // Se compara el nombre de usuario y contrasenia de la base de datos con el
        // nombre de usuario y contrasenia pasados por parametro
        while (result.next() && !this.SesionIniciada) {
            if (nombreUsuario.equals(result.getString("nomUsuario")) && contra.equals(result.getString("contra"))) {
                setNomUsuario(result.getString("nomUsuario"));
                setDNI(result.getString("dni"));
                this.SesionIniciada = true; // Si el nombre de usuario y la contrasenia coinciden, se pone a la sesion
                                            // iniciada en true
            }
        }
        if (this.SesionIniciada) {
            System.out.println("Sesion iniciada con el usuario: " + nombreUsuario);
        } else {
            System.out.println("Nombre de usuario o contrasenia incorrectos.");
        }
        sc.close();

    }

    // Cierre de Sesion
    public void CerrarSesion() {
        this.SesionIniciada = false;
        System.out.println("Session cerrada.");
    }

    // Pregunta si el cliente es un administrador para poder realizar las otras
    // operaciones que no puede hacer un cliente
    public boolean EsAdmin() {
        return this.admin;
    }
}
