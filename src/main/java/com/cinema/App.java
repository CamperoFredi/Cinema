package com.cinema;

import java.sql.SQLException;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws SQLException
    {	
    	Menu menu = new Menu ();
    	
    	menu.MenuGeneral();
    	
    }
}