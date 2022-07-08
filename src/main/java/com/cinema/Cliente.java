package com.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cliente
{
	private String nomUsuario;		//Nombre de Usuario
	private String nya;				//Nombre y Apellido
	private long dni;
	private String contra;			//Contrase�a
	private long nroTarjeta;		//Numero de Tarjeta
	private String fecha;			//Fecha de registro en el formato "dd-MM-yyyy"
	private boolean admin = false;
	private boolean SesionIniciada = false;
	/*
	La variable SesionIniciada tiene varias funciones:
	* Controla que no se inicie mas de una sesion
	* Siempre que no haya ninguna sesion abierta se pueden registrar n clientes
	* Cuando este una sesion abierta no se permite el registro de algun cliente
	*/
	Statement stm = ConexionMysql.getStatement();
	ResultSet result = stm.executeQuery("select * from USUARIO");
	
	//Constructor para el cliente
	public Cliente (String nomUsuario, String nya, long dni, String contra, long nroTarjeta, String fecha) throws SQLException
	{
		this.nomUsuario = nomUsuario;
		this.nya = nya;
		this.dni = dni;
		this.contra = contra;
		this.nroTarjeta = nroTarjeta;
		this.fecha = fecha;
		RegistroDeUsuario ();
	}
	
	//Constructor para el administrador
	public Cliente (String nomUsuario, String nya, long dni, String contra, long nroTarjeta, String fecha, boolean admin) throws SQLException
	{
		this.nomUsuario = nomUsuario;
		this.nya = nya;
		this.dni = dni;
		this.contra = contra;
		this.nroTarjeta = nroTarjeta;
		this.fecha = fecha;
		this.admin = admin;
		RegistroDeUsuario ();
	}
	
	//Getters
	public String getNomUsuario ()
	{
		return this.nomUsuario;
	}
	
	public String getnya ()
	{
		return this.nya;
	}
	
	public long getDNI ()
	{
		return this.dni;
	}
	
	public String getcontra ()
	{
		return this.contra;
	}
	
	public long getNroTarjeta ()
	{
		return this.nroTarjeta;
	}
	
	public String getFecha ()
	{
		return this.fecha;
	}
	
	//Registro de usuario
	public void RegistroDeUsuario () throws SQLException
	{
		String insertar;
		
		if (!this.SesionIniciada)
		{
			//Aqui se crea una cadena con todos los datos pasados al constructor
			insertar ="";
			insertar += "INSERT INTO USUARIO (nomUsuario, nya, dni, contra, nroTarjeta, fecha) VALUE (";
			insertar += ("\'" + getNomUsuario () + "\',");
			insertar += ("\'"+ getnya () + "\',");
			insertar += (getDNI () + ",");
			insertar += ("\'" + getcontra ()+ "\'" + ",");
			insertar += (getNroTarjeta () + ",");
			insertar += ("\'" + getFecha ()+ "\')");
			
			//Con esta sentencia se sube la cadena a la base de datos
			stm.executeUpdate(insertar);
			
			System.out.println("Se registro el usuario: " + this.nomUsuario);
		}
		else
		{
			System.out.println("No puede registrar el usuario porque hay una sesion abierta.");
		}
	}
	
	//Inicio de Sesion
	public void IniciarSesion (String nombreUsuario, String contra) throws SQLException
	{
		
		if (this.SesionIniciada)
		{
			System.out.println("Hay una sesion iniciada. Cierrela si quiere iniciar sesion con una cuenta nueva");
		}
		else
		{
			//Se compara el nombre de usuario y contrasenia de la base de datos con el nombre de usuario y contrasenia pasados por parametro 
			while (result.next () && !this.SesionIniciada)
			{
				if (nombreUsuario.equals(result.getString("nomUsuario")) && contra.equals(result.getString("contra")))
				{
					this.SesionIniciada = true;			//Si el nombre de usuario y la contrasenia coinciden, se pone a la sesion iniciada en true
				}
			}
			if (this.SesionIniciada)
			{
				System.out.println("Sesion iniciada.");
			}
			else
			{
				System.out.println("Nombre de usuario o contrase�a incorrectos.");
			}
		}
		
	}
	
	//Cierre de Sesion
	public void CerrarSesion ()
	{
		this.SesionIniciada = false;
	}
	
	//Pregunta si el cliente es un administrador para poder realizar las otras operaciones que no puede hacer un cliente
	public boolean EsAdmin ()
	{
		return this.admin;
	}
}
