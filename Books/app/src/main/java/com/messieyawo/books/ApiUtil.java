package com.messieyawo.books;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    //this class will contain contants and static method and will never be instatiate
    //so it is a good idea to remove the constractor

    private ApiUtil(){}
//now lets create a constant for the base url for the Api and we will follow java convention to write constant all upercase
     public static    final String BASE_API_URL =
                "https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY = "q";
    public  static final String KEY = "key";
     public  static final String API_KEY = "AIzaSyCK9Z5fuQBLM0RXV58u1Wmkt9zznb0269c";
    //public  static final String API_KEY = "AIzaSyCK9Z5fuQBLM0RXV58u1Wmkt9zznb0269c";

    public static final String TITLE = "intitle:";
    public static final String AUTHOR = "inauther:";
    public static final String PUBLISHER = "inpublisher:";
    public static final String ISBN = "isbn:";
        //now we need to add aquery for the title
    //it will be a function that return a URL and take a string as parameter
    public static URL buildURL (String title) {
      //this string will contain the title we want to query
        //we are going to use the base URL, it want change
        String fullURL = BASE_API_URL + "?q=" + title;
        URL url = null;
//        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
//                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
//                .appendQueryParameter(KEY,API_KEY)
//                .build();
        //lets add a catch block and in the catch block lets prints a stack trace.
        try {
            url = new URL(fullURL);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
//now that we have build the url we need to connect to the API
    //in order to connect to the api
    // we will create a new method call getJason it will return a string and take as a parameter the url
    //the url we want to connect to

    public static URL buildURL(String title,String author,String publisher, String isbn){
URL url = null;
StringBuilder sb = new StringBuilder();
if (!title.isEmpty()) sb.append(TITLE + title +"+");
if (!author.isEmpty()) sb.append(AUTHOR +author +"+");
if (!publisher.isEmpty()) sb.append(PUBLISHER +publisher +"+");
if (!isbn.isEmpty()) sb.append(ISBN +isbn +"+");
sb.setLength(sb.length()-1);
String query = sb.toString();
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY,query)
                .appendQueryParameter(KEY,API_KEY)
                .build();
        try {
            url = new URL(uri.toString());

        }catch (Exception e){
         e.printStackTrace();

        }
 return url;
    }


  public static String getJson(URL url) throws IOException {
      //first we need to establish a connection to the url that will be passed
      //the object to use in order to do that is the http url connection that will establish the actual connection.
      //this will happen when only we will try to read data from it.to create it we will call the open connection on the url
      //because open connection can trow an io exception we could try and catch it by putting it into a try catch block or
      //we can also specify the exeption so that it can be handle by the caller. which throws IOException  above

          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//once we have the connection we need to create an input stream that will allow us to read any data
      //the stream could be a file , a web page or an image and call getinputStream method from the connection

      try {


          InputStream stream = connection.getInputStream();
          //once we have the stream as we are reading Json we need to convert our string to a string
          //there are several ways to do that in android but one of the most easiest way is using a scanner.
          //which has the advantage of buffering the data and encoding characters to utf-16which is android format
          //let's create a new instance of a scanner
          Scanner scanner = new Scanner(stream);
          //this scanner can be used to delimit large spaces of stream into smaller one.
          //in this case we will set the delimeter to \\A which means we want to read everything
          scanner.useDelimiter("\\A");
          //next lets check whether we have data by using hasNext method

          //it will be a good idea to enclose this code into try catch block
          //then in the catch we can log the error.

          boolean hasData = scanner.hasNext();
          //the has next function return true if there is data
          if (hasData) {

              return scanner.next();

          } else {
              return null;
          }
      }
      catch (Exception e){
          Log.d("Error",e.toString());
          return null;
      }
      //and in the finally block we definitely want to close the connection calling disconnect method
      finally {
connection.disconnect();
      }
  }
//that's all there is only one step before we can verify everything is working.
    //we need to call this method from our activity and put the result into our text box.
    //in the booklist method in oncreate method
//lets create another function
  public static ArrayList<Book> getBooksFromJson(String json){

        final  String ID ="id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUME_INFO = "volumeInfo";
        final  String DESCRIPTION = "description";

      //we declare an array list of and setbit to null at the beginning
      ArrayList<Book> books = new ArrayList<Book>();

      try {
     //json object
          JSONObject jsonBooks = new JSONObject(json);
          //lets get the array that contain all the books
          JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
          //now we need to know how many books we will retrieve

          int numberOfBooks = arrayBooks.length();
          //now lets loop through the json array
          for (int i=0; i< numberOfBooks; i++){

              //then we will create a single json object
              JSONObject bookJSON = arrayBooks.getJSONObject(i);
              //we will also get the volume info
              JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUME_INFO);
              //we need to get authors which is an array

              //let's add json object for the image
              int authorNum;
             try {
                 authorNum = volumeInfoJSON.getJSONArray(AUTHORS).length();
             }catch (Exception e){

                 authorNum = 0;
             }

              //then we create an array of string that will contain the authors
              String[] authors = new String[authorNum];
              for (int j= 0; j<authorNum ; j++){
                  //then we will create a single json object
                  //but this time we will use get method
                  authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();


              }
              //now lets create a new book retrieving all the data we need
              //and use the get method to get the id and title
              Book book = new Book(
                      bookJSON.getString(ID),
                      volumeInfoJSON.getString(TITLE),
                      //the sbtitle key may not be there all so we will use a ternary operator
                      (volumeInfoJSON.isNull(SUBTITLE)?"":volumeInfoJSON.getString(SUBTITLE)),
                      authors,
                      (volumeInfoJSON.isNull(PUBLISHER) ? " ": volumeInfoJSON.getString(PUBLISHER)),
                      (volumeInfoJSON.isNull(PUBLISHED_DATE) ? "": volumeInfoJSON.getString(PUBLISHED_DATE)),
                      (volumeInfoJSON.isNull(DESCRIPTION) ? "": volumeInfoJSON.getString(DESCRIPTION)));
              books.add(book);


          }
      }catch (JSONException e){
       e.printStackTrace();

      }
      return books;
      //now lets go above and create a few constant of data we want to retrieve from our json

  }

    }

