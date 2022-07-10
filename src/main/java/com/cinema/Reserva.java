package com.cinema;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Reserva {
    private int Id;
    private int UsuarioId;
    private int SalaId;
    private int DescuentoId;
    private String DiaFuncion;
    private String Modificada;
    private String Precio;
    private String ConfirmaPago;
    private String FechaCreacion;

    Statement conexionDB = ConexionMysql.getStatement();

    public Reserva() {
    }

    public Reserva(int usuarioId, int salaId, int idDescuent, String diaFuncion, String modificada, String precio,
            String confirmaPago) {
        this.UsuarioId = usuarioId;
        this.SalaId = salaId;
        this.DescuentoId = idDescuent;
        this.DiaFuncion = diaFuncion;
        this.Modificada = modificada;
        this.Precio = precio;
        this.ConfirmaPago = confirmaPago;
    }

    public int getSalaId() {
        return SalaId;
    }

    public void setSalaId(int salaId) {
        this.SalaId = salaId;
    }

    public int getUsuarioId() {
        return this.UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.UsuarioId = usuarioId;
    }

    public String getDiaFuncion() {
        return DiaFuncion;
    }

    public void setDiaFuncion(String diaFuncion) {
        this.DiaFuncion = diaFuncion;
    }

    public Boolean getModificada() {
        return false;
    }

    public void setModificada(String modificada) {
        this.Modificada = modificada;
    }

    public String getPrecio() {
        return this.Precio;
    }

    public void setPrecio(String precio) {
        this.Precio = precio;
    }

    public String getConfirmaPago() {
        return this.ConfirmaPago;
    }

    public void setConfirmaPago(String confirmaPago) {
        this.ConfirmaPago = confirmaPago;
    }

    public String getFechaCreacion() {
        return this.FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateCreate = localDate.format(formatterDate);
        this.FechaCreacion = dateCreate;
    }

    public int getDescuentoId() {
        return this.DescuentoId;
    }

    public void setDescuentoId(int descuentoId) {
        this.DescuentoId = descuentoId;
    }

    public String agregarReserva(Reserva reser) throws SQLException {
        try {
            conexionDB.executeUpdate(
                    "INSERT INTO reservas(UsuarioId, SalaId, DescuentoId, DiaFuncion, Modificada, Precio, ConfirmaPago, FechaCreacion) VALUES("
                            + reser.getUsuarioId()
                            + ", '" + reser.getSalaId()
                            + "', '" + reser.getDescuentoId()
                            + "', '" + reser.getDiaFuncion()
                            + "', '" + reser.getModificada()
                            + "', '" + reser.getPrecio()
                            + "', '" + reser.getConfirmaPago()
                            + "', '" + reser.getFechaCreacion() + "')");
            return "Se agrego la reserva";
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public ArrayList<String> getMisReservas(String dni) {
        try {
            Connection con = conexionDB.getConnection();
            String SQL = "SELECT P.NomPelicula, S.SalaNro, S.NombreSala, R.DiaFuncion, R.Precio, R.ConfirmaPago FROM Reservas R "
                    + "INNER JOIN Usuarios U ON R.UsuarioId = U.Id"
                    + "INNER JOIN Salas S ON R.SalaId = S.Id"
                    + "INNER JOIN Peliculas P ON S.PeliculaId = P.Id" +
                    "WHERE U.Dni = '" + dni + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            ArrayList<String> r = new ArrayList<>();
            while (rs.next()) {
                // System.out.println(rs.getString("DiaFuncion") + ", " +
                // rs.getString("Precio"));
                r.add(rs.getString("NomPelicula"));
                r.add(rs.getString("SalaNro"));
                r.add(rs.getString("NombreSala"));
                r.add(rs.getString("DiaFuncion"));
                r.add(rs.getString("Precio"));
                r.add(rs.getString("ConfirmaPago"));
            }
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modificarReservas(Reserva reser) {
        try {
            conexionDB.executeUpdate(
                    "UPDATE Reservas SET UsuarioId = '1', SalaId = '"
                            + reser.getSalaId()
                            + "', DescuentoId = '" + reser.getDescuentoId()
                            + "', DiaFuncion = '" + reser.getDiaFuncion()
                            + "', Modificada = '" + reser.getModificada()
                            + "', Precio = '" + reser.getPrecio()
                            + "', ConfirmaPago = '" + reser.getConfirmaPago()
                            + "', FechaCreacion = '" + reser.getFechaCreacion() + "'");
            return "Reserva modificada correctamente";
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public ResultSet getReserva(String dni, int reservaId) {
        try {
            Connection con = conexionDB.getConnection();
            String SQL = "SELECT P.NomPelicula, S.SalaNro, S.NombreSala, R.DiaFuncion, R.Precio, R.ConfirmaPago, R.DescuentoId, R.Precio FROM Reservas R "
                    + "INNER JOIN Usuarios U ON R.UsuarioId = U.Id"
                    + "INNER JOIN Salas S ON R.SalaId = S.Id"
                    + "INNER JOIN Peliculas P ON S.PeliculaId = P.Id" +
                    "WHERE U.Dni = '" + dni + "' AND R.Id = '" + reservaId + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                return rs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
