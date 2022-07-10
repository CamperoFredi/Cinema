package com.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu 
{
	Scanner sc = new Scanner (System.in);
	private int op;
	private boolean salir = false;
	private static Cliente cliente;
	private static Administrador administrador;
	private static Cine cine;
	private static Reserva reserva;
	private static Sala sala;
	private static Descuentos descuentos;
	private static Pelicula pelicula;
	
	
	
	public Menu () throws SQLException
	{
		cliente = new Cliente ();
		administrador = new Administrador ();
		cine = new Cine ();
		reserva = new Reserva();
		sala = new Sala();
		descuentos = new Descuentos();
		pelicula = new Pelicula();
	}
	
	public void MenuGeneral () throws SQLException
	{

		System.out.println("---INICIO DEL PROGRAMA---");
		System.out.println("---BIENVENIDO AL CINE---");
		System.out.println("Presione una tecla para continuar...");
		sc.nextLine();
		cls ();
		
		do
		{
			try
			{
				System.out.println("\n---MENU GENERAL---\n");
				MostrarOpcionesGenerales ();
				System.out.println("\nIngrese una opcion: \n");
				op = sc.nextInt();
				
				switch (op)
				{
				case 1:
					if(!cliente.SesionIniciada && !administrador.SesionIniciada)
					{
						cliente.RegistroDeUsuario();
					}
					else
					{
						System.out.println("Hay una sesion iniciada. Cierrela si quiere registrar un nuevo usuario");
					}
					break;
					
				case 2:
					if(!cliente.SesionIniciada && !administrador.SesionIniciada)
					{
						
						cliente.IniciarSesion();
						if (cliente.SesionIniciada)
						{
							MenuCliente();
						}
					}
					else
					{
						System.out.println("Hay una sesion iniciada. Cierrela si quiere iniciar sesion con una cuenta nueva");
					}
					break;
				case 3:
					if(!cliente.SesionIniciada && !administrador.SesionIniciada)
					{
						
						administrador.IniciarSesion();
						if (cliente.SesionIniciada)
						{
							MenuAdministrador();
						}
					}
					else
					{
						System.out.println("Hay una sesion iniciada. Cierrela si quiere iniciar sesion con una cuenta nueva");
					}
					break;
				case 4:
					salir = true;
					break;
				default:
					System.out.println("Debe ingresar una opcion correcta.");
				}
			}
			catch (InputMismatchException e)
			{
				salir = true;
				cls ();
				System.out.println("Debia ingresar un numero.");
				System.out.println("Ejecucion finalizada");
			}
		}
		while (!salir);
	}
	
	public void cls ()
	{
		for (int i = 0; i <= 100; i++)
		{
			System.out.println();
		}
	}
	
	public void MostrarOpcionesGenerales()
	{
		System.out.println("\n1. Registrar un usuario.");
		System.out.println("\n2. Iniciar sesion como usuario.");
		System.out.println("\n3. Iniciar sesion como administrador.");
		System.out.println("\n4. Salir");
	}
	
	public void MenuCliente ()
	{
		cls ();
		System.out.println("---MENU DEL USUARIO---");
		MostrarOpcionesCliente ();
		System.out.println("\nIngrese una opcion: \n");
		op = sc.nextInt();
		
		do
		{
			try
			{
				switch (op)
				{
				case 1:
	                System.out.println("======= SALAS =======");
	                String desc = Descuentos.GetDiaDescuentoActual();
	                System.out.println("Descuento del dia " + desc);
	                System.out.println();
	                System.out.println("Salas disponibles: ");
	                ArrayList<Sala> salas = cine.getListSalas();
	                for (Sala sala : salas) {
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
			catch (InputMismatchException e)
			{
				salir = true;
				cls ();
				System.out.println("Debia ingresar un numero.");
				System.out.println("Pulse enter para volver al menu principal");
				sc.nextLine();
				cliente.CerrarSesion();
			}
		}
		while (salir);
	}
	
	public void MostrarOpcionesCliente ()
	{
		System.out.println("\n1. Ver salas disponibles.");
        System.out.println("\n2. Ver mis reservas.");
		System.out.println("\n3. Cerrar Sesion.");
	}
	
	public void MenuAdministrador ()
	{
		cls ();
		System.out.println("---MENU DEL USUARIO---");
		MostrarOpcionesCliente ();
		System.out.println("\nIngrese una opcion: \n");
		op = sc.nextInt();
		
		do
		{
			try
			{
				switch (op)
				{
	            case 1:
	                System.out.println("Usted va a agregar una pelicula.");
	                System.out.println("Ingrese el nombre de la pelicula: ");
	                String nombre = System.console().readLine();

	                System.out.println("Ingrese el precio de la pelicula: ");
	                String precio = System.console().readLine();

	                try {
	                    pelicula.CrearPelicula(nombre);
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
				
				
				case 3:
					administrador.CerrarSesion();
					salir = true;
				}
			}
			catch (InputMismatchException e)
			{
				salir = true;
				cls ();
				System.out.println("Debia ingresar un numero.");
				System.out.println("Pulse enter para volver al menu principal");
				sc.nextLine();
				administrador.CerrarSesion();
			}
		}
		while (salir);
	}
	
	public void MostrarOpcionesAdministrador ()
	{
		System.out.println("\n1. Crear Pelicula.");
		//System.out.println("\n2. Iniciar sesion como usuario.");
		//System.out.println("\n3. Iniciar sesion como administrador.");
		System.out.println("\n4. Cerrar Sesion.");
	}
}
