package com.messieyawo.unityassembly.EvntManager;


import android.content.Context;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.messieyawo.unityassembly.R;

import java.util.List;

public class EventRecyclerViewConfig {

    //helps make calls to activities methods
    private Context mContext;




    private DailyEventAdapter mDailyEventAdapter;
    public void setConfg(RecyclerView recyclerView,Context context,List<EventObject> eventObjects,List<String> keys){
        mContext = context;
        mDailyEventAdapter = new DailyEventAdapter(eventObjects, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mDailyEventAdapter);
    }

    //inflate the recyclerview layout and populate it views
    class DailyEventView extends RecyclerView.ViewHolder {
        private TextView mEventName;
        private TextView mEventDescription;
        private TextView mEventDate;
        private TextView mEventLocation;
        private TextView mEventType;
        private String key;
        //string variable for holding the keys of the variable

        public DailyEventView(@NonNull View parent){
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.model_event, (ViewGroup) parent,false));

            mEventName = itemView.findViewById(R.id.name_publsher);
            mEventDescription = itemView.findViewById(R.id.description);
            mEventLocation = itemView.findViewById(R.id.verse_published);
            mEventDate = itemView.findViewById(R.id.date_published);
            mEventType =itemView.findViewById(R.id.typeVerse);

            //send to edit page
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UpdateEventActivity.class);
                    intent.putExtra("key",key);
                    intent.putExtra("eventName",mEventName.getText().toString());
                    intent.putExtra("eventDate",mEventDate.getText().toString());
                    intent.putExtra("eventDescription",mEventDescription.getText().toString());
                    intent.putExtra("eventLocation",mEventLocation.getText().toString());
                    intent.putExtra("eventType",mEventType.getText().toString());
                    mContext.startActivity(intent);


                }
            });

        }


        public void bind(EventObject eventObject,String key){
            //then published textview from the book objects
            mEventName.setText(eventObject.getEventName());
            mEventDate.setText(eventObject.getEventDate());
            mEventLocation.setText(eventObject.getEventlocaton());
            mEventDescription.setText(eventObject.getEventDescription());
            mEventType.setText(eventObject.getEventType());
            this.key = key;
        }

    }



    //responsible for creating daily verses vew object and pass it to bind to bind view
    class DailyEventAdapter extends RecyclerView.Adapter<DailyEventView> {
        private List<EventObject> mDailyEventList;
        private List<String> mKeys;

        public DailyEventAdapter(List<EventObject> mDailyEventList, List<String> mKeys) {
            this.mDailyEventList = mDailyEventList;
            this.mKeys = mKeys;
        }


        @NonNull
        @Override
        public DailyEventView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DailyEventView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DailyEventView holder, int position) {
            holder.bind(mDailyEventList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mDailyEventList.size();
        }
    }
}