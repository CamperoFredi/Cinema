package com.cinema;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pelicula 
{
	private String nombrePelicula;
	//private int idFuncion;
	private String fechaCreacion = "00-00-0000";
	
	Statement stm = ConexionMysql.getStatement();
	ResultSet result = stm.executeQuery("select * from PELICULA");
	
	public Pelicula (String nombrePelicula, boolean EsAdmin) throws SQLException 
	{
		this.nombrePelicula = nombrePelicula;
		CrearPelicula (EsAdmin);
	}
	
	public String getNombrePelicula ()
	{
		return this.nombrePelicula;
	}
	
	public String getFechaCreacion ()
	{
		return this.fechaCreacion;
	}
	
	public void CrearPelicula (boolean EsAdmin) throws SQLException
	{
		String insertar;
		
		if (EsAdmin)
		{
			//Aqui se crea una cadena con todos los datos pasados al constructor
			insertar ="";
			insertar += "INSERT INTO PELICULA (NomPelicula, fecha) VALUE (";
			insertar += ("\'" + getNombrePelicula () + "\',");
			insertar += ("\'"+ getFechaCreacion () + "\')");
			
			//Con esta sentencia se sube la cadena a la base de datos
			stm.executeUpdate(insertar);
			
			System.out.println("Se registro la pelicula: " + this.nombrePelicula);
		}
		else
		{
			System.out.println("Debe ser administrador para crear una pelicula.");
		}
	}
}
