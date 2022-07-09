package com.cinema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nomUsuario; // Nombre de Usuario
    private String nya; // Nombre y Apellido
    private long dni;
    private String contra; // Contrase�a
    private long nroTarjeta; // Numero de Tarjeta
    private String fecha; // Fecha de registro en el formato "dd-MM-yyyy"
    private boolean admin = false;
    private boolean SesionIniciada = false;
    private List<Reserva> reservas;
    /*
     * La variable SesionIniciada tiene varias funciones:
     * Controla que no se inicie mas de una sesion
     * Siempre que no haya ninguna sesion abierta se pueden registrar n clientes
     * Cuando este una sesion abierta no se permite el registro de algun cliente
     */
    Statement stm = ConexionMysql.getStatement();

    public Cliente() {
    }

    // Constructor para el cliente
    public Cliente(String nomUsuario, String nya, long dni, String contra, long nroTarjeta, String fecha) {
        this.nomUsuario = nomUsuario;
        this.nya = nya;
        this.dni = dni;
        this.contra = contra;
        this.nroTarjeta = nroTarjeta;
        this.fecha = fecha;
        try {
            RegistroDeUsuario();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Constructor para el administrador
    public Cliente(String nomUsuario, String nya, long dni, String contra, long nroTarjeta, String fecha,
            boolean admin) {
        this.nomUsuario = nomUsuario;
        this.nya = nya;
        this.dni = dni;
        this.contra = contra;
        this.nroTarjeta = nroTarjeta;
        this.fecha = fecha;
        this.admin = admin;
        try {
            RegistroDeUsuario();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getNomUsuario() {
        return this.nomUsuario;
    }

    public String getnya() {
        return this.nya;
    }

    public long getDNI() {
        return this.dni;
    }

    public String getcontra() {
        return this.contra;
    }

    public long getNroTarjeta() {
        return this.nroTarjeta;
    }

    public String getFecha() {
        return this.fecha;
    }

    // Registro de usuario
    public void RegistroDeUsuario() throws SQLException {
        String insertar;

        if (!this.SesionIniciada) {
            // Aqui se crea una cadena con todos los datos pasados al constructor
            insertar = "";
            insertar += "INSERT INTO Usuarios (nomUsuario, apeUsuario, dni, password, nroTarjeta, fecha) VALUE (";
            insertar += ("\'" + getNomUsuario() + "\',");
            insertar += ("\'" + getnya() + "\',");
            insertar += (getDNI() + ",");
            insertar += ("\'" + getcontra() + "\'" + ",");
            insertar += (getNroTarjeta() + ",");
            insertar += ("\'" + getFecha() + "\')");

            // Con esta sentencia se sube la cadena a la base de datos
            stm.executeUpdate(insertar);

            System.out.println("Se registro el usuario: " + this.nomUsuario);
        } else {
            System.out.println("No puede registrar el usuario porque hay una sesion abierta.");
        }
    }

    // Inicio de Sesion
    public void IniciarSesion(String nombreUsuario, String contra) throws SQLException {
        ResultSet result = stm.executeQuery("select * from Usuarios");
        if (this.SesionIniciada) {
            System.out.println("Hay una sesion iniciada. Cierrela si quiere iniciar sesion con una cuenta nueva");
        } else {
            // Se compara el nombre de usuario y contrasenia de la base de datos con el
            // nombre de usuario y contrasenia pasados por parametro
            while (result.next() && !this.SesionIniciada) {
                if (nombreUsuario.equals(result.getString("nomUsuario")) && contra.equals(result.getString("contra"))) {
                    this.SesionIniciada = true; // Si el nombre de usuario y la contrasenia coinciden, se pone a la
                                                // sesion iniciada en true
                }
            }
            if (this.SesionIniciada) {
                System.out.println("Sesion iniciada.");
            } else {
                System.out.println("Nombre de usuario o contrase�a incorrectos.");
            }
        }

    }

    // Cierre de Sesion
    public void CerrarSesion() {
        this.SesionIniciada = false;
    }

    // Pregunta si el cliente es un administrador para poder realizar las otras
    // operaciones que no puede hacer un cliente
    public boolean EsAdmin() {
        return this.admin;
    }

    public List<Reserva> GetReservas() {
        return this.reservas;
    }

    public void agregarReserva(Reserva reser) throws SQLException {
        try {
            stm.executeUpdate(
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
                stm.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public ArrayList<Reserva> getMisReservas() {
        try {
            Connection con = stm.getConnection();
            String SQL = "SELECT * FROM reservas INNER JOIN usuarios ON reservas.UsuarioId = usuarios.Id WHERE usuarios.dni = "
                    + this.dni + ";";
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
