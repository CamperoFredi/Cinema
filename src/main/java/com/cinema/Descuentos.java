package com.cinema;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// -	Lunes y  Miércoles: 20%
// -	Martes y  Jueves: 15%
// -	Viernes, Sábados y Domingos: 10%

public class Descuentos {
    private int Id;
    private String DiaDescuento;
    private int Porcentaje;
    private String FechaCreacion;

    Statement conexionDB = ConexionMysql.getStatement();

    // Id, DiaDesceunto, Porcentaje, FechaCreacion
    public Descuentos() {
    }

    public Descuentos(String diaDescuento, int porcentaje, String fechaCreacion) {
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

    // - Lunes y Miércoles: 20%
    // - Martes y Jueves: 15%
    // - Viernes, Sábados y Domingos: 10%
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
        return "Descuento: " + diaEsp + " " + descuento + "%";
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
                dia = "Sabado";
                break;
            case "SUNDAY":
                dia = "Domingo";
                break;
        }
        return dia;
    }

    public static String getFechaActual() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String modificarReserva(Descuentos desc) {
        try {
            conexionDB.executeUpdate(
                    "UPDATE descuentos SET Porcentaje = " + desc.getPorcentaje()
                            + "' WHERE Id = " + desc.getId() + ";");

            return "Descuento modificado correctamente.";
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

}
