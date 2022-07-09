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
    private int UsuarioId;
    private int SalaId;
    private int DescuentoId;
    private String DiaFuncion;
    private Boolean Modificada;
    private String Precio;
    private Boolean ConfirmaPago;
    private String FechaCreacion;

    Statement conexionDB = ConexionMysql.getStatement();

    public Reserva() {
    }

    public Reserva(int salaId, String diaFuncion, boolean modificada, String precio,
            boolean confirmaPago, String fechaCreacion) {
        this.SalaId = salaId;
        this.DiaFuncion = diaFuncion;
        this.Modificada = modificada;
        this.Precio = precio;
        this.ConfirmaPago = confirmaPago;
        this.FechaCreacion = fechaCreacion;
    }

    public int getSalaId() {
        return SalaId;
    }

    public void setSalaId(int salaId) {
        this.SalaId = salaId;
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

    public void setModificada(Boolean modificada) {
        this.Modificada = modificada ? modificada : false;
    }

    public String getPrecio() {
        return this.Precio;
    }

    public void setPrecio(String precio) {
        this.Precio = precio;
    }

    public Boolean getConfirmaPago() {
        return this.ConfirmaPago;
    }

    public void setConfirmaPago(Boolean confirmaPago) {
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

    public void agregarReserva(Reserva reser) throws SQLException {
        try {
            conexionDB.executeUpdate(
                    "INSERT INTO reservas(UsuarioId, SalaId, DescuentoId, DiaFuncion, Modificada, Precio, ConfirmaPago, FechaCreacion) VALUES(1, '"
                            + reser.getSalaId()
                            + "', '" + reser.getDescuentoId()
                            + "', '" + reser.getDiaFuncion()
                            + "', '" + reser.getModificada()
                            + "', '" + reser.getPrecio()
                            + "', '" + reser.getConfirmaPago()
                            + "', '" + reser.getFechaCreacion() + "')");

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            try {
                conexionDB.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public ArrayList<Reserva> getMisReservas(String dni) {
        try {
            Connection con = conexionDB.getConnection();
            String SQL = "SELECT * FROM reservas INNER JOIN usuarios ON reservas.UsuarioId = usuarios.Id WHERE usuarios.dni = "
                    + dni + ";";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            ArrayList<Reserva> reservas = new ArrayList<Reserva>();
            while (rs.next()) {
                System.out.println(rs.getString("DiaFuncion") + ", " + rs.getString("Precio"));
                Reserva r = new Reserva();
                r.setSalaId(rs.getInt("SalaNro"));
                r.setDiaFuncion(rs.getString("DiaFuncion"));
                r.setModificada(rs.getBoolean("Modificada"));
                r.setPrecio(rs.getString("Precio"));
                r.setConfirmaPago(rs.getBoolean("ConfirmaPago"));
                r.setFechaCreacion(rs.getString("FechaCreacion"));
                reservas.add(r);
            }
            return reservas;

            // rs.close();
            // stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
