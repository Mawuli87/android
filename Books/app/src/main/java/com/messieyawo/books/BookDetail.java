package com.messieyawo.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.messieyawo.books.databinding.ActivityBookDetailBinding;

public class BookDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        //let' read the book from the bandle to get the parcelable intent overour book

        Book book = getIntent().getParcelableExtra("Book");
        //to make sure everything is working perfectly
        ActivityBookDetailBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_book_detail);
        binding.setBook(book);
    }
}
