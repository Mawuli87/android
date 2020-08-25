package com.messieyawo.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.messieyawo.bookapp.book.ListItem;
import com.messieyawo.bookapp.book.BooksAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ViewPostActivity extends AppCompatActivity {

    private static final String URL_LOAD_DATA = "http://192.168.8.100/myProjects/sellBook/viewPost.php";


    // Member variables.
    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<ListItem> mSportsData;
    private BooksAdapter mAdapter;

   // private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
   // private List<ListItem> listItem;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        //clear all cache
        Glide.get(ViewPostActivity.this).clearMemory();

        mRecyclerView = findViewById(R.id.recyclerView_book);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        mSportsData =  new ArrayList<>();
          loadRecyclerViewData();


        // Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,

                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                // Remove the item from the dataset.
               // mSportsData.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
               // mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Attach the helper to the RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);

    }


    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.ic_cloud_upload_black_24dp);
        progressDialog.setMessage("Uploading please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOAD_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                                for (int i=0; i< jsonArray.length(); i++){
                                    JSONObject o = jsonArray.getJSONObject(i);
                                    ListItem item = new ListItem(
                                            o.getString("id"),
                                            o.getString("idImage"),
                                            o.getString("title"),
                                            o.getString("type"),
                                            o.getString("school"),
                                            o.getString("price"),
                                            o.getString("description"),
                                            o.getString("phone"),
                                            o.getString("location"),
                                            o.getString("photo")



                                    );
                                    mSportsData.add(item);
                                    progressDialog.dismiss();
                                }
                                adapter = new BooksAdapter(getApplicationContext(), mSportsData);
                              mRecyclerView.setAdapter(adapter);
                                progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ViewPostActivity.this,"Error "+e.toString(),Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ViewPostActivity.this,"Error "+error.toString(),Toast.LENGTH_LONG).show();

                    }
                });



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
