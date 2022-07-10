package com.cinema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Cine {
    Statement conexionDB = ConexionMysql.getStatement();

    public String agregarNuevaSala(Sala nSala) throws SQLException {
        try {
            conexionDB.executeUpdate(
                    "INSERT INTO Salas(SalaNro, NombreSala, PeliculaId, Horario, Capacidad, Tipo, Precio, FechaCreacion) VALUES('"
                            + nSala.getNroSala()
                            + "', '" + nSala.getNombreSala()
                            + "', '" + nSala.getPeliculaId()
                            + "', '" + nSala.getHorario()
                            + "', '" + nSala.getCapacidad()
                            + "', '" + nSala.getTipo()
                            + "', '" + nSala.getPrecio()
                            + "', '" + nSala.getFechaCreacion() + "')");
            return "Se agrego la sala " + nSala.getNombreSala() + " correctamente.";
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String modificarSala(Sala mSala) {
        try {
            conexionDB.executeUpdate(
                    "UPDATE Salas SET SalaNro = '" + mSala.getNroSala()
                            + "', NombreSala = '" + mSala.getNombreSala()
                            + "', PeliculaId = '" + mSala.getPeliculaId()
                            + "', Horario = '" + mSala.getHorario()
                            + "', Capacidad = '" + mSala.getCapacidad()
                            + "', Tipo = '" + mSala.getTipo()
                            + "' WHERE Id = '" + mSala.getId() + "'");
            return "Se actualizo la sala " + mSala.getNombreSala() + " correctamente.";
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public Boolean isAdmin(Usuario usuario) {

        String admin;
        ResultSet rs;
        try {
            rs = conexionDB.executeQuery("SELECT * FROM Usuarios WHERE dni = '" + usuario.getDNI() + "'");
            while (rs.next()) {
                admin = rs.getString("admin");
                if (admin.equals("1")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    public ArrayList<String> getListSalas() {
        try {
            Connection con = conexionDB.getConnection();
            String SQL = "SELECT * FROM Salas S INNER JOIN Peliculas P ON S.PeliculaId = P.Id;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            ArrayList<String> salas = new ArrayList<>();
            while (rs.next()) {
                System.out.println(rs.getString("SalaNro")
                        + " -Nombre " + rs.getString("nombreSala")
                        + " -Pelicula " + rs.getString("NomPelicula")
                        + " -Horario " + rs.getString("Horario")
                        + " -Capacidad " + rs.getString("Capacidad")
                        + " -Tipo " + rs.getString("Tipo")
                        + " -Precio " + rs.getString("Precio"));
                salas.add(rs.getString("Horario"));
                salas.add(rs.getString("SalaNro"));
                salas.add(rs.getString("nombreSala"));
                salas.add(rs.getString("PeliculaId"));
                salas.add(rs.getString("Horario"));
                salas.add(rs.getString("Tipo"));
                salas.add(rs.getString("Precio"));
            }
            return salas;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String borrarSala(int idSala) {
        try {
            conexionDB.executeUpdate("DELETE FROM Salas WHERE Id = '" + idSala + "'");
            return "Sala eliminada correctamente.";
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

}
