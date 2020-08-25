package com.messieyawo.books;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class Book implements Parcelable {
    public String id;
    public String title;
    public String subTitle;
    public String authors;
    public String publisher;
    public String publishedDate;
    public String description;



    public Book(String id, String title, String subTitle, String[] authors, String publisher, String publishedDate,String description) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = TextUtils.join(",",authors);
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;

    }
//this is a constructor that takes in a parcel and from here populate other members
    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();

    }
//this is the creator which create the parcel method and return a new book that cause the constructor passing the new parcel
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //witatParcel method takes the parcel and name it for additional flags
    //and flatten our book to a parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeString(authors);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
        parcel.writeString(description);

    }
    //now let create our binding adapter

//    @BindingAdapter({"android:imageUrl"})
//    public static void loadImage(ImageView view, String imageUrl) {
//      Picasso.get().load(imageUrl).placeholder(R.drawable.book_icon).into(view);
//
//
//    }

}
//lets recap what happen
//we implements parcelable interface on the Book class and android studio help us build the parcelable classes.

//now we click on the list we want to get to the new activity so the first thing to do
//is to go to our book viewholder class and implement an onClicklistener