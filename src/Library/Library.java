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
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultset;
    static List<Book> books = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:library.db");
        statement = connection.createStatement();
        System.out.println("Hello, World!");
        Book.createtable();
        User.createtable();
        System.out.printf(
                "-----welcome to the library management system -----\n\t choose the number for relevant operation \n");
        Boolean flag = true;

        ResultSet bookresult = statement.executeQuery("select * from booktable");
        while (bookresult.next()) {
            System.out.println(bookresult.getInt("id"));
            books.add(new Book(bookresult.getInt("id"), bookresult.getString("name"), bookresult.getString("author"),
                    bookresult.getString("genre"), bookresult.getBoolean("availability")));
        }
        ResultSet userresult = statement.executeQuery("select * from user_table");
        while (userresult.next()) {
            users.add(new User(userresult.getInt("id"), userresult.getString("name"),
                    userresult.getString("contact_info"),
                    userresult.getInt("borrowed_book")));
        }

        while (flag) {
            try {
                System.out.printf(
                        "1->\t insert book\n2->\t insert user\n3->\t Display book\n4->\t search book\n5->\t issue book\n6->\t return book\n7->\t EXIT\n");
                System.out.print("choose option :");
                int choice = input.nextInt();

                switch (choice) {
                    case 1:
                        Book.insert();
                        break;
                    case 2:
                        User.insert();
                        break;
                    case 3:
                        Book.display_book();
                        break;
                    case 4:
                        System.out.print("enter id of book : ");
                        int check_id = Library.input.nextInt();
                        Book bookr = Book.searchbook(check_id);
                        System.out.print("the book is " + bookr);
                        break;
                    case 5:
                        Book.issue_book();
                        break;
                    case 6:
                        Book.return_book();
                        break;
                    case 7:
                        flag = false;
                        break;

                }
            } catch (IOException e) {
                System.out.print("Error in code ");

            }

        }

    }
}
