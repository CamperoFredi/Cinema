package com.cinema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Cine {
    Statement conexionDB = ConexionMysql.getStatement();

    public void agregarNuevaSala(Sala nSala) throws SQLException {
        try {
            conexionDB.executeUpdate(
                    "INSERT INTO salas(SalaNro, NombreSala, PeliculaId, Horario, Capacidad, Tipo, FechaCreacion) VALUES('"
                            + nSala.getNroSala()
                            + "', '" + nSala.getNombreSala()
                            + "', '" + nSala.getPeliculaId()
                            + "', '" + nSala.getHorario()
                            + "', '" + nSala.getCapacidad()
                            + "', '" + nSala.getTipo()
                            + "', '" + nSala.getFechaCreacion() + "')");
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            try {
                conexionDB.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void modificarSala(Sala mSala) {
        try {
            conexionDB.executeUpdate(
                    "UPDATE salas SET SalaNro = '" + mSala.getNroSala()
                            + "', NombreSala = '" + mSala.getNombreSala()
                            + "', PeliculaId = '" + mSala.getPeliculaId()
                            + "', Horario = '" + mSala.getHorario()
                            + "', Capacidad = '" + mSala.getCapacidad()
                            + "', Tipo = '" + mSala.getTipo()
                            + "' WHERE Id = '" + mSala.getId() + "'");

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            try {
                conexionDB.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Boolean isAdmin(Usuario usuario) {

        String admin;
        ResultSet rs;
        try {
            rs = conexionDB.executeQuery("select rol from usuarios");
            while (rs.next()) {
                admin = rs.getString(1);
                if (admin.equals("admin")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public ArrayList<Sala> getListSalas() {
        try {
            Connection con = conexionDB.getConnection();
            String SQL = "SELECT * FROM salas";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            ArrayList<Sala> salas = new ArrayList<Sala>();
            while (rs.next()) {
                System.out.println(rs.getString("SalaNro") + ", " + rs.getString("NombreSala"));
                Sala sala = new Sala();
                sala.setNombreSala(rs.getString("nombreSala"));
                sala.setNroSala(rs.getString("SalaNro"));
                sala.setPeliculaId(rs.getString("PeliculaId"));
                sala.setHorario(rs.getString("Horario"));
                sala.setTipo(rs.getString("Tipo"));
                salas.add(sala);

            }
            return salas;

            // rs.close();
            // stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
