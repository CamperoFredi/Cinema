package com.cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionMysql {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_DB = "jdbc:mysql://localhost:3306/cinema";
    private static final String USER = "";
    private static final String PASS = "";

    public static Statement getStatement() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL_DB, USER, PASS);
            return conn.createStatement();
        } catch (SQLException exception) {
            System.out.println(exception.getLocalizedMessage());
            return null;
        }
    }

}
