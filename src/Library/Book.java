//book class with all methods
package Library;

import java.util.Scanner;

public class Book {
    int id; // Unique identifier for the book
    String name; // Name of the book
    String author; // Author of the book
    String genre; // Genre/category of the book
    Boolean availability; // Availability status of the book

    // Constructor to initialize Book object
    public Book(int id, String name, String author, String genre, Boolean availability) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    // Method to create a table for storing books in the database
    public static void createtable() throws Exception {
        // SQL query to create booktable if it doesn't exist
        String booktable = "create table IF NOT exists booktable (id integer primary key autoincrement,name  varchar(50),author varchar(50),genre varchar(50),availability Boolean)";
        Library.statement.execute(booktable);
    }

    // Method to insert a new book into the database
    public static void insert() throws Exception {
        System.out.println("entered into insert book method");
        System.out.print("put name of book :");
        String name = Library.input.next();
        System.out.print("put author :");
        String author = Library.input.next();
        System.out.print("put genre :");
        String genre = Library.input.next();

        // SQL query to insert book details into the database
        String query = String.format(
                "INSERT INTO BOOKTABLE (name, author, genre, availability) VALUES ('%s', '%s', '%s', %B)", name,
                author, genre, true);
        Library.statement.execute(query);
        // SQL query to create issued_book_details table if it doesn't exist
        String query2 = "create table if not exists issued_book_details (id_user int primary key references usertable(id), id_book int references booktable(id))";
        Library.statement.execute(query2);
        // Add the newly inserted book to the list of books in memory
        Library.books.add(new Book(Library.statement.getGeneratedKeys().getInt(1), name, author, genre, true));
        System.out.println("\n\nNow back again to Main");


    }

    // Method to search for a book by its ID
    public static Book searchbook(int check_id) {
        System.out.println("entered into search book method");

        for (Book book : Library.books) {
            if (book.id == check_id) {
                System.out.println("the book exist ");
                return book;

            }
        }

        return null;
        
    }

    // Method to display all books
    public static void display_book() throws Exception {
        System.out.println("entered into Display book method");

        System.out.println("Displaying all Books ");
        for (Book book : Library.books) {
            System.out.println(book.name);
        }
        System.out.println("\n\nNow back again to Main");

    }

    // Method to issue a book to a user
    public static void issue_book() throws Exception {
        System.out.println("entered into issue book method");

        System.out.print("put the book id you want to borrow: ");
        int num_book = Library.input.nextInt();
        Book book = Book.searchbook(num_book);
        if (book != null) {
            System.out.print("put the user id : ");
            int num_user = Library.input.nextInt();
            // SQL query to insert issued book details into the database
            String Query = String.format("insert into issued_book_details(id_user,id_book) values(%d,%d)", num_user,
                    num_book);
            Library.statement.execute(Query);

        } else {
            System.out.println("Book doesnot exist !!");
        }
        System.out.println("\n\nNow back again to Main");


    }

    // Method to return a book
    public static void return_book() throws Exception {
        System.out.println("entered into return book method");

        System.out.print("what is user id ");
        int num_user = Library.input.nextInt();
        System.out.print("put the book id you want to return: ");
        int num_book = Library.input.nextInt();
        // SQL query to delete issued book details from the database
        String Query = String.format(" delete from issued_book_details where id_user=%d and id_book=%d", num_user,
                num_book);
        Library.statement.execute(Query);
        System.out.println("\n\nNow back again to Main");


    }

}
