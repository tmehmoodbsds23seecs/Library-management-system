//user class 
package Library;

import java.util.Scanner;

public class User {
    int id; // Unique identifier for the user
    String name; // Name of the user
    String contact_info; // Contact information of the user
    int borrowed_book; // Number of books borrowed by the user

    // Constructor to initialize User object
    public User(int id, String name, String contact_info, int borrowed_book) {
        this.id = id;
        this.name = name;
        this.contact_info = contact_info;
        this.borrowed_book = borrowed_book;
    }

    // Method to create a table for storing user information in the database
    public static void createtable() throws Exception {
        // SQL query to create user_table if it doesn't exist
        String user_table = "create table if not exists user_table (id integer primary key autoincrement,name varchar(50),contact_info varchar(50),borrowed_book int)";
        Library.statement.execute(user_table);
    }

    // Method to insert a new user into the database
    public static void insert() throws Exception {
        System.out.println("Entered into method for input of user");

        System.out.print("put name :");
        String name = Library.input.next();
        System.out.print("put contact Number :");
        String contact_no = Library.input.next();
        System.out.print("put borrowed book :");
        int borrowed = Library.input.nextInt();

        // SQL query to insert user details into the database
        String query = String.format(
                "INSERT INTO user_table (name, contact_info,borrowed_book) VALUES ('%s', '%s', '%d')", name,
                contact_no, borrowed);
        Library.statement.execute(query);
        // Add the newly inserted user to the list of users in memory
        Library.users.add(new User(Library.statement.getGeneratedKeys().getInt(1), name, contact_no, borrowed));
        System.out.println("\n\nNow back again to Main");


    }

}
