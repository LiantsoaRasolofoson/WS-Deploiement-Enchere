package com.example.enchere.connexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionPostgreSQL {
    
    Connection Connect;

    public ConnexionPostgreSQL(){}

    public Connection getConnect(){
        try{
            Class.forName("org.postgresql.Driver");
            this.Connect = DriverManager.getConnection("jdbc:postgresql://containers-us-west-152.railway.app:7462/railway", "postgres", "ImxFaShp0EZQG6NcnRTB");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return this.Connect;
    }
}
