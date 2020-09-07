package com.hm.spring.boot.docker.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabaseConnection {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public String insertReord() {

        
        String connectionUrl =
        "jdbc:sqlserver://sql-hisham.database.windows.net:1433;"
        + "database=db-hm;"
        + "user=sqladmin@sql-hisham2;password=hisham@1234;"
        + "encrypt=true;"
        + "trustServerCertificate=false;"
        + "hostNameInCertificate=*.database.windows.net;"
        + "loginTimeout=30;";
        
        String sql = "insert into Persons (" + 
        "    LastName ," + 
        "    FirstName ," + 
        "    Address ," + 
        "    City )" + 
        "values ('DD', 'BB','AA', 'Doha')";
        
        
 

        ResultSet resultSet = null;
        String insertedId = "";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement prepsInsertProduct = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            prepsInsertProduct.execute();
            // Retrieve the generated key from the insert.
            resultSet = prepsInsertProduct.getGeneratedKeys();

            // Print the ID of the inserted row.
            
            while (resultSet.next()) {
            	insertedId = resultSet.getString(1);
                System.out.println("Generated: " + insertedId );
            }
            return insertedId;
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
            
        }
        
       
    }
}


