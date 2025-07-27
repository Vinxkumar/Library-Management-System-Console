package org.example;
import java.util.Scanner;
import java.sql.*;


class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_database";
    private static final String USER = "root";            // Change if your MySQL user is different
    private static final String PASSWORD = "0608";            // Add your MySQL password if set

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Authenticate au;
        try {
            Connection con = DBConnection.getConnection();
            au = new Authenticate(con, sc);
            System.out.println("Connection Successful");
            au.Authorize();
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }

    }
}