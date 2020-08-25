package com.messieyawo.bookapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.messieyawo.bookapp.SessionManager.ID;

public class AddPostActivity extends AppCompatActivity {
    EditText mTitle,mPrice,mDescription,mPhone,mLocation;
    Button btn_send,btn_pick;
    Spinner typeSpinner,choiceSpinner;
    ImageView addImage;
    private ProgressBar loading;
    Bitmap bitmap;
    String getId;
    SessionManager sessionManager;
    private String pictureImagePath = "";

    private static String URL_ADDPOST= "http://192.168.8.100/myProjects/sellBook/addpost.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        //clear all cache
        Glide.get(AddPostActivity.this).clearMemory();

            sessionManager = new SessionManager(this);
          // sessionManager.checkLoggin();

        HashMap<String,String> user = sessionManager.getUserDetails();
        getId = user.get(ID);



        mTitle = findViewById(R.id.titleEditText);
        typeSpinner = findViewById(R.id.typeSpinner);
        choiceSpinner = findViewById(R.id.choiceSpinner);
        mPrice = findViewById(R.id.priceEditText);
        mDescription = findViewById(R.id.descEditText);
        mPhone = findViewById(R.id.phoneEditText);
        mLocation = findViewById(R.id.locationEditText);
        btn_pick = findViewById(R.id.btn_pick);
        addImage = findViewById(R.id.addImage);
        btn_send = findViewById(R.id.btn_send);
        btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost(getId);
            }
        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



    }



    private void pickImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPostActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {

                    ActivityCompat.requestPermissions(AddPostActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);

                    ActivityCompat.requestPermissions(AddPostActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);


                }
                else if (options[item].equals("Choose from Gallery"))
                {

                    ActivityCompat.requestPermissions(AddPostActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2);

                    ActivityCompat.requestPermissions(AddPostActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);



//                    Glide.with(this).load(bitmap)
//                            .centerCrop()
//                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                            .transform(new CenterCrop())
//                            .into(addImage);


                   addImage.setImageBitmap(bitmap);
                   addImage.invalidate();
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outFile);

                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                bitmap = (BitmapFactory.decodeFile(picturePath));
//                Picasso.get()
//                        .load(String.valueOf(bitmap))
//                        // .resize(0, 300)
//                        //.resizeDimen(R.dimen.image_size, R.dimen.image_size)
//                        //.onlyScaleDown()
//                        .fit()
//                        .centerCrop()
//                        .into(addImage);

                addImage.setImageBitmap(bitmap);
            }
        }
    }
    //convert bitmap to string
    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        return encodeImage;
    }




    private void addPost(final  String id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.ic_cloud_upload_black_24dp);
        progressDialog.setMessage("Uploading please wait....");
        progressDialog.show();


        final String title = mTitle.getText().toString().trim();
        final String type = typeSpinner.getSelectedItem().toString().trim();
        final String school = choiceSpinner.getSelectedItem().toString().trim();
        final String price = mPrice.getText().toString().trim();
        final String description = mDescription.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        final String location = mLocation.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Toast.makeText(AddPostActivity.this,"Registration successful",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                addImage.setImageBitmap(null);
                                mTitle.setText(null);
                                mPrice.setText(null);
                                mDescription.setText(null);
                                mPhone.setText(null);
                                mLocation.setText(null);

                                Intent viewIntent = new Intent(AddPostActivity.this,ViewPostActivity.class);
                                startActivity(viewIntent);
                               //send user to view post page


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddPostActivity.this,"Registration failed"+e.toString(),Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            addImage.setImageBitmap(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddPostActivity.this,"Registration failed"+error.toString(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("title",title);
                params.put("type",type);
                params.put("school",school);
                params.put("price",price);
                params.put("description",description);
                params.put("phone",phone);
                params.put("location",location);
                params.put("image",getStringImage(bitmap));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
