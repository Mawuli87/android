package com.messieyawo.books;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class spUtil {
    //let create a constructor for share preference
    private spUtil(){}

    //let's craete a constant
    public static final String FREE_NAME = "BooksPreferences";
    //lets create a couple of classes in the spUtil class to help us keep tract of the sharedPreferenc
    //first constant for position and the rest for the five queries
    public static final String POSITION = "position";
    public static final String QUERY = "query";//now let's go to searchActivity

    //now lets create a method for the initialisation of the share preference  and call it getPrefs
    //which will take context as an argument
    public static SharedPreferences getPrefs(Context context){

        //here we will just return the result from context through getSharedPreferences
        //passing in the constant FREE_NAME, and another argument Context.MODE_PRIVATE
        //which means only the application can have access to it
        return context.getSharedPreferences(FREE_NAME,Context.MODE_PRIVATE);
    }
    //to read and write the preference we need some more couple of method
    //first we will create a method to read the string from preferences
    //passing as argument context and the key we want to retrieve
    public static String getPreferences (Context context, String key){


        //now we will return getPrefs passing context as argument and call getString()
        // method on it passing in the key and the second paramenter is the default value which is empty string
        return getPrefs(context).getString("key","");
    }

    //we do exatly the same thing for the integer method
    public static int getPreferenceInt(Context context, String key){


        //this time we call the getInt and the default value will be 0
        return getPrefs(context).getInt("key",0);
    }
    //we need to create two more method one to write the string and the other to write the integer
    //lets begin with string method that will take as argument context string containing key and string containing value
    public static void setPreferenceString(Context context,String key, String value){

        //inside the method lets retrieve the editor object first
        SharedPreferences.Editor editor = getPrefs(context).edit();
        //on the edit we will put the string method
        editor.putString(key,value);
        editor.apply();

    }
    //let's do the same with set preference Int method
    public static void setPreferenceInt(Context context,String key, int value){

        //inside the method lets retrieve the editor object first
        SharedPreferences.Editor editor = getPrefs(context).edit();
        //on the edit we will put the string method
        editor.putInt(key,value);
        editor.apply();

    }
    //now we can read and write data through sharedPrefrence
    //now we have come back from searchActivity
    //here lets create a method that return an arrayList of string and the only argument we need is the context

    public static ArrayList<String> getQueryList(Context context){
        //inside the method we will create a new array of string
        ArrayList<String> QueryList = new ArrayList<String>();
        //we know that we have five positions to check
        for (int i = 1; i<= 5; i++){

            String query = getPrefs(context).getString(QUERY + String.valueOf(i),"");
            //because these result will appear on the menu
            if (!query.isEmpty()){
                query = query.replace(",", " ");
                //we dont want commas on our menu then lets remove beginning and ending spacing
                QueryList.add(query.trim());
            }
        }
 return QueryList;
        //now we want the item should appear in the menu
        //so lets go to the oncreate option menu
    }
}
