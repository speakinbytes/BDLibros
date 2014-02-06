package com.pabloojeda.bdlibros.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class AbstractAdapter<T> extends BaseAdapter {
	private Context mContext;
	private List<T> mItems;
	
	public AbstractAdapter(Context context, List<T> items) {
		mContext = context;
		mItems = items;
	}

	@Override
	public int getCount() {
		return mItems == null ? 0 : mItems.size();
	}
	
	public void replace(int position, T object) {
		
		if(mItems != null && object != null) {
			mItems.remove(position);
			mItems.add(position, object);
			notifyDataSetChanged();
		}
		
	}

	@Override
	public T getItem(int position) {
		return mItems == null ? null : mItems.get(position);
	}
	
	public List<T> getItems() {
		return mItems;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	protected Context getContext() {
		return mContext;
	}
	
	public void addAll(List<T> items) {
		if(mItems == null) {
			mItems = new ArrayList<T>();
		}
		
		mItems.addAll(items);
		
		notifyDataSetChanged();
	}
	
	public void replaceAll(List<T> items) {
		if(mItems != null) {
			mItems.removeAll(mItems);
			
		} else {
			mItems = new ArrayList<T>();
		}
		mItems.addAll(items);
		notifyDataSetChanged();
	}
	
	public void clear() {
		if(mItems != null) {
			mItems.clear();
		}
		
		notifyDataSetChanged();
	}

}
