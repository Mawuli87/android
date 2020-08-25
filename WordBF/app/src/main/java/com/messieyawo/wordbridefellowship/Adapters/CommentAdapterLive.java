package com.messieyawo.wordbridefellowship.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.messieyawo.wordbridefellowship.R;
import com.messieyawo.wordbridefellowship.models_live.YoutubeCommentModelLive;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by Suman Dey(MONSTER TECHNO) on 1/4/18.
 */

public class CommentAdapterLive extends RecyclerView.Adapter<CommentAdapterLive.YoutubeCommentHolder> {

    private ArrayList<YoutubeCommentModelLive> dataSet;
    private static Context mContext = null;

    public CommentAdapterLive(Context mContext, ArrayList<YoutubeCommentModelLive> data) {
        this.dataSet = data;
        this.mContext = mContext;
    }

    @Override
    public CommentAdapterLive.YoutubeCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.youtube_comment_layout_live, parent, false);
        YoutubeCommentHolder postHolder = new YoutubeCommentHolder(view);
        return postHolder;
    }

    @Override
    public void onBindViewHolder(YoutubeCommentHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        TextView feedback = holder.feedback;
        ImageView imageView = holder.imageViewIcon;
        YoutubeCommentModelLive object = dataSet.get(position);
        textViewName.setText(object.getTitle());
        feedback.setText(object.getComment());
        try {
            if (object.getThumbnail() != null) {
                if (object.getThumbnail().startsWith("http")) {
                    Picasso.get()
                            .load(object.getThumbnail())
                            .into(imageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class YoutubeCommentHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView feedback;
        ImageView imageViewIcon;

        public YoutubeCommentHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.imageViewIcon =  itemView.findViewById(R.id.profile_image);
            this.feedback =  itemView.findViewById(R.id.feedback);

        }

    }

}


