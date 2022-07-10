package com.cinema;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static spark.Spark.*;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public final class App {
    private static Cine cine = new Cine();
    private static Cliente cliente = new Cliente();
    private static Usuario usuario = new Usuario();
    private static Reserva reserva = new Reserva();
    private static Sala sala = new Sala();
    private static Descuentos descuentos = new Descuentos();
    private static Pelicula pelicula = new Pelicula();

    public static void main(String[] args) throws ParseException, SQLException {
        Gson gson = new Gson();
        port(8081);

        // System.out.println("====== BIENVENIDO A CINE ======");
        // System.out.println("1. Iniciar sesión");
        // System.out.println("2. Crear una cuenta");
        // System.out.println("3. Salir");
        // int opcion = Integer.parseInt(System.console().readLine());

        // switch (opcion) {
        // case 1:
        // System.out.println("Ingrese su nombre de usuario: ");
        // String usuario = System.console().readLine();
        // System.out.println("Ingrese su contraseña: ");
        // String contraseña = System.console().readLine();
        // if (usuario.IniciarSesion(usuario, contraseña)) {
        // System.out.println("Bienvenido " + usuario);
        // } else {
        // System.out.println("Usuario o contraseña incorrectos");
        // }
        // break;
        // case 2:
        // System.out.println("Ingrese nombre de usuario: ");
        // String usuario2 = System.console().readLine();
        // System.out.println("Ingrese contraseña: ");
        // String contraseña2 = System.console().readLine();
        // if (cliente.RegistroDeUsuario()) {
        // System.out.println("Cuenta creada correctamente");
        // } else {
        // System.out.println("El usuario ya existe");
        // }
        // break;
        // case 3:
        // System.out.println("Gracias por visitarnos");
        // break;
        // default:
        // System.out.println("Opción inválida");
        // break;
        // }

        System.out.println("======= OPCIONES =======");
        System.out.println("1. Ver salas disponibles.");
        System.out.println("2. Ver mis reservas.");
        System.out.println("3. Salir");
        System.out.println();
        System.out.println("Ingrese una opcion: ");
        int opcion2 = Integer.parseInt(System.console().readLine());

        switch (opcion2) {
            case 1:
                System.out.println("======= SALAS =======");
                String desc = Descuentos.GetDiaDescuentoActual();
                System.out.println("Descuento del dia " + desc);
                System.out.println();
                System.out.println("Salas disponibles: ");
                ArrayList<String> salas = cine.getListSalas();
                for (String sala : salas) {
                    System.out.println(sala);
                }
                System.out.println();

                System.out.println("====== OPCIONES ======");
                System.out.println("1. Realizar una reserva.");
                System.out.println("2. Salir");

                int opcion3 = Integer.parseInt(System.console().readLine());

                switch (opcion3) {
                    case 1:
                        System.out.println("Usted va a realizar una reserva.");
                        System.out.println("Coloque el numero de la sala que desea reservar: ");
                        int idSala = Integer.parseInt(System.console().readLine());
                        String descu = descuentos.GetDiaDescuentoActual();
                        String dia = descu.split(" ")[0];
                        int idDescuent = descuentos.getIdDescuento(dia);
                        String diaFun = sala.getHorarioById(idSala);

                        System.out.println("Abonar entrada: ");
                        String precio = String.valueOf(System.console().readLine());

                        Reserva r = new Reserva(1, idSala, idDescuent, diaFun, "0", precio, "1");

                        try {
                            reserva.agregarReserva(r);
                        } catch (SQLException e) {
                            System.out.println(e.getLocalizedMessage());
                        }

                        break;

                    case 2:
                        System.out.println("Gracias por visitarnos");
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;
                }
                break;

            case 2:
                System.out.println("Reservas realizadas: ");
                ArrayList<String> reservas = reserva.getMisReservas(usuario.dni);
                for (String r : reservas) {
                    System.out.println(r);
                }
                System.out.println();
                System.out.println("====== OPCIONES ======");
                System.out.println("1. Modificar una reserva.");
                System.out.println("2. Salir");

                int opcion4 = Integer.parseInt(System.console().readLine());

                switch (opcion4) {
                    case 1:
                        System.out.println("Usted va a modificar una reserva.");
                        System.out.println("Ingrese su DNI: ");
                        String dni = System.console().readLine();
                        System.out.println("Coloque el numero de la reserva que desea modificar: ");
                        int idReserva = Integer.parseInt(System.console().readLine());
                        try {
                            ResultSet re = reserva.getReserva(dni, idReserva);
                            System.out.println("Usted esta por modificar la reserva del dia "
                                    + re.getString("DiaFuncion") + ", en la sala: " +
                                    re.getString("NombreSala"));
                            int descc = re.getInt("DescuentoId");
                            String preciov = re.getString("Precio");
                            System.out.println("Desea continuar S/N: ");
                            String resp = System.console().readLine();
                            if (resp.toUpperCase().equals("S")) {
                                System.out.println("Ingrese el nuevo numero de la sala: ");
                                int idSala2 = Integer.parseInt(System.console().readLine());
                                System.out.println("Ingrese el nuevo dia de funcion: ");
                                String diaFun2 = System.console().readLine();
                                Reserva r2 = new Reserva(idReserva, idSala2, descc, diaFun2, "1", preciov,
                                        "1");
                                String response = reserva.modificarReservas(r2);
                                System.out.println(response);
                            }

                        } catch (SQLException e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Gracias por visitarnos");
                        break;
                    default:
                        System.out.println("Opción inválida");
                        break;

                }

                break;
            case 3:
                System.out.println("Gracias por visitarnos");
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }

        System.out.println("======= OPCIONES ADMINISTRADOR =======");
        System.out.println("1. Agregar una pelicula.");
        System.out.println("2. Agregar una sala.");
        System.out.println("3. Agregar un descuento.");
        System.out.println("4. Salir");
        System.out.println("Ingrese una opcion: ");
        int opcion = Integer.parseInt(System.console().readLine());

        switch (opcion) {
            case 1:
                System.out.println("Usted va a agregar una pelicula.");
                System.out.println("Ingrese el nombre de la pelicula: ");
                String nombre = System.console().readLine();

                System.out.println("Ingrese el precio de la pelicula: ");
                String precio = System.console().readLine();

                boolean isAdmin = true;

                try {
                    pelicula.CrearPelicula(nombre, isAdmin);
                } catch (SQLException e) {
                    System.out.println(e.getLocalizedMessage());
                }
                break;
            case 2:
                System.out.println("Usted va a agregar una sala.");
                System.out.println("Ingrese el nombre de la sala: ");
                String nombreS = System.console().readLine();
                System.out.println("Ingrese el numero de la sala: ");
                int numero = Integer.parseInt(System.console().readLine());
                System.out.println("Ingrese el numero de asientos: ");
                int asientos = Integer.parseInt(System.console().readLine());

                pelicula.getPelicula();
                break;
            // case 3:
            // System.out.println("Usted va a agregar una funcion.");
            // System.out.println("Ingrese el nombre de la sala: ");
            // String nombre2 = System.console().readLine();
            // System.out.println("Ingrese el numero de la sala: ");
            // int numero2 = Integer.parseInt(System.console().readLine());
            // System.out.println("Ingrese el numero de asientos: ");
            // int asientos2 = Integer.parseInt(System.console().readLine());
            // Sala sala2 = new Sala(nombre2, numero2, asientos2);
            // try {
            // cine.agregarSala(sala2);
            // } catch (SQLException e) {
            // System.out.println(e.getLocalizedMessage());
            // }
            // break;
            // case 4:
            // System.out.println("Usted va a agregar un descuento.");
            // System.out.println("Ingrese el dia: ");
            // String dia = System.console().readLine();
            // System.out.println("Ingrese el porcentaje: ");
            // int porcentaje = Integer.parseInt(System.console().readLine());
            // Descuento descuento = new Descuento(dia, porcentaje);
            // try {
            // cine.agregarDescuento(descuento);
            // } catch (SQLException e) {
            // System.out.println(e.getLocalizedMessage());
            // }
            // break;

            case 5:
                System.out.println("Gracias por visitarnos");
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }

    }
}
