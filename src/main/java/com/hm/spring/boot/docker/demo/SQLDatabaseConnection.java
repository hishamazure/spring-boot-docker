package com.hm.spring.boot.docker.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabaseConnection {
	
	

		
		/*
		final String FAILOVER_GROUP_NAME = "<your failover group name>";  // add failover group name

		final String DB_NAME = "<your database>";  // add database name
		final String USER = "<your admin>";  // add database user
		final String PASSWORD = "<your password>";  // add database password
		*/
		
	public static final String FAILOVER_GROUP_NAME = "failover-hisham1";  // add failover group name

	public static final String DB_NAME = "db-hm";  // add database name
	public static final String USER = "sqladmin@sql-hisham2";  // add database user
	public static final String PASSWORD = "hisham@1234";  // add database password

	public static final String READ_WRITE_URL = String.format("jdbc:" +
		      "sqlserver://%s.database.windows.net:1433;database=%s;user=%s;password=%s;encrypt=true;" +
		      "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", FAILOVER_GROUP_NAME, DB_NAME, USER, PASSWORD);
		   
	public static final String READ_ONLY_URL = String.format("jdbc:" +
		      "sqlserver://%s.secondary.database.windows.net:1433;database=%s;user=%s;password=%s;encrypt=true;" +
		      "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", FAILOVER_GROUP_NAME, DB_NAME, USER, PASSWORD);
	
	
	
	public String getDBConnectionString() {
		
		/**
		 * The direct connection
		 */
		String serverNameDirect = "sql-hisham2.database.windows.net";
		
		/**
		 * The connection by READ-WRITE Listeners
		 * read-write listener <failover-group-name>.database.windows.net
		 */
		String serverNameFailover = "failover-hisham1.database.windows.net";
		
		
		String dbName = "database-db1";
		String username = "sqladmin@sqlserver-hisham1";//@
		String password = "hisham@1234";
		
		
        String connectionUrl =
        "jdbc:sqlserver://"+ serverNameFailover +":1433;"
        + "database="+dbName+";"
        + "user="+username+";password="+password+";"
        + "encrypt=true;"
        + "trustServerCertificate=false;"
        + "hostNameInCertificate=*.database.windows.net;"
        + "loginTimeout=30;";
        
        return connectionUrl;
		
		
	}

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public String insertReord() {

        
        String connectionUrl = getDBConnectionString();
        
        
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


