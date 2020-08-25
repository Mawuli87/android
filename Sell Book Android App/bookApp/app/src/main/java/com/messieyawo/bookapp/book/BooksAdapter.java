

package com.messieyawo.bookapp.book;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.messieyawo.bookapp.BookDetailActivity;
import com.messieyawo.bookapp.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<ListItem> mSportsData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param sportsData ArrayList containing the sports data.
     * @param context Context of the application.
     */
    public BooksAdapter(Context context, ArrayList<ListItem> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.book_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(BooksAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
       ListItem currentSport = mSportsData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentSport);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        TextView id,idImage,title,type,school,price,description,phone,location;
        ImageView image_url;
        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            id = itemView.findViewById(R.id.id);
            idImage = itemView.findViewById(R.id.idImage);
            title = itemView.findViewById(R.id.title_txt);
            type = itemView.findViewById(R.id.book_type);
            school = itemView.findViewById(R.id.school_type);
            price = itemView.findViewById(R.id.price_type);
            description = itemView.findViewById(R.id.description_type);
            phone = itemView.findViewById(R.id.phone_type);
            location = itemView.findViewById(R.id.location_type);
            image_url = itemView.findViewById(R.id.image_book);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(ListItem currentSport){
            // Populate the textviews with data.
            id .setText(currentSport.getId());
            idImage.setText(currentSport.getIdImage());
            title.setText(currentSport.getTitle());
            type.setText(currentSport.getType());
            school.setText(currentSport.getSchool());
            price.setText(currentSport.getPrice());
            description.setText(currentSport.getDescription());
            phone.setText(currentSport.getPhone());
            location.setText(currentSport.getLocation());

            // Load the images into the ImageView using the Glide library.
            //clear all cache
           Glide.get(mContext).clearMemory();
            Glide.with(mContext)
                    .load(
                    currentSport.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(image_url);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            ListItem currentSport = mSportsData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, BookDetailActivity.class);
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            detailIntent.putExtra("id", currentSport.getId());
            detailIntent.putExtra("idImage", currentSport.getIdImage());
            detailIntent.putExtra("title", currentSport.getTitle());
            detailIntent.putExtra("type", currentSport.getType());
            detailIntent.putExtra("school", currentSport.getSchool());
            detailIntent.putExtra("price", currentSport.getPrice());
            detailIntent.putExtra("description", currentSport.getDescription());
            detailIntent.putExtra("phone", currentSport.getPhone());
            detailIntent.putExtra("location", currentSport.getLocation());
            detailIntent.putExtra("photo", currentSport.getImage());
            mContext.startActivity(detailIntent);
        }
    }
}
