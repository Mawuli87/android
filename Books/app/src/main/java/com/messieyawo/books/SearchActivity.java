package com.messieyawo.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etTitle =  findViewById(R.id.etTitle);
        final EditText etAuthor =  findViewById(R.id.etAuthor);
        final EditText etPublisher =  findViewById(R.id.etPublisher);
        final EditText etIsbn =  findViewById(R.id.etIsbn);
       final Button button = findViewById(R.id.btnSearch);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String title = etTitle.getText().toString().trim();
               String author = etAuthor.getText().toString().trim();
               String publisher = etPublisher.getText().toString().trim();
               String isbn = etIsbn.getText().toString().trim();
               if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){
              String message = getString(R.string.no_serch_data);
                   Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

               }else {


                   URL queryUrl = ApiUtil.buildURL(title,author,publisher,isbn);
                   //sharedPreferences
                   Context context = getApplicationContext();
                   //next retrieve positioninteger
                   int position = spUtil.getPreferenceInt(context, spUtil.POSITION);
                   //the first time when we call this methos we will get a default value which is zero
                   if (position == 0 || position == 5){

                       position = 1;

                   }else {

                position++;
                   }
                   //now that we know to update lets create the string containing the key concatinated with the query parameter
                   String key = spUtil.QUERY + String.valueOf(position);
                   String value = title + ", " + author + ","+publisher+","+isbn;
                   //lets call setPreferenceString method passing in context key and value
                   spUtil.setPreferenceString(context,key,value);
                   spUtil.setPreferenceInt(context,spUtil.POSITION,position);
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   intent.putExtra("Query",queryUrl);
                   startActivity(intent);

               }
           }
       });
    }
}
