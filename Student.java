package org.example;

import com.sun.security.jgss.GSSUtil;

import java.sql.*;
import java.util.Scanner;

//import org.example.Authenticate;a
public class Student extends Authenticate {
    Connection con;
    Scanner sc;
    CurdOperation obj;
    private String user;
    private String pass;
    Student(Connection con, Scanner sc, String User, String pass) {
        super(con, sc);
        this.con = con;
        this.sc = sc;
        this.user = User;
        this.pass = pass;
        this.obj = new CurdOperation(con, sc);
    }

    public void InterFace() {
        System.out.println("\n\t        Welcome");
        System.out.println("\t 1. Books Availed     |  2. List Available Books");
        System.out.println("\t 3. Avail More Books  |  4. Return Books");
        System.out.println("\n\t 0. Log Out");
        int choice = sc.nextInt();
        if(choice ==1) {
            obj.AvailedBooks();
        } else if(choice==2) {
            obj.ListAvailable();
        } else if(choice==3) {
            obj.Avail() ;
        }else if(choice==4){
            obj.ReturnBooks();
        }else if(choice==0) {
            System.out.println("Logging Out");
            Authorize();
        } else {
            System.out.println("No Operation Available for " + choice);
            InterFace();
        }
    }

    class CurdOperation {
        Connection con;
        Scanner sc;
        CurdOperation(Connection con, Scanner sc) {
            this.con = con;
            this.sc = sc;
        }
        private void AvailedBooks() {
            try {
                String selectcmd = "select id, title from books where id in( select bookid from lendedbooks where UID = ?); ";
                PreparedStatement prep = con.prepareStatement(selectcmd);
                prep.setString(1,user);
                System.out.println("\tBook Id \t Book Name");
                ResultSet rs = prep.executeQuery();
                while(rs.next()) {
                    System.out.println("\t" + rs.getString("id") +"\t" + rs.getString("title"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            InterFace();
        }
        private void ListAvailable() {
            String selectcmd = "SELECT * FROM books WHERE id NOT IN (SELECT DISTINCT bookid FROM lendedbooks where UID = ?)";
            try {
                PreparedStatement prep = con.prepareStatement(selectcmd);
                prep.setString(1, user);
                ResultSet rs = prep.executeQuery();
                System.out.println("\n\t\tId\t\tTitle\t\tAuthor");
                while(rs.next()) {
                    String Id = rs.getString("Id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    System.out.println("\t\t" + Id +"\t"+ title +"\t"+author);
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            InterFace();
        }
        private void Avail() {
            String insertcmd = "insert into lendedbooks(UID, bookid) values (?, ?)";
            System.out.println("Enter the Book Id: ");
            String id = sc.next();
            try {
                PreparedStatement prepstm = con.prepareStatement(insertcmd);
                prepstm.setString(1, user);
                prepstm.setString(2, id);
                prepstm.executeUpdate();
                System.out.println("Book with Id(" + id +") Availed Successfully");
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
            InterFace();
        }
        private void ReturnBooks() {
            String removecmd = "delete from lendedbooks where UID = ? and bookid = ?";
            System.out.println("Enter the Book Id: ");
            String id = sc.next();
            try {
                PreparedStatement prepstm = con.prepareStatement(removecmd);
                prepstm.setString(1, user);
                prepstm.setString(2, id);
                prepstm.executeUpdate();
                System.out.println("Book with Id(" + id +") Returned Successfully");
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
            InterFace();
        }
    }

}
