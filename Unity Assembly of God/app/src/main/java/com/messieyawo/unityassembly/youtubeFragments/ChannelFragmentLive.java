package com.messieyawo.unityassembly.youtubeFragments;

import android.graphics.Color;
import android.os.Bundle;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.messieyawo.unityassembly.Adapters.VideoPostAdapterLive;
import com.messieyawo.unityassembly.DetailsActivityLive;
import com.messieyawo.unityassembly.MainActivity;
import com.messieyawo.unityassembly.R;
import com.messieyawo.unityassembly.interfaces_live.OnItemClickListenerLive;
import com.messieyawo.unityassembly.models_live.YoutubeDataModelLive;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragmentLive extends Fragment {
//
//    private static String GOOGLE_YOUTUBE_API_KEY = "AIzaSyBJQYpQRTzM5wuuhMUxmP7rvP3lbMGtUZ8";//here you should use your api key for testing purpose you can use this api also
//    private static String CHANNEL_ID = "UCmNuVBKQmvm_pZYm3RteMog"; //here you should use your channel id for testing purpose you can use this ID also
//   // private static String CHANNLE_GET_URL = ""+CHANNEL_ID+"&maxResults=1&key="+GOOGLE_YOUTUBE_API_KEY +"";
//    private static String CHANNLE_GET_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId="+CHANNEL_ID+"&maxResults=1&key="+GOOGLE_YOUTUBE_API_KEY +"";



    private RecyclerView mList_videos = null;
    private VideoPostAdapterLive adapter = null;
    private ArrayList<YoutubeDataModelLive> mListData = new ArrayList<>();

    public ChannelFragmentLive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel_live, container, false);
        mList_videos = (RecyclerView) view.findViewById(R.id.mList_videos);
        initList(mListData);
       //  new RequestYoutubeAPI().execute();





        return view;
    }

    private void initList(ArrayList<YoutubeDataModelLive> mListData) {
        mList_videos.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VideoPostAdapterLive(getActivity(), mListData, new OnItemClickListenerLive() {
            @Override
            public void onItemClick(YoutubeDataModelLive item) {
                YoutubeDataModelLive youtubeDataModelLive = item;
                Intent intent = new Intent(getActivity(), DetailsActivityLive.class);
                intent.putExtra(YoutubeDataModelLive.class.toString(), youtubeDataModelLive);
                startActivity(intent);
            }
        });
        mList_videos.setAdapter(adapter);

    }

//
//    //create an asynctask to get all the data from youtube
//    private class RequestYoutubeAPI extends AsyncTask<Void, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpGet httpGet = new HttpGet(CHANNLE_GET_URL);
//            Log.e("URL", CHANNLE_GET_URL);
//            try {
//                HttpResponse response = httpClient.execute(httpGet);
//                HttpEntity httpEntity = response.getEntity();
//                String json = EntityUtils.toString(httpEntity);
//                return json;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//           return null;
//        }
//
//        @Override
//        protected void onPostExecute(String response) {
//            super.onPostExecute(response);
//            if (response != null) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Log.e("response", jsonObject.toString());
//                    mListData = parseVideoListFromResponse(jsonObject);
//                    initList(mListData);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public ArrayList<YoutubeDataModelLive> parseVideoListFromResponse(JSONObject jsonObject) {
//        ArrayList<YoutubeDataModelLive> mList = new ArrayList<>();
//
//        if (jsonObject.has("items")) {
//            try {
//                JSONArray jsonArray = jsonObject.getJSONArray("items");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject json = jsonArray.getJSONObject(i);
//                    if (json.has("id")) {
//                        JSONObject jsonID = json.getJSONObject("id");
//                        String video_id = "";
//                        if (jsonID.has("videoId")) {
//                            video_id = jsonID.getString("videoId");
//                        }
//                        if (jsonID.has("kind")) {
//                            if (jsonID.getString("kind").equals("youtube#video")) {
//                                YoutubeDataModelLive youtubeObject = new YoutubeDataModelLive();
//                                JSONObject jsonSnippet = json.getJSONObject("snippet");
//                                String title = jsonSnippet.getString("title");
//                                String description = jsonSnippet.getString("description");
//                                String publishedAt = jsonSnippet.getString("publishedAt");
//                                String thumbnail = jsonSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");
//
//                                youtubeObject.setTitle(title);
//                                youtubeObject.setDescription(description);
//                                youtubeObject.setPublishedAt(publishedAt);
//                                youtubeObject.setThumbnail(thumbnail);
//                                youtubeObject.setVideo_id(video_id);
//                                mList.add(youtubeObject);
//
//                            }
//                        }
//                    }
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return mList;
//
//    }

}
