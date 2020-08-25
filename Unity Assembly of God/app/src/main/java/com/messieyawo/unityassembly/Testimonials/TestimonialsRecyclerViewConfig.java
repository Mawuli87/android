package com.messieyawo.unityassembly.Testimonials;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.messieyawo.unityassembly.EvntManager.UpdateEventActivity;
import com.messieyawo.unityassembly.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TestimonialsRecyclerViewConfig {

    //helps make calls to activities methods
    private Context mContext;

    private TestimonialsAdapter mTestimonialsAdapter;


    public void setConfg(RecyclerView recyclerView,Context context,List<Testimonials> testimonials,List<String> keys){
        mContext = context;
        mTestimonialsAdapter = new TestimonialsAdapter(testimonials,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mTestimonialsAdapter);
    }

    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());
    //inflate the recyclerview layout and populate it views
    class TestimonialsView extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mDescription;
        private TextView mDate;
        private TextView mTitle;
        private TextView mLocation;
        private String key;
        private TextView mCurrentTime;
        //string variable for holding the keys of the variable

        public TestimonialsView(@NonNull View parent){
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.model_testiony, (ViewGroup) parent,false));


            mName = itemView.findViewById(R.id.name_testimony);
            mDescription = itemView.findViewById(R.id.description_testimony);
            mDate = itemView.findViewById(R.id.date_published);
            mTitle =itemView.findViewById(R.id.testimony_published);
            mCurrentTime = itemView.findViewById(R.id.date_current);
            mLocation = itemView.findViewById(R.id.location_testimony);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UpdateTestmonialsActivity.class);
                    intent.putExtra("key",key);
                    intent.putExtra("testName",mName.getText().toString());
                    intent.putExtra("testDate",mDate.getText().toString());
                    intent.putExtra("testDescription",mDescription.getText().toString());
                    intent.putExtra("testLocation",mTitle.getText().toString());
                    intent.putExtra("testTitle",mLocation.getText().toString());
                    mContext.startActivity(intent);
                }
            });



        }


        public void bind(Testimonials testimonials,String key){
            //then published textview from the book objects
            mName.setText(testimonials.getName());
            mDate.setText(testimonials.getDate());
            mTitle.setText(testimonials.getTitle());
            mDescription.setText(testimonials.getDescription());
            mLocation.setText(testimonials.getLocation());

            mCurrentTime.setText("Current date: "+date);
            this.key = key;
        }

    }
    //responsible for creating daily verses vew object and pass it to bind to bind view
    class TestimonialsAdapter extends RecyclerView.Adapter<TestimonialsView> {
        private List<Testimonials> mTestimonialsList;
        private List<String> mKeys;

        public TestimonialsAdapter(List<Testimonials> mTestimonialsList, List<String> mKeys) {
            this.mTestimonialsList = mTestimonialsList;
            this.mKeys = mKeys;
        }


        @NonNull
        @Override
        public TestimonialsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TestimonialsView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TestimonialsView holder, int position) {
            holder.bind(mTestimonialsList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mTestimonialsList.size();
        }
    }
}