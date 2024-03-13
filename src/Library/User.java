package Library;

import java.util.Scanner;

public class User {
    int id;
    String name;
    String contact_info;
    int borrowed_book;

    public User(int id, String name, String coontact_info, int borrowed_book) {
        this.id = id;
        this.name = name;
        this.contact_info = contact_info;
        this.borrowed_book = borrowed_book;
    }

    public static void createtable() throws Exception {
        String user_table = "create table if not exists user_table (id integer primary key autoincrement,name varchar(50),contact_info varchar(50),borrowed_book int)";
        Library.statement.execute(user_table);
    }

    public static void insert() throws Exception {
        System.out.print("put name :");
        String name = Library.input.next();
        System.out.print("put contact Number :");
        String contact_no = Library.input.next();
        System.out.print("put borrowed book :");
        int borrowed = Library.input.nextInt();

        String query = String.format(
                "INSERT INTO user_table (name, contact_info,borrowed_book) VALUES ('%s', '%s', '%d')", name,
                contact_no, borrowed);
        Library.statement.execute(query);
        Library.users.add(new User(Library.statement.getGeneratedKeys().getInt(1), name, contact_no, borrowed));

    }

}
