//class Admin

package org.example;
import java.util.Scanner;
import java.sql.*;

public class Admin extends Authenticate {
    Connection con;
    Scanner sc;
    CurdOperations obj;
    Admin(Connection con, Scanner sc) {
        super(con, sc);
        this.con = con;
        this.sc = sc;
        this.obj = new CurdOperations(con, sc);
    }

    public void Interface() {
        System.out.println("\n\n\n");
        System.out.println("\t                          Welcome\n ");
        System.out.println("\t Student Management          |    Inventory Management");
        System.out.println("\t 1. Add Student              |     5. Add New Books");
        System.out.println("\t 2. Update Student Password  |     6. Remove Books");
        System.out.println("\t 3. List Student             |     7. List Books");
        System.out.println("\t 4. Remove Student           |     8. List Lent Books");
        System.out.println("\n\t 0. Log out");
        int choice = sc.nextInt();
        switch(choice) {
            case 1 : {
                obj.AddStudent();
                break;
            }
            case 2 : {
                obj.UpdatePassword();
                break;
            }
            case 3: {
                obj.ListStudent();
                break;
            }
            case 4 : {
                obj.RemoveStudent();
                break;
            }
            case 5  : {
                obj.AddBooks();
                break;
            }
            case 6 :{
                obj.RemoveBooks();
                break;
            }
            case 7: {
                obj.ListBooks();
                break;
            }
            case 8: {
                obj.Lent();
                break;
            }
            case 0 : {
                System.out.println("Logged Out");
                Authorize();
                break;
            }
            default :{
                System.out.println("No Operations Available for this Choice");
                break;
            }
        }
    }
    class CurdOperations{
//        int operations;
        Connection con;
        Scanner sc;
        CurdOperations(Connection con, Scanner sc) {
            this.con = con;
            this.sc = sc;
//            this.operations = operations;
        }

        private void AddStudent() {
            String StName;
            String StId;
            String Stpass;
            System.out.print("\tEnter Student Name:\n\t");
            StName = sc.next();
            System.out.println("\tEnter Id: ");
            System.out.println("\t");
            StId = sc.next();
            System.out.println("\tEnter password: ");
            Stpass = sc.next();
            try {
                PreparedStatement prep = con.prepareStatement("select UID from Users where Name = ?");
                prep.setString(1, StName);
                ResultSet rs = prep.executeQuery();
                if(!rs.next()) {
                    PreparedStatement ins = con.prepareStatement("Insert into Users (UID, pass, Name) values(?, ?, ?)");
                    ins.setString(1, StId);
                    ins.setString(2, Stpass);
                    ins.setString(3, StName);
                    ins.executeUpdate();
                    System.out.println("Student" + StName + " Added Sucessfully");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Interface();
        }
        private void UpdatePassword() {
//            String StName;
            String StId;
            String Newpass;
            System.out.println("\tEnter Id: ");
            StId = sc.next();
            System.out.println("\tEnter password: ");
            Newpass = sc.next();
            try {
                PreparedStatement prep = con.prepareStatement("select UID from Users where UID = ?");
                prep.setString(1, StId);
                ResultSet rs = prep.executeQuery();
                if(rs.next()) {
                    PreparedStatement ins = con.prepareStatement("update Users set pass = ? where UID = ?");
                    ins.setString(1, Newpass);
                    ins.setString(2, StId);
//                    ins.setString(3, StName);
                    ins.executeUpdate();
                    System.out.println("Student with Id " + StId + " Password Updated Successfully");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Interface();
        }
        private void ListStudent() {
            String selectcmd = "select UID, Name from Users";
            try {
                PreparedStatement prep = con.prepareStatement(selectcmd);
                ResultSet rs = prep.executeQuery();
                System.out.println("\n\t\tUserId\t\tName\t\t");
                while(rs.next()) {
                    String Id = rs.getString("UID");
                    String Name = rs.getString("Name");
//                    String author = rs.getString("author");
                    System.out.println("\t\t" + Id +"\t"+ Name);
                }
            }
            catch (SQLException e) {
                throw  new RuntimeException(e);
            }
            Interface();
        }
        private void RemoveStudent() {
            String Id;
            System.out.println("\tEnter Id: ");
            Id = sc.next();
            try {
                PreparedStatement prep = con.prepareStatement("select UID from Users where UID = ?");
                prep.setString(1,Id);
                ResultSet rs = prep.executeQuery();
                if(rs.next()) {
                    PreparedStatement ins = con.prepareStatement("delete from Users where UID = ?");
                    ins.setString(1, Id);
                    ins.executeUpdate();
                    PreparedStatement inst = con.prepareStatement("delete from lendedbooks where UID = ?");
                    inst.setString(1, Id);
                    ins.executeUpdate();
                    System.out.println("Student with Id " +Id + " Removed Successfully");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Interface();
        }

        private void AddBooks() {
            String Title;
            String Id;
            String Author;
            System.out.print("\tEnter Author Name:");
            Author = sc.next();
            System.out.println("\tEnter Id: ");
            Id = sc.next();
            System.out.println("\tEnter Title: ");
            Title = sc.nextLine();
            try {
                PreparedStatement prep = con.prepareStatement("select Id from books where Title = ?");
                prep.setString(1,Id);
                ResultSet rs = prep.executeQuery();
                if(!rs.next()) {
                    PreparedStatement ins = con.prepareStatement("Insert into books (Id, title, author) values(?, ?, ?)");
                    ins.setString(1, Id);
                    ins.setString(2, Title);
                    ins.setString(3, Author);
                    ins.executeUpdate();
                    System.out.println("Book " + Title + " Added Successfully");
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Interface();
        }
        private void RemoveBooks() {
//            String Title;
            String Id;
            System.out.println("\tEnter Id: ");
            Id = sc.next();
            try {
                PreparedStatement prep = con.prepareStatement("select Id from books where Id = ?");
                prep.setString(1,Id);
                ResultSet rs = prep.executeQuery();
                if(rs.next()) {
                    PreparedStatement ins = con.prepareStatement("delete from books where id = ?");
                    ins.setString(1, Id);
                    ins.executeUpdate();
                    System.out.println("Book with Id " +Id + " Removed Successfully");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Interface();
        }
        private void ListBooks() {
            String selectcmd = "select * from books";
            try {
                PreparedStatement prep = con.prepareStatement(selectcmd);
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
            Interface();
        }
        private void Lent() {
            String selectcmd = "select * from lendedbooks";
            try {
                PreparedStatement prep = con.prepareStatement(selectcmd);
                ResultSet rs = prep.executeQuery();
                System.out.println("\n\t\tuserId\t\tBookId\t\tBookName");
                while(rs.next()) {
                    String Id = rs.getString("UID");
//                    String Name = rs.getString("SName");
                    String BookId= rs.getString("bookid");
                    String BookName= rs.getString("bName");
                    System.out.println("\t\t" + Id +"\t"+ BookId +"\t"+BookName);
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Interface();
        }
    }
}