package com.cinema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Descuentos {
    private int Id;
    private String DiaDescuento;
    private int Porcentaje;
    private String FechaCreacion;

    Statement conexionDB = ConexionMysql.getStatement();

    public Descuentos() {
    }

    public Descuentos(int id, String diaDescuento, int porcentaje, String fechaCreacion) {
        this.Id = id;
        this.DiaDescuento = diaDescuento;
        this.Porcentaje = porcentaje;
        this.FechaCreacion = fechaCreacion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getDiaDescuento() {
        return DiaDescuento;
    }

    public void setDiaDescuento(String diaDescuento) {
        this.DiaDescuento = diaDescuento;
    }

    public int getPorcentaje() {
        return Porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.Porcentaje = porcentaje;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.FechaCreacion = fechaCreacion;
    }

    public static int getDescuento(String dia) {
        int descuento = 0;
        switch (dia) {
            case "Lunes":
            case "Miercoles":
                descuento = 20;
                break;
            case "Martes":
            case "Jueves":
                descuento = 15;
                break;
            case "Viernes":
            case "Sabado":
            case "Domingo":
                descuento = 10;
                break;
        }
        return descuento;
    }

    public String toString() {
        return "Descuentos: " + "Dia de descuento = " + DiaDescuento + ", Porcentaje = " + Porcentaje + "%.";
    }

    public static String GetDiaDescuentoActual() throws ParseException {
        String dateFormat = getFechaActual();
        String inputDateStr = String.format(dateFormat);
        Date inputDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
        String diaEsp = formatFecha(dayOfWeek);
        int descuento = getDescuento(diaEsp);
        return diaEsp + " " + descuento + "%";
    }

    public static String formatFecha(String fechaIng) {
        String dia = "";
        switch (fechaIng) {
            case "MONDAY":
                dia = "Lunes";
                break;
            case "TUESDAY":
                dia = "Martes";
                break;
            case "WEDNESDAY":
                dia = "Miercoles";
                break;
            case "THURSDAY":
                dia = "Jueves";
                break;
            case "FRIDAY":
                dia = "Viernes";
                break;
            case "SATURDAY":
                dia = "Sabados";
                break;
            case "SUNDAY":
                dia = "Domingos";
                break;
        }
        return dia;
    }

    public static String getFechaActual() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String modificarDescuento(int id, int desc) {
        try {
            conexionDB.executeUpdate(
                    "UPDATE descuentos SET Porcentaje = " + desc
                            + " WHERE Id = " + id);

            return "Descuento modificado correctamente.";
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public Integer getIdDescuento(String dia) {
        Integer id = 0;
        try {
            conexionDB.executeUpdate("SELECT Id FROM descuentos WHERE DiaDescuento = '" + dia + "';");
            id = conexionDB.getResultSet().getInt("Id");
            return id;
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return id;
    }

    public ArrayList<String> getListDescuentos() {
        try {
            Connection con = conexionDB.getConnection();
            String SQL = "SELECT * FROM Descuentos;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            ArrayList<String> salas = new ArrayList<>();
            while (rs.next()) {
                System.out.println("Id: " + rs.getInt("Id") + " Dia: " + rs.getString("DiaDescuento")
                        + " Descuento: " + rs.getInt("Porcentaje") + "%");
                salas.add(rs.getString("Id"));
                salas.add(rs.getString("DiaDescuento"));
                salas.add(rs.getString("Porcentaje"));
            }
            return salas;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
