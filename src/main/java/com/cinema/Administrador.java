package com.cinema;

import java.sql.SQLException;
import java.util.Scanner;

public class Administrador extends Usuario
{
	
	public Administrador () throws SQLException
	{
		this.admin = true;
	}
	
	public void RegistroDeAdmin () throws SQLException
	{
		Scanner sc = new Scanner (System.in);
		String insertar;

		System.out.println("Ingrese un nombre de usuario: \n");
		setNomUsuario (sc.nextLine());
		
		System.out.println("Ingrese una contraseña: \n");
		setContra (sc.nextLine());
		
		System.out.println("Ingrese su nombre y apellido: \n");
		setNyA (sc.nextLine());
		
		System.out.println("Ingrese su DNI: \n");
		setDNI (sc.nextLine());
		
		//Aqui se crea una cadena con todos los datos pasados al constructor
		insertar ="";
		insertar += "INSERT INTO USUARIO (nomUsuario, nya, dni, contra, nroTarjeta, fecha) VALUE (";
		insertar += ("\'" + getNomUsuario () + "\',");
		insertar += ("\'" + getnya () + "\',");
		insertar += ("\'" + getDNI () + ",");
		insertar += ("\'" + getcontra ()+ "\'" + ",");
		insertar += (null + ",");
		insertar += ("\'" + null + "\')");
		
		//Con esta sentencia se sube la cadena a la base de datos
		stm.executeUpdate(insertar);
		
		System.out.println("Se registro el usuario: " + this.nomUsuario);
		sc.close();
	}
	
	public void CrearPelicula (String nombrePelicula) throws SQLException
	{
		new Pelicula (nombrePelicula);
	}
}
