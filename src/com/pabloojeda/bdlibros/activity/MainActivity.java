package com.pabloojeda.bdlibros.activity;

import java.util.List;

import com.pabloojeda.bdlibros.R;
import com.pabloojeda.bdlibros.adapter.AdapterBookList;
import com.pabloojeda.bdlibros.db.DBSQLiteHelper;
import com.pabloojeda.bdlibros.model.Book;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	private ListView ListBooks;
	private EditText editTextTitle;
	private EditText editTextAuthor;
	private EditText editTextIsbn;
	private AdapterBookList adapterBooks;
	public List<Book> allBooks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        Button newContact = (Button) findViewById(R.id.createBook); 
        newContact.setOnClickListener(this);
        
        Button listBooks = (Button) findViewById(R.id.listBooks); 
        listBooks.setOnClickListener(this);
        
        DBSQLiteHelper dbh = new DBSQLiteHelper(getApplicationContext());          	
    	allBooks = dbh.getAllBooks();	
        dbh.closeDB();
        adapterBooks = new AdapterBookList(this, allBooks);
        ListBooks = (ListView) findViewById(R.id.ListBooks);
		ListBooks.setAdapter(adapterBooks);    	
    	// listening to single list item on click
    	ListBooks.setOnItemClickListener(this);
        
    	if (allBooks.isEmpty()){
    		TextView info = (TextView) findViewById(R.id.info);
    		info.setText(R.string.infoNotBooks);
    		info.setTextColor(Color.parseColor(getString(R.color.red)));
    	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
    	editTextTitle = (EditText) findViewById(R.id.titleBook);
    	String title = editTextTitle.getText().toString();
    	editTextAuthor = (EditText) findViewById(R.id.authorBook);
    	String author = editTextAuthor.getText().toString();
    	editTextIsbn = (EditText) findViewById(R.id.isbnBook);
    	String isbn = editTextIsbn.getText().toString();

		switch (v.getId()) {
		// Settings
		case R.id.createBook:
			// Open DB
			if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()){
				if (title.isEmpty()) editTextTitle.setError("No puede estar en blanco");
				if (author.isEmpty()) editTextAuthor.setError("No puede estar en blanco");
				if (isbn.isEmpty()) editTextIsbn.setError("No puede estar en blanco");
			} else {
				DBSQLiteHelper db = new DBSQLiteHelper(getApplicationContext());	        
		        Book book = new Book(title, author, isbn);
		        long book_id = db.createBook(book);
		        Book b = db.getBook(book_id);
		        // Don't forget to close database connection
		        db.closeDB();
		        Toast toastNewBook = Toast.makeText(getApplicationContext(), "CREADO NUEVO LIBRO CON TÍTULO " + b.getTitle(), Toast.LENGTH_LONG);
		        toastNewBook.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
		        toastNewBook.show();
		        
		        adapterBooks.add(allBooks, book);
		        
		        editTextTitle.setText("");
		        editTextAuthor.setText("");
		        editTextIsbn.setText("");	        
			}
			break;
			
		case R.id.listBooks:
			DBSQLiteHelper dbh = new DBSQLiteHelper(getApplicationContext());
        	List<Book> filterBooks = dbh.getFilterBooks(title, author, isbn);
        	List<Book> books = dbh.getAllBooks();
        	dbh.closeDB();
			if (!title.isEmpty() || !author.isEmpty() || !isbn.isEmpty()){
	        	if (filterBooks.isEmpty()){
	        		Toast toast = Toast.makeText(getApplicationContext(), "NO HAY SIMILITUDES EN LA BÚSQUEDA. SE MUESTRAN TODOS LOS LIBROS.", Toast.LENGTH_LONG);
	    			toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
	    			toast.show();
	    			adapterBooks.replaceAll(books);
	        	} else {	
	        		adapterBooks.replaceAll(filterBooks);
	        	}
	        }	
	        else {
	        	Toast toastNotFilter = Toast.makeText(getApplicationContext(), "SE DEVUELVEN TODOS LOS LIBROS", Toast.LENGTH_LONG);
	        	toastNotFilter.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
	        	toastNotFilter.show();
	        	adapterBooks.replaceAll(books);
	        }
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Book book = ((Book)arg0.getAdapter().getItem(arg2));
		
		Intent i = new Intent(arg1.getContext(), ShowBook.class);
		i.putExtra("BOOK", book);
		
		startActivity(i);
	}
}
