// library class and main class is also defined here
package Library;

import java.util.Scanner;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Library {
    // Declaring class variables for database connection, statement, result sets,
    // and lists to store books and users.
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultset;
    static List<Book> books = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // Establishing database connection and creating statement object.
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:library.db");
        statement = connection.createStatement();
        System.out.println("Hello, World!");

        // Creating tables for books and users if they don't exist.
        Book.createtable();
        User.createtable();

        // Displaying welcome message and loading existing books and users from the
        // database.
        System.out.printf(
                "-----welcome to the library management system -----\n\t choose the number for relevant operation \n");
        Boolean flag = true;

        ResultSet bookresult = statement.executeQuery("select * from booktable");
        while (bookresult.next()) {
            // Populating the books list with data from the database.
            books.add(new Book(bookresult.getInt("id"), bookresult.getString("name"),
                    bookresult.getString("author"), bookresult.getString("genre"),
                    bookresult.getBoolean("availability")));
        }
        ResultSet userresult = statement.executeQuery("select * from user_table");
        while (userresult.next()) {
            // Populating the users list with data from the database.
            users.add(new User(userresult.getInt("id"), userresult.getString("name"),
                    userresult.getString("contact_info"), userresult.getInt("borrowed_book")));
        }

        // Main loop for the library management system.
        while (flag) {
            try {
                // Displaying menu options for different operations.
                System.out.printf(
                        "1->\t insert book\n2->\t insert user\n3->\t Display book\n4->\t search book\n5->\t issue book\n6->\t return book\n7->\t EXIT\n");
                System.out.print("choose option :");
                int choice = input.nextInt();

                switch (choice) {
                    case 1:
                        // Invoking method to insert a new book into the database.
                        Book.insert();
                        break;
                    case 2:
                        // Invoking method to insert a new user into the database.
                        User.insert();
                        break;
                    case 3:
                        // Invoking method to display all books in the library.
                        Book.display_book();
                        break;
                    case 4:
                        // Searching for a book by ID and displaying its details.
                        System.out.print("enter id of book : ");
                        int check_id = Library.input.nextInt();
                        Book bookr = Book.searchbook(check_id);
                        System.out.print("the book is " + bookr);
                        break;
                    case 5:
                        // Invoking method to issue a book to a user.
                        Book.issue_book();
                        break;
                    case 6:
                        // Invoking method to return a book from a user.
                        Book.return_book();
                        break;
                    case 7:
                        // Exiting the program.
                        flag = false;
                        break;
                    default:
                        System.out.println("wrong input !! put no in range of 1 to 7");
                        break;
                }
            } catch (IOException e) {
                // Handling IO Exception.
                System.out.print("Error in code ");
            }
        }
    }
}
