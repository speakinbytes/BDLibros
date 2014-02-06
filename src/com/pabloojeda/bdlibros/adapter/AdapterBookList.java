package com.pabloojeda.bdlibros.adapter;

import java.util.List;

import com.pabloojeda.bdlibros.R;
import com.pabloojeda.bdlibros.model.Book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterBookList extends AbstractAdapter<Book>{
	public AdapterBookList(Context context, List<Book> items) {
		super(context, items);
	}
	
	public void add(List<Book> items, Book book) {
		items.add(book);
		
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Book item = getItem(position);
		
		Holder holder = null;
		
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext())
					.inflate(R.layout.list_books, null);
			holder = new Holder();
			
			holder.title = (TextView) convertView.findViewById(R.id.bookTitle);
			holder.author = (TextView) convertView.findViewById(R.id.bookAuthor);
			holder.isbn = (TextView) convertView.findViewById(R.id.bookIsbn);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.title.setText(item.getTitle());
		holder.author.setText(item.getAuthor());
		holder.isbn.setText(item.getIsbn());
		
		return convertView;
		
	}
	
	class Holder {
		TextView title;
		TextView author;
		TextView isbn;
	}

}
