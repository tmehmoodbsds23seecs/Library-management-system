package Library;

import java.util.Scanner;

public class Book {
    int id;
    String name;
    String author;
    String genre;
    Boolean availability;

    public Book(int id, String name, String author, String genre, Boolean availability) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    // Book bok=new Book();
    public static void createtable() throws Exception {
        String booktable = "create table IF NOT exists booktable (id integer primary key autoincrement,name  varchar(50),author varchar(50),genre varchar(50),availability Boolean)";
        Library.statement.execute(booktable);
    }

    public static void insert() throws Exception {

        // System.out.print("put id :");
        // int id = Library.input.nextInt();
        System.out.print("put name :");
        String name = Library.input.next();
        System.out.print("put author :");
        String author = Library.input.next();
        System.out.print("put genre :");
        String genre = Library.input.next();

        String query = String.format(
                "INSERT INTO BOOKTABLE (name, author, genre, availability) VALUES ('%s', '%s', '%s', %B)", name,
                author, genre, true);
        Library.statement.execute(query);
        String query2 = "create table if not exists issued_book_details (id_user int primary key references usertable(id), id_book int references booktable(id))";
        Library.statement.execute(query2);
        Library.books.add(new Book(Library.statement.getGeneratedKeys().getInt(1), name, author, genre, true));

    }

    public static Book searchbook(int check_id) {
        for (Book book : Library.books) {
            if (book.id == check_id) {
                System.out.println("the book exist ");
                return book;

            }
        }
        return null;
    }

    public static void display_book() throws Exception {
        System.out.println("Displaying all Books ");
        for (Book book : Library.books) {
            System.out.println(book.name);
        }
    }

    public static void issue_book() throws Exception {
        System.out.print("put the book id you want to borrow: ");
        int num_book = Library.input.nextInt();
        Book book = Book.searchbook(num_book);
        if (book != null) {
            System.out.print("put the user id : ");
            int num_user = Library.input.nextInt();
            String Query = String.format("insert into issued_book_details(id_user,id_book) values(%d,%d)", num_user,
                    num_book);
            Library.statement.execute(Query);

        } else {
            System.out.println("Book doesnot exist !!");
        }

    }

    public static void return_book() throws Exception {
        System.out.print("what is user id ");
        int num_user = Library.input.nextInt();
        System.out.print("put the book id you want to return: ");
        int num_book = Library.input.nextInt();
        String Query = String.format(" delete from issued_book_details where id_user=%d and id_book=%d", num_user,
                num_book);
        Library.statement.execute(Query);

    }

}
