package com.cinema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Pelicula {
    private String nombrePelicula;
    // private int idFuncion;
    private String fechaCreacion;

    Statement stm = ConexionMysql.getStatement();
    // ResultSet result = stm.executeQuery("select * from PELICULA");

    public Pelicula(String nombrePelicula) throws SQLException {
        this.nombrePelicula = nombrePelicula;
        // CrearPelicula(EsAdmin);
    }

    public Pelicula() {
    }

    public String getNombrePelicula() {
        return this.nombrePelicula;
    }

    public String getFechaCreacion() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCreate = localDate.format(formatterDate);
        return this.fechaCreacion = dateCreate;
    }

    public void CrearPelicula (String nombre) throws SQLException
	{
		String insertar;
		this.nombrePelicula = nombre;

		//Aqui se crea una cadena con todos los datos pasados al constructor
		insertar ="";
		insertar += "INSERT INTO PELICULA (NomPelicula, fecha) VALUE (";
		insertar += ("\'" + getNombrePelicula () + "\',");
		insertar += ("\'"+ getFechaCreacion () + "\')");
		
		//Con esta sentencia se sube la cadena a la base de datos
		stm.executeUpdate(insertar);
		
		System.out.println("Se registro la pelicula: " + this.nombrePelicula);
    }

    public ArrayList<String> getPeliculas() {
        try {
            Connection con = stm.getConnection();
            String SQL = "SELECT * FROM Peliculas";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            ArrayList<String> r = new ArrayList<>();
            while (rs.next()) {
                System.out.println("Id: " + rs.getString("Id") + " Pelicula: " + rs.getString("NomPelicula"));
            }
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
