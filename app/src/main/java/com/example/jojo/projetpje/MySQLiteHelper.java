package com.example.jojo.projetpje;

/**
 * Created by Nadia on 04/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jojo.projetpje.dummy.DummyContent;

import java.util.LinkedList;
import java.util.List;



public class MySQLiteHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "BookDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la base de données
        // on exécute ici les requêtes de création des tables
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "isbn TEXT, " +
                "title TEXT, " +
                "author TEXT )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }


    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_BOOKS = "books";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ISBN = "isbn";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";

    private static final String[] COLUMNS = {
            KEY_ID,
            KEY_ISBN,
            KEY_TITLE,
            KEY_AUTHOR
    };

    public void addBook(DummyContent.Book book) {
        Log.d("addBook", book.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ISBN, book.getIsbn());
        values.put(KEY_TITLE, book.getTitle()); // get title
        values.put(KEY_AUTHOR, book.getAuthor()); // get author

        // 3. insert
        db.insert(TABLE_BOOKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    public DummyContent.Book getBook(String title) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_BOOKS, // a. table
                        COLUMNS, // b. column names
                        " title = ?", // c. selections
                        new String[]{String.valueOf(title)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        DummyContent.Book book = new DummyContent.Book();
        book.setId((cursor.getString(0)));
        book.setIsbn(cursor.getString(1));
        book.setTitle(cursor.getString(2));
        book.setAuthor(cursor.getString(3));

        Log.d("getBook(" + title + ")", book.toString());

        // 5. return book
        return book;
    }

    // Get All Books
    public List<DummyContent.Book> getAllBooks() {
        List<DummyContent.Book> books = new LinkedList<DummyContent.Book>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BOOKS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        DummyContent.Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new DummyContent.Book();
                book.setId((cursor.getString(0)));
                book.setIsbn(cursor.getString(1));
                book.setTitle(cursor.getString(2));
                book.setAuthor(cursor.getString(3));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        // return books
        return books;
    }

    // Deleting single book
    public void deleteBook(DummyContent.Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOOKS,
                KEY_ID + " = ?",
                new String[]{String.valueOf(book.getId())});

        // 3. close
        db.close();

        Log.d("deleteBook", book.toString());

    }
}