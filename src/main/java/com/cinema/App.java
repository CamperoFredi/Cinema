package com.cinema;

import com.google.gson.Gson;
import static spark.Spark.*;

public final class App {
    private App() {
    }

    public static void main(String[] args) {

        Gson gsonMapper = new Gson();

        get("/saludar", (request, response) -> {
            response.type("application/json");
            return gsonMapper.toJson("Hola");
        });
    }

}
