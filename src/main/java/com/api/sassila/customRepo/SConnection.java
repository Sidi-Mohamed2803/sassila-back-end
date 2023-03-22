package com.api.sassila.customRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SConnection {
    @Autowired
    private Environment env;

    private static String url = "jdbc:mysql://localhost:3306/genealogy";
//    @Value("${spring.datasource.username}")
    private static String user = "root";
//    @Value("${spring.datasource.password}")
    private static String mdp = "";
    private static Connection cnx = null;

    public static Connection getInstance() {
        try {
            cnx = DriverManager.getConnection(url, user, mdp);
        } catch (SQLException e) {
            System.out.println("Problème rencontré lors de la connexion...\n");
            e.printStackTrace();
        }
        return cnx;
    }

    public static void close() {
        try {
            cnx.close();
        } catch (SQLException e) {
            System.out.println("Problème rencontré lors de la fermeture de la connexion...\n");
            e.printStackTrace();
        }
    }
}
