//class Authenticate
package org.example;

import java.sql.*;
import java.util.Scanner;

public class Authenticate {
    Connection con;
    Scanner sc;
    Student std;
    Admin adm;
    private String admin;
    private String apass;
    private String user;
    private String pass;
    Authenticate(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

     private Boolean fetch(int mode) {
        if(mode==2) {
            String selectcmd = "select * from AdminUsers;";
            try {
                PreparedStatement prepstm = con.prepareStatement(selectcmd);
                ResultSet rs = prepstm.executeQuery();
                while (rs.next()) {
                    String tempuid = rs.getString("UID");
                    String temppass = rs.getString("pass");
                    if(tempuid.equals(admin) && temppass.equals(apass)) {
//                        System.out.println("Sucessfull");
                        return true;
                    } else {
                        return  false;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            } 
        } else if (mode ==1) {
            String SUsers = "select * from Users;";
            try {
                PreparedStatement prepstm = con.prepareStatement(SUsers);
                ResultSet rs = prepstm.executeQuery();
                while (rs.next()) {
                    if (user.equals(rs.getString("UID")) && pass.equals(rs.getString("pass"))) {
                        return true;
//                        break;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return false;
    }
    public void Authorize() {
        System.out.println("\t\t\tLibrary Management System\n");
        System.out.print("\t1: Student Login | 2: Admin Login | 0: Exit \n\t");
        int mode = sc.nextInt();

        if(mode == 1) {
            System.out.print("User Name: ");
            user = sc.next();
            System.out.print("Password: ");
            pass = sc.next();
        }
        else if(mode == 2) {
            System.out.print("Admin User Name: ");
            admin = sc.next();
            System.out.print("Password: ");
            apass = sc.next();
        } else if (mode == 0) {
            System.out.println("Exiting");
            System.exit(0);
        }

        try {
            if(fetch(mode)) {
                if(mode==2) {
                    adm = new Admin(con,sc);
                    adm.Interface();
                } else if(mode==1) {
                    std = new Student(con, sc, user, pass);
                    std.InterFace();
                }
            }
            else {
                System.out.println("Invalid Credentials");
                Authorize();
            }
        } catch (Exception e) {
            System.out.println("Error while fetching data: " + e.getMessage());
        }
    }
}
