package com.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    private int op;
    private boolean salir = false;
    private static Cliente cliente;
    private static Administrador administrador;
    private static Cine cine;
    private static Reserva reserva;
    private static Sala sala;
    private static Descuentos descuentos;
    private static Pelicula pelicula;

    public Menu() throws SQLException {
        cliente = new Cliente();
        administrador = new Administrador();
        cine = new Cine();
        reserva = new Reserva();
        sala = new Sala();
        descuentos = new Descuentos();
        pelicula = new Pelicula();
    }

    public void MenuGeneral() throws SQLException {

        System.out.println("======= INICIO DEL PROGRAMA =======");
        System.out.println("======= OPCIONES GENERALES =======");
        System.out.println("Presione una tecla para continuar...");
        sc.nextLine();
        cls();

        do {
            try {
                System.out.println("\n======= MENU GENERAL =======\n");
                MostrarOpcionesGenerales();
                System.out.println("\nIngrese una opcion: \n");
                op = sc.nextInt();

                switch (op) {
                    case 1:
                        if (!cliente.SesionIniciada && !administrador.SesionIniciada) {
                            cliente.RegistroDeUsuario();
                        } else {
                            System.out
                                    .println("Hay una sesion iniciada. Cierrela si quiere registrar un nuevo usuario");
                        }
                        break;

                    case 2:
                        if (!cliente.SesionIniciada && !administrador.SesionIniciada) {

                            cliente.IniciarSesion();
                            if (cliente.SesionIniciada) {
                                MenuCliente();
                            }
                        } else {
                            System.out.println(
                                    "Hay una sesion iniciada. Cierrela si quiere iniciar sesion con una cuenta nueva");
                        }
                        break;
                    case 3:
                        if (!cliente.SesionIniciada && !administrador.SesionIniciada) {

                            administrador.IniciarSesion();
                            if (cliente.SesionIniciada) {
                                MenuAdministrador();
                            }
                        } else {
                            System.out.println(
                                    "Hay una sesion iniciada. Cierrela si quiere iniciar sesion con una cuenta nueva");
                        }
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Debe ingresar una opcion correcta.");
                }
            } catch (InputMismatchException e) {
                salir = true;
                cls();
                System.out.println("Debia ingresar un numero.");
                System.out.println("Ejecucion finalizada");
            }
        } while (!salir);
    }

    public void cls() {
        for (int i = 0; i <= 100; i++) {
            System.out.println();
        }
    }

    public void MostrarOpcionesGenerales() {
        System.out.println("\n1. Registrar un usuario.");
        System.out.println("\n2. Iniciar sesion como usuario.");
        System.out.println("\n3. Iniciar sesion como administrador.");
        System.out.println("\n4. Salir");
    }

    public void MenuCliente() {
        cls();
        System.out.println("======= OPCIONES CLIENTE =======");
        MostrarOpcionesCliente();
        System.out.println("\nIngrese una opcion: \n");
        op = sc.nextInt();

        do {
            try {
                switch (op) {
                    case 1:
                        System.out.println("======= SALAS =======");
                        String desc = Descuentos.GetDiaDescuentoActual();
                        System.out.println(desc);

                        System.out.println("Descuento del dia " + desc);
                        System.out.println();
                        System.out.println("Salas disponibles: ");
                        cine.getListSalas();
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
                                System.out.println("Abonar entrada: ");
                                String precio = String.valueOf(System.console().readLine());
                                String descu = descuentos.GetDiaDescuentoActual();
                                String dia = descu.split(" ")[0];
                                int idDescuent = descuentos.getIdDescuento(dia);
                                String diaFun = sala.getHorarioById(idSala);
                                try {
                                    Reserva r = new Reserva(cliente.getId(), idSala, idDescuent, diaFun, "0", precio,
                                            "1");
                                    reserva.agregarReserva(r);
                                } catch (SQLException e) {
                                    System.out.println(e.getLocalizedMessage());
                                }

                                break;

                            case 2:
                                System.out.println("Gracias por visitarnos");
                                salir = true;
                                break;
                            default:
                                System.out.println("Opción inválida");
                                break;
                        }
                        break;

                    case 2:
                        System.out.println("Reservas realizadas: ");
                        ArrayList<String> reservas = reserva.getMisReservas(cliente.getDNI());
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
                                        Reserva r2 = new Reserva(idReserva, idSala2, descc, diaFun2, "1", preciov, "1");
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

                            case 3:
                                cliente.CerrarSesion();
                                salir = true;
                        }
                }
            } catch (InputMismatchException e) {
                salir = true;
                cls();
                System.out.println("Debia ingresar un numero.");
                System.out.println("Pulse enter para volver al menu principal");
                sc.nextLine();
                cliente.CerrarSesion();
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } while (salir);
    }

    public void MostrarOpcionesCliente() {
        System.out.println("\n1. Ver salas disponibles.");
        System.out.println("\n2. Ver mis reservas.");
        System.out.println("\n3. Cerrar Sesion.");
    }

    public void MenuAdministrador() {
        cls();

        MostrarOpcionesCliente();
        System.out.println("\nIngrese una opcion: \n");
        op = sc.nextInt();

        do {
            try {
                switch (op) {
                    case 1:
                        System.out.println("Usted va a agregar una pelicula.");
                        System.out.println("Ingrese el nombre de la pelicula: ");
                        String nombre = System.console().readLine();
                        try {
                            pelicula.CrearPelicula(nombre);
                        } catch (SQLException e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Usted va a agregar una sala.");
                        System.out.println("Ingrese el numero de la sala: ");
                        String salaNro = System.console().readLine();
                        System.out.println("Ingrese el nombre de la sala: ");
                        String nombreSala = System.console().readLine();
                        System.out.println(
                                "Debe agregar el Id de una pelicula, ¿Desea ver las peliculas disponibles? S/N: ");
                        String resp = System.console().readLine();
                        if (resp.toUpperCase().equals("S")) {
                            pelicula.getPeliculas();
                        }
                        System.out.println("Ingrese el id de la pelicula: ");
                        String peliculaId = System.console().readLine();
                        System.out.println("Ingrese el horario de la sala: ");
                        String horario = System.console().readLine();
                        System.out.println("Ingrese la capacidad de la sala: ");
                        int capacidad = Integer.parseInt(System.console().readLine());
                        System.out.println("Ingrese el tipo de la sala: ");
                        String tipo = System.console().readLine();
                        System.out.println("Ingrese el precio de la sala: ");
                        String precio = System.console().readLine();

                        Sala s = new Sala(salaNro, nombreSala, peliculaId, horario, capacidad, tipo, precio);
                        try {
                            String nsala = cine.agregarNuevaSala(s);
                            System.out.println(nsala);
                        } catch (SQLException e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Usted va a eliminar una sala.");
                        System.out
                                .println(
                                        "Debe agregar el Id de la salas a eliminar, ¿Desea ver las salas disponibles? S/N: ");
                        String rsala = System.console().readLine();
                        if (rsala.toUpperCase().equals("S")) {
                            System.out.println("Salas disponibles: ");
                            ArrayList<String> salas = cine.getListSalas();
                            for (String sala : salas) {
                                System.out.println(sala);
                            }
                        }
                        System.out.println("Ingrese el Id de la sala que desea eliminar: ");
                        int salaNro2 = Integer.parseInt(System.console().readLine());
                        String nsala = cine.borrarSala(salaNro2);
                        System.out.println(nsala);
                        break;
                    case 4:
                        System.out.println("Usted va a modificar un descuento.");
                        System.out.println(
                                "Debe agregar el Id del descuento a modificar, ¿Desea ver el listado de descuentos disponibles? S/N: ");
                        String rdesc = System.console().readLine();
                        if (rdesc.toUpperCase().equals("S")) {
                            System.out.println("Descuentos disponibles: ");
                            descuentos.getListDescuentos();
                        }
                        System.out.println("Ingrese el Id del descuento que desea modificar: ");
                        int descId = Integer.parseInt(System.console().readLine());
                        System.out.println("Ingrese el nuevo descuento: ");
                        int desc = Integer.parseInt(System.console().readLine());
                        String ndesc = descuentos.modificarDescuento(descId, desc);
                        System.out.println(ndesc);
                        break;

                    case 5:
                        administrador.CerrarSesion();
                        salir = true;
                }
            } catch (InputMismatchException e) {
                salir = true;
                cls();
                System.out.println("Debia ingresar un numero.");
                System.out.println("Pulse enter para volver al menu principal");
                sc.nextLine();
                administrador.CerrarSesion();
            }
        } while (salir);
    }

    public void MostrarOpcionesAdministrador() {
        System.out.println("======= OPCIONES ADMINISTRADOR =======");
        System.out.println("1. Agregar una pelicula.");
        System.out.println("2. Agregar una sala.");
        System.out.println("3. Eliminar Sala.");
        System.out.println("4. Modificar descuento.");
        System.out.println("5. Cerrar Sesion.");
        System.out.println("Ingrese una opcion: ");

    }
}
