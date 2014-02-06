package com.pabloojeda.bdlibros.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Book implements Parcelable {
	private int mId;
	private String mTitle;
	private String mAuthor;
	private String mIsbn;
	private String created_at;
	
	public Book() {
		
	}
	
	public Book(int id, String name, String author, String isbn) {
		this.mTitle = name;
		this.mAuthor = author;
		this.mIsbn = isbn;
		this.mId = id;
	}
	
	
	public Book(String name, String author, String isbn) {
		this.mTitle = name;
		this.mAuthor = author;
		this.mIsbn = isbn;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return mId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		mId = id;
	}
	/**
	 * @return the name
	 */
	public String getTitle() {
		return mTitle;
	}
	/**
	 * @param name the name to set
	 */
	public void setTitle(String title) {
		mTitle = title;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return mAuthor;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		mAuthor = author;
	}
	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return mIsbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		mIsbn = isbn;
	}
	
	public String getCreatedAt() {
		return created_at;
	}
	
	public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }
	
	// Parcelling part
    public Book(Parcel in){
        mId = in.readInt();
        mTitle = in.readString();
        mAuthor = in.readString();
        mIsbn = in.readString();
    }
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
    public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mId);
		dest.writeString(mTitle);
		dest.writeString(mAuthor);
		dest.writeString(mIsbn);
    }
	
    @SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Book createFromParcel(Parcel in) {
            return new Book(in); 
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
	
}
