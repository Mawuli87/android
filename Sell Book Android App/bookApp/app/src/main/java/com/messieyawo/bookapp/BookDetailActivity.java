package com.messieyawo.bookapp;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.Animator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.messieyawo.bookapp.book.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class BookDetailActivity extends AppCompatActivity {
    TextView id,idImage,title,type,school,price,description,phone,location;
    ImageView bookImage,bookImage_d;

    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen = false;

    private static String URL_EDIT_BOOK = "http://192.168.8.100/myProjects/sellBook/edit_book.php";
    private static String URL_DELETE_BOOK = "http://192.168.8.100/myProjects/sellBook/delete_post.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        //clear all cache
        Glide.get(BookDetailActivity.this).clearMemory();
        id = findViewById(R.id.id_d);
        idImage = findViewById(R.id.idImage_d);
        title = findViewById(R.id.title_txt_d);
        type =findViewById(R.id.book_type_d);
        school = findViewById(R.id.school_type_d);
        price = findViewById(R.id.price_type_d);
        description = findViewById(R.id.description_type_d);
        phone = findViewById(R.id.phone_type_d);
        location = findViewById(R.id.location_type_d);
        bookImage = findViewById(R.id.image_book_d);
        bookImage_d = findViewById(R.id.image_book_d_big);

        //get the id's
        String id  = getIntent().getExtras().getString("id");
        String idImage  = getIntent().getExtras().getString("idImage");

        String title_d  = getIntent().getExtras().getString("title");
        String type_d = getIntent().getExtras().getString("type");
        String school_d = getIntent().getExtras().getString("school") ;
        String price_d = getIntent().getExtras().getString("price");
        String description_d = getIntent().getExtras().getString("description") ;
        String phone_d = getIntent().getExtras().getString("phone") ;
        String location_d = getIntent().getExtras().getString("location") ;
        String mImage_d = getIntent().getExtras().getString("photo") ;
        String mImage_d_bg = getIntent().getExtras().getString("photo") ;

        title.setText(title_d);
        type.setText(type_d);
        school.setText(school_d);
        price.setText(price_d);
        description.setText(description_d);
        phone.setText(phone_d);
        location.setText(location_d);



       //  Picasso.get().load(mImage_d).into(bookImage);



        //glide to load image into the imageView
        Glide.with(BookDetailActivity.this).load( mImage_d)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(bookImage);


        //RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


         //set image using Glide
        Glide.with(BookDetailActivity.this)
                .load(mImage_d_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(bookImage_d);

      //fab menu
        fabLayout1 =  findViewById(R.id.fabLayout1);
        fabLayout2 = findViewById(R.id.fabLayout2);
        fabLayout3 =  findViewById(R.id.fabLayout3);
        fab =  findViewById(R.id.fab);
        fab1 =  findViewById(R.id.fab1);
        fab2 =  findViewById(R.id.fab2);
        fab3 =  findViewById(R.id.fab3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        //edit book page
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBook();
            }
        });
       fab2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deleteBook();
           }
       });


    }

    private void deleteBook() {

        AlertDialog.Builder builder=new AlertDialog.Builder(BookDetailActivity.this); //Home is name of the activity
        builder.setMessage("Are you sure you want to delete this post?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

               callDeleteFunction();

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.show();
    }

    private void callDeleteFunction() {
        final String idUser  = Objects.requireNonNull(getIntent().getExtras()).getString("id");
        final String idImageEdt  = getIntent().getExtras().getString("idImage");
        final String mImage_d = getIntent().getExtras().getString("photo") ;
        //final String title_d  = getIntent().getExtras().getString("title");


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting post  please wait....");
        progressDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL_DELETE_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                progressDialog.dismiss();

                                    // progressDialog.dismiss();
                                    Intent editPage = new Intent(BookDetailActivity.this,ViewPostActivity.class);

                                    startActivity(editPage);




                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            // Toast.makeText(ProfileActivity.this,"Error saving details"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(BookDetailActivity.this,"Error saving details"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",idUser);
                params.put("idImage",idImageEdt);
                params.put("photo",mImage_d);
               // params.put("title",title_d);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest1);



    }

    private void EditBook() {

       final String idUser  = Objects.requireNonNull(getIntent().getExtras()).getString("id");
       final String idImageEdt  = getIntent().getExtras().getString("idImage");
       final String title_d  = getIntent().getExtras().getString("title");
       final String type_d = getIntent().getExtras().getString("type");
       final String school_d = getIntent().getExtras().getString("school") ;
       final String price_d = getIntent().getExtras().getString("price");
       final String description_d = getIntent().getExtras().getString("description") ;
       final String phone_d = getIntent().getExtras().getString("phone") ;
       final String location_d = getIntent().getExtras().getString("location") ;
       final String mImage_d = getIntent().getExtras().getString("photo") ;
       final String mImage_d_bg = getIntent().getExtras().getString("photo") ;




        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading post to be updated please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("editPost");

                            if (success.equals("1")){
                             progressDialog.dismiss();
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String userId = object.getString("id").trim();
                                    String imageId = object.getString("idImage").trim();
                                    String imageTitle = object.getString("title").trim();
                                    String imageType = object.getString("type").trim();
                                    String imageSchool = object.getString("school").trim();
                                    String imagePrice = object.getString("price").trim();
                                    String imageDescription = object.getString("description").trim();
                                    String imagePhone = object.getString("phone").trim();
                                    String imageLocation = object.getString("location").trim();
                                    String imagePhoto = object.getString("photo").trim();


                                   // progressDialog.dismiss();
                                    Intent editPage = new Intent(BookDetailActivity.this,EditBookActivity.class);
                                    editPage.putExtra("editId",userId);
                                    editPage.putExtra("imageId",imageId);
                                    editPage.putExtra("imageTitle",imageTitle);
                                    editPage.putExtra("imageType",imageType);
                                    editPage.putExtra("imageSchool",imageSchool);
                                    editPage.putExtra("imagePrice",imagePrice);
                                    editPage.putExtra("imageDescription",imageDescription);
                                    editPage.putExtra("imagePhone",imagePhone);
                                    editPage.putExtra("imageLocation",imageLocation);
                                    editPage.putExtra("imagePhoto",imagePhoto);
                                  startActivity(editPage);

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                           // Toast.makeText(ProfileActivity.this,"Error saving details"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                      Toast.makeText(BookDetailActivity.this,"Error saving details"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",idUser);
                params.put("idImage",idImageEdt);
                params.put("title",title_d);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }


}
