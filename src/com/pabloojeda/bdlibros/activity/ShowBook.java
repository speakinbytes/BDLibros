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
import android.widget.TextView;

public class ShowBook extends Activity implements OnClickListener{
	
	private Book book;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_book);
		
		TextView txtTitle = (TextView) findViewById(R.id.bookTitle);
		TextView txtAuthor = (TextView) findViewById(R.id.bookAuthor);
		TextView txtIsbn = (TextView) findViewById(R.id.bookIsbn);
		
		Button bEdit = (Button) findViewById(R.id.editBook); 
        bEdit.setOnClickListener(this);
		Button bDelete = (Button) findViewById(R.id.deleteBook); 
        bDelete.setOnClickListener(this);
        Button bShow = (Button) findViewById(R.id.showBooks); 
        bShow.setOnClickListener(this);
		
		// Obtenemos el libro
		Bundle data = getIntent().getExtras();
		book = (Book) data.getParcelable("BOOK");
		
		if (book != null){
			txtTitle.setText(book.getTitle());
			txtAuthor.setText(book.getAuthor());
			txtIsbn.setText(book.getIsbn());
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
    				
		switch (v.getId()) {
		case R.id.deleteBook:
			// Open DB
	        DBSQLiteHelper db = new DBSQLiteHelper(getApplicationContext());	        
	        db.deleteBook(book);	        
	        // Don't forget to close database connection
	        db.closeDB();
	        Intent i = new Intent(this, MainActivity.class);
	        startActivity(i);
	        
			break;
		case R.id.editBook:
			Intent iEdit = new Intent(this, EditBook.class);
			iEdit.putExtra("BOOK", book);			
			startActivity(iEdit);
			break;
		case R.id.showBooks:
			Intent iShowBooks = new Intent(this, MainActivity.class);
			startActivity(iShowBooks);
			break;
		default:
			break;
		}
	}

}
