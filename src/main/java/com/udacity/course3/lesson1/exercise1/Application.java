package com.udacity.course3.lesson1.exercise1;


import org.flywaydb.core.Flyway;

import java.sql.*;

public class Application {

    public static void main(String[] args) {
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDND_C3?user=root&password=Summer2019"))
            {
                System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());
            } catch (SQLException e) {
                System.out.println("SqlException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
            ;


            // STEP 2: Setup and Run Flyway migration that creates the member table using its Java API
            // https://flywaydb.org/getstarted/firststeps/api#integrating-flyway
            // Note the above link talks about connecting to H2 database, for this exercise, MySQL is used. Adapt the code accordingly.
            Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost:3306/JDND_C3?", "root", "Summer2019").load();

            // Start the migration
            flyway.migrate();


            // STEP 3: Obtain a connection to the JDND-C3 database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDND_C3?user=root&password=Summer2019");
            // STEP 4: Use Statement to INSERT 2 records into the member table


            String query = " insert into member (first_name, last_name, age, gender, balance)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, "Barney");
            preparedStmt.setString (2, "Rubble");
            preparedStmt.setInt   (3, 39);
            preparedStmt.setString(4, "F");
            preparedStmt.setDouble   (5, 50.00);

            // execute the preparedstatement
            preparedStmt.execute();


            // NOTE: The member table is created using Flyway by placing the migration file in src/main/resources/db/migration
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * from member");
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String first_name = rs.getString(2);
                    String last_name = rs.getString(3);
                    int age = rs.getInt(4);
                    String gender = rs.getString(5);
                    double balance = rs.getDouble(6);
                    System.out.println(id + "\t" + first_name +
                            "\t" + last_name + "\t" + age +
                            "\t" + gender + "\t" + balance );
                }

            }

            // STEP 5: Read ALL the rows from the member table and print them here


            // STEP 6: verify that all inserted rows have been printed
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
