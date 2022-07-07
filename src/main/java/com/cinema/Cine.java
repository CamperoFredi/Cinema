package com.cinema;

import java.sql.SQLException;
import java.sql.Statement;

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
        return true;
    }
}
