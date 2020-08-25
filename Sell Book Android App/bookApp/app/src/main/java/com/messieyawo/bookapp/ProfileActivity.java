package com.messieyawo.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName() ;
    private TextView name,email;
    private Button btn_logout,postBook,view_my_post;
    SessionManager sessionManager;
    FloatingActionButton fb;
    CircleImageView  mImage;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri  mImageUri;
    private Bitmap bitmap;

    String getId;
    private static String URL_READ = "http://192.168.8.100/myProjects/sellBook/read_details.php";
    private static String URL_EDIT = "http://192.168.8.100/myProjects/sellBook/edit_details.php";
    private static String URL_UPLOAD = "http://192.168.8.100/myProjects/sellBook/upload.php";
    private Menu action;
    public static final String PREFS = "prefs";
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       // Toolbar toolbar =findViewById(R.id.toolbar_profile);
       // setSupportActionBar(toolbar);
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setIcon(R.drawable.tbook);
        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Glide.get(ProfileActivity.this).clearMemory();

       sessionManager = new SessionManager(this);
       sessionManager.checkLoggin();


        name = findViewById(R.id.Editext_usename);
        email = findViewById(R.id.editText_email);
        fb = findViewById(R.id.update_profile);
        mImage = findViewById(R.id.profile_image);
        postBook = findViewById(R.id.postBook);
        btn_logout = findViewById(R.id.log_out);
        view_my_post = findViewById(R.id.view_my_post);

        HashMap<String,String> user = sessionManager.getUserDetails();
       getId = user.get(sessionManager.ID);

        postBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ProfileActivity.this,AddPostActivity.class);
                startActivity(newIntent);
            }
        });

        view_my_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myPostintent = new Intent(ProfileActivity.this,ViewPostActivity.class);
                startActivity(myPostintent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 sessionManager.logout();

            }
        });
        //update profile picture
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFle();
            }
        });

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUri = prefs.getString("image", null);
          //  mImage.setImageBitmap(bitmap);
        if (mImageUri != null) {
            mImage.setImageURI(Uri.parse(mImageUri));
            // mImage.setImageResource(R.drawable.dick);
        }


    }

    private void chooseFle() {
      permissionsCheck();
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        }
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE_REQUEST);

      // startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

          //filePath = data.getData();
            mImageUri = data.getData();

            // Saves image URI as string to Default Shared Preferences
            // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("image", String.valueOf(mImageUri));
            editor.apply();

            // Sets the ImageView with the Image URI


//            Glide.with(this).load(mImageUri)
//                    .centerCrop()
//                    .transform(new CircleCrop())
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .into(mImage);

            mImage.setImageURI(mImageUri);
            mImage.invalidate();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),  mImageUri);

               // profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPicture(getId,getStringImage(bitmap));
        }
    }

    public void permissionsCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }



    private void UploadPicture(final String id, final String photo) {
     final ProgressDialog progressDialog = new ProgressDialog(this);
     progressDialog.setIcon(R.drawable.ic_cloud_upload_black_24dp);
     progressDialog.setMessage("Uploading please wait....");
     progressDialog.show();

     StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
             new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                     Log.i(TAG,response.toString());
                     try {
                         JSONObject jsonObject = new JSONObject(response);
                         String success = jsonObject.getString("success");
                         if (success.equals("1")){
                             progressDialog.dismiss();
                             Toast.makeText(ProfileActivity.this,"Upload success",Toast.LENGTH_LONG);

                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                         progressDialog.dismiss();
                         Toast.makeText(ProfileActivity.this,"Upload failed try it again.."+e.toString(),Toast.LENGTH_LONG);
                     }

                 }
             },
             new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     progressDialog.dismiss();
                     Toast.makeText(ProfileActivity.this,"Upload failed try it again.."+error.toString(),Toast.LENGTH_LONG);
                 }
             })
     {
         @Override
         protected Map<String, String> getParams() throws AuthFailureError {
             Map<String, String> params = new HashMap<>();
             params.put("id",id);
             params.put("photo",photo);
             return params;
         }
     };

     RequestQueue requestQueue = Volley.newRequestQueue(this);
     requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        return encodeImage;
    }

    private void getUserDetail(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Loading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG,response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                          JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){
                                for (int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String strName = object.getString("name").trim();
                                    String strEmail = object.getString("email").trim();


                                    name.setText(strName);
                                    email.setText(strEmail);


                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this,"Error reading details"+e.toString(),Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this,"Error reading details"+error.toString(),Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.menu_action,menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:

             openDialogEdit();



                name.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(name,InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);
                return true;
            case R.id.menu_save:
                SaveEditDetails();
                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);
                name.setFocusableInTouchMode(false);
                email.setFocusableInTouchMode(false);
                name.setFocusable(false);
                email.setFocusable(false);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openDialogEdit() {
        final Dialog d = new Dialog(ProfileActivity.this);
        d.setContentView(R.layout.input_dialog_event_update);

        //now id for dialog

        final EditText mNameD = d.findViewById(R.id.eventName);
        final EditText mEmailD = d.findViewById(R.id.eventEmail);



        Button saveBtn =  d.findViewById(R.id.saveBtn);
        Button cansel_btn = d.findViewById(R.id.dismiss_dialog);
        cansel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();

            }
        });

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mNameS = mNameD.getText().toString().trim();
                final String mEmailS = mEmailD.getText().toString().trim();


                final TextView mEmail =  findViewById(R.id.editText_email);
                final TextView mName = findViewById(R.id.Editext_usename);


               mEmail.setText(mEmailS);
               mName.setText(mNameS);

                d.dismiss();

            }
        });
        d.show();
        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void SaveEditDetails() {

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String id = getId;



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving your update please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Toast.makeText(ProfileActivity.this,name+" Update successful.",Toast.LENGTH_LONG).show();
                                sessionManager.createSession(name,email,id);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this,"Error saving details"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this,"Error saving details"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("id",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
