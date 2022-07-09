package com.cinema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

}
