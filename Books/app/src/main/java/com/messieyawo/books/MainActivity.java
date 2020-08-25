package com.messieyawo.books;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
private ProgressBar mLoadingProgress;
//fromthe activity class let create arecycler call rvBooks
    private RecyclerView rvBooks;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView =(SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        //here let's retrieve the arrayList of our items
        ArrayList<String> recentList = spUtil.getQueryList(getApplicationContext());
        //let's count them because their number maybe smaller
       int itemNum = recentList.size();
     MenuItem recentMenu;
        //let craete a for loop that add menus
        for (int i = 0; i<itemNum; i++){

            recentMenu = menu.add(Menu.NONE,i,Menu.NONE,recentList.get(i));
            //now lets respond to the click in onOptionItemSelected
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_advanced_search:
               Intent intent = new Intent(this, SearchActivity.class);
               startActivity(intent);
               return true;
                default:

                    //we will use the default case here as we said the id of the menu
                    int position = item.getItemId() + 1;
                    String preferenceName = spUtil.QUERY +(position);
                    //here we will get the string that contain the query
                   // String query = spUtil.getPreferences(getApplicationContext(),prefernceName);
                    String query = spUtil.getPreferences(getApplicationContext(),preferenceName);
                    String[] prefParams = query.split("\\,");
                    String[] queryParams = new String[4];
                    //now lets cycle the element

                    for (int i=0; i<prefParams.length; i++){

                        queryParams[i] = prefParams[i];
                    }
                    //finaly build the url
                    URL bookUrl = ApiUtil.buildURL((queryParams[0]==null )?"":queryParams[0],
                            (queryParams[1]==null )?"":queryParams[0],
                            (queryParams[2]==null )?"":queryParams[0],
                            (queryParams[3]==null )?"":queryParams[0]);
                     new BooksQueryTask().execute(bookUrl);
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingProgress = findViewById(R.id.pb_loading);
        rvBooks = findViewById(R.id.rv_books);

        Intent intent = getIntent();
        String query = intent.getStringExtra("Query");
        URL bookUrl;

        try {
            if (query== null || query.isEmpty()){
              bookUrl = ApiUtil.buildURL("cooking");
            }else{
              bookUrl = new URL(query);
            }
          new BooksQueryTask().execute(bookUrl);
        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }



        //finaly let's create a new layout manager

        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        //now let set the layoutManager on the RecyclerView
        rvBooks.setLayoutManager(booksLayoutManager);
        //TextView tvResult = findViewById(R.id.tvResponse);
      //the MainActivity here is the booklist activity
        //now here let's build the url first
        //we create a url called bookURL and call the method buildURL and passing cooking as an argument
        try {

//we need to put all these in the catch exception block and log it
            URL bookURL = ApiUtil.buildURL("Stories ");
            //now we create a string called called JsonResult calling Json method and passing the url

            //now we need to create a new query under the booklist task and call it execute method
            new BooksQueryTask().execute(bookURL);




            //finally we will write the result in our textView using the  setText method.
           // tvResult.setText(jsonResult);
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());


        }
        //then we need to write in textView the result we got
        //first let's go above and get the textView
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
       try {

URL bookURL = ApiUtil.buildURL(s);
new BooksQueryTask().execute(bookURL);

       }catch (Exception e){
Log.d("Error",e.getMessage());
       }
       return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    //in our booklist activity class let's create a new public class that extends async task
    //the first parameter is the type of paramter sent to the task for an execution
    //progress is the progress publish at the background during execution
    // but we do not need that here. so we will put void there
    //result is the result of the backgroung of computor issue so we pss a string as a result
    public class BooksQueryTask extends AsyncTask<URL,Void,String>{
        @Override

        protected String doInBackground(URL... urls) {
            //we have URL as parameter because android studio recognize
            // that we need a URL function to do the background function.
            //you may notice that android studio is giving us error
            //this is because we need to implement the required method of this class.which is doing in background
            //the three dots means we can pass more parameters and the result is always an array
            //the function return String because we have specified that
            URL searchURL = urls[0];
            //let's declare the string that will contain the result at the beginning it willbe null
            String result = null;
            //so in the try catch block we will put our json result
            try {
                result = ApiUtil.getJson(searchURL);
            }
            catch (Exception e){
                Log.e("Error",e.getMessage());
            }

            return result;
        }
        //we need to implement onpost method when the background has completed.
        @Override
        protected void onPostExecute(String result) {

            TextView tvError = findViewById(R.id.tverror);
            //after loading data
            mLoadingProgress.setVisibility(View.INVISIBLE);

            //and one last task in order to call the Async task
            //in order to call the async task we need to instantiate the class and call the execute method
            //and we do that in the oncreate function off the booklist activity class.
           if (result == null){
               rvBooks.setVisibility(View.INVISIBLE);
               tvError.setVisibility(View.VISIBLE);
           }else{

              rvBooks.setVisibility(View.VISIBLE);
               tvError.setVisibility(View.INVISIBLE);

               ArrayList<Book> books = ApiUtil.getBooksFromJson(result);
               String resultString = "";

              //let create a new book adapter call adapter and pass it the book array list
               BooksAdapter adapter = new BooksAdapter(books);
               rvBooks.setAdapter(adapter);


           }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //before loading
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
