package com.cinema;

import com.google.gson.Gson;
import static spark.Spark.*;
import java.sql.SQLException;

public final class App {
    private static Cine cine = new Cine();

    public static void main(String[] args) {
        Gson gson = new Gson();
        port(8081);

        // CREA UNA NUEVA SALA, TODO AGREGAR CONTROL DE ADMINISTRADOR
        post("/crearSala", "application/json", (request, response) -> {
            if (Boolean.TRUE
                    .equals(cine.isAdmin(gson.fromJson(request.headers("Authorization"), Usuario.class)))) {
                try {
                    Sala nuevaSala = gson.fromJson(request.body(), Sala.class);
                    cine.agregarNuevaSala(nuevaSala);
                    System.out.println("La sala: " + nuevaSala.getNombreSala() + "se ha creado correctamente.");
                    return "La sala " + nuevaSala.getNombreSala() + " se ha creado correctamente.";
                } catch (SQLException e) {
                    response.status(400);
                    return e.getLocalizedMessage();
                }
            } else {
                return "Usted no posee los permisos necesarios para crear una nueva sala.";
            }
        });

        put("/modificarSala", "application/json", (request, response) -> {
            if (Boolean.TRUE
                    .equals(cine.isAdmin(gson.fromJson(request.headers("Authorization"), Usuario.class)))) {
                try {
                    Sala mSala = gson.fromJson(request.body(), Sala.class);
                    cine.modificarSala(mSala);
                    response.type("application/json");
                    System.out.println("La sala " + mSala.getNombreSala() + " se ha modificado correctamente.");
                    return "La sala " + mSala.getNombreSala() + " se ha modificado correctamente.";
                } catch (Exception e) {
                    response.status(400);
                    return e.getLocalizedMessage();
                }
            } else {
                return "Usted no posee los permisos necesarios para realizar modificaciones en la Sala.";
            }
        });

    }

}
