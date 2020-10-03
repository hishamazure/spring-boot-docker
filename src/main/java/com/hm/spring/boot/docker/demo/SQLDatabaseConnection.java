package com.hm.spring.boot.docker.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
		runnslookup();
		myCommand();
		
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
		String username = "sqladmin@failover-hisham1";//@
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
    
    
    public void myCommand() 
    {
    	
    	System.out.println("----START-------");
    	Process process = null;
             try
             { 
             process = Runtime.getRuntime().exec("pwd"); // for Linux
             //Process process = Runtime.getRuntime().exec("cmd /c dir"); //for Windows

             process.waitFor();
             BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             String line;
                while ((line=reader.readLine())!=null)
                {
                 System.out.println(line);   
                 }
              }       
                 catch(Exception e)
              { 
                  System.out.println(e); 
              }
              finally
              {
                process.destroy();
                System.out.println("----END-------");
              }  
     }
    
    public static void runnslookup() {
		ProcessBuilder processBuilder = new ProcessBuilder();

		// -- Linux --

		// Run a shell command
		processBuilder.command("nslookup failover-hisham1.database.windows.net");



		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success!");
				System.out.println(output);
				System.exit(0);
			} else {
				//abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


