package com.pabloojeda.bdlibros.activity;


import com.pabloojeda.bdlibros.R;
import com.pabloojeda.bdlibros.db.DBSQLiteHelper;
import com.pabloojeda.bdlibros.model.Book;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditBook extends Activity implements OnClickListener{
	private Book book;
	private EditText editTextTitle;
	private EditText editTextAuthor;
	private EditText editTextIsbn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_book);
		
		// Recupero el contacto
		Bundle data = getIntent().getExtras();
		book = (Book) data.getParcelable("BOOK");
		
		// Select editTexts
		editTextTitle = (EditText) findViewById(R.id.titleBook);
		editTextAuthor = (EditText) findViewById(R.id.authorBook);
		editTextIsbn = (EditText) findViewById(R.id.isbnBook);
		
		// Put the corrects fields
		if (book != null){
			editTextTitle.setText(book.getTitle());
			editTextAuthor.setText(book.getAuthor());
			editTextIsbn.setText(book.getIsbn());
		}  
		
		Button editBook = (Button) findViewById(R.id.editButton); 
		editBook.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		   	
    	String title = editTextTitle.getText().toString();    	
    	String author = editTextAuthor.getText().toString();
    	String isbn = editTextIsbn.getText().toString();
    				
		switch (v.getId()) {
		// Settings
		case R.id.editButton:
			if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()){
				if (title.isEmpty()) editTextTitle.setError("No puede estar en blanco");
				if (author.isEmpty()) editTextAuthor.setError("No puede estar en blanco");
				if (isbn.isEmpty()) editTextAuthor.setError("No puede estar en blanco");
			} else {
			// Open DB
				DBSQLiteHelper db = new DBSQLiteHelper(getApplicationContext());
	        
				book.setTitle(title);
				book.setAuthor(author);
				book.setIsbn(isbn);
	        
				db.updateBook(book);
	        
				Book b = db.getBook(book.getId());
				// Don't forget to close database connection
				db.closeDB();
	        
				Intent iShowBook = new Intent (this, ShowBook.class);
				iShowBook.putExtra("BOOK", b);
				startActivity(iShowBook);
			}
			break;
		default:
			break;
		}
	}
	
}
