package com.pabloojeda.bdlibros.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.pabloojeda.bdlibros.model.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBSQLiteHelper extends SQLiteOpenHelper{
	// Logcat tag
	private static final String LOG = "DatabaseHelper";
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "booksManager";
		
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    
    // Table Books
    private static final String TABLE_BOOKS = "books";
    private static final String TITLE = "title";
	private static final String AUTHOR = "author";
	private static final String ISBN = "isbn";
	
	// Table Create Statements
	private static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOKS 
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " TEXT," + AUTHOR + " TEXT,"
            + ISBN + " TEXT, " + KEY_CREATED_AT + " DATETIME" + ")";
	
    
	public DBSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
      
    public DBSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, null, version);
	}
	
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci—n de la tabla
        db.execSQL(CREATE_TABLE_BOOK);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS); 
        // create new tables
        onCreate(db);
    }
    
    public long createBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(TITLE, book.getTitle());
        values.put(AUTHOR, book.getAuthor());
        values.put(ISBN, book.getIsbn());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long contact_id = db.insert(TABLE_BOOKS, null, values);

        return contact_id;
    }
    
    /**
     * get single CONTACT
     */
    public Book getBook(long book_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS + " WHERE "
                + KEY_ID + " = " + book_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Book book = new Book();
        book.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        book.setTitle((c.getString(c.getColumnIndex(TITLE))));
        book.setAuthor((c.getString(c.getColumnIndex(AUTHOR))));
        book.setIsbn((c.getString(c.getColumnIndex(ISBN))));
        book.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
        
        return book;
    }
    
    /**
     * getting all BOOK
     * */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Book b = new Book();
                b.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                b.setTitle(c.getString(c.getColumnIndex(TITLE)));
                b.setAuthor(c.getString(c.getColumnIndex((AUTHOR))));
                b.setIsbn(c.getString(c.getColumnIndex((ISBN))));
                b.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to tags list
                books.add(b);
            } while (c.moveToNext());
        }
        return books;
    }
    
    /**
     * getting selected BOOKS
     * */
    public List<Book> getFilterBooks(String title, String author, String isbn) {
        List<Book> books = new ArrayList<Book>();
        if (title.isEmpty()) title = "NOT_DATA";
        if (author.isEmpty()) author = "NOT_DATA";
        if (isbn.isEmpty()) isbn = "NOT_DATA";
        
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS + " WHERE "
                + TITLE + " LIKE " + "'%"+title+"%'"  + " OR " 
        		+ AUTHOR + " LIKE " + "'%"+author+"%'" + " OR " 
                + ISBN + " LIKE " + "'%"+isbn+"%'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Book b = new Book();
                b.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                b.setTitle(c.getString(c.getColumnIndex(TITLE)));
                b.setAuthor(c.getString(c.getColumnIndex((AUTHOR))));
                b.setIsbn(c.getString(c.getColumnIndex((ISBN))));
                b.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to tags list
                books.add(b);
            } while (c.moveToNext());
        }
        return books;
    }
    
    /**
     * Updating a BOOK
     */
    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, book.getTitle());
        values.put(AUTHOR, book.getAuthor());
        values.put(ISBN, book.getIsbn());

        // updating row
        return db.update(TABLE_BOOKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(book.getId()) });
    }
	
    /**
     * Deleting a BOOK
     */
    public void deleteBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        // now delete the tag
        db.delete(TABLE_BOOKS, KEY_ID + " = ?",
                new String[] { String.valueOf(book.getId()) });
       
    }
	
	public void deleteDataBase(SQLiteDatabase db){
		db.delete(TABLE_BOOKS, null, null);
	}
	   
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
	    if (db != null && db.isOpen())
	    	db.close();
	}
	   
	/**
	* get datetime
	* */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
