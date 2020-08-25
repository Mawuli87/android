package com.messieyawo.wordbridefellowship.youtubeFragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.messieyawo.wordbridefellowship.Adapters.VideoPostAdapter;
import com.messieyawo.wordbridefellowship.DetailsActivity;
import com.messieyawo.wordbridefellowship.R;
import com.messieyawo.wordbridefellowship.interfaces.OnItemClickListener;
import com.messieyawo.wordbridefellowship.models.YoutubeDataModel;

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

import ir.hatamiarash.toast.RTLToast;
import work.zice.loadingview.AnimalLoadingView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {

    private static String GOOGLE_YOUTUBE_API_KEY = "";//here you should use your api key for testing purpose you can use this api also
    private static String CHANNEL_ID = ""; //here you should use your channel id for testing purpose you can use this ID also
    private static String CHANNLE_GET_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId="+CHANNEL_ID+"&maxResults=5&key="+GOOGLE_YOUTUBE_API_KEY +"";


    private RecyclerView mList_videos = null;
    private VideoPostAdapter adapter = null;
    private ArrayList<YoutubeDataModel> mListData = new ArrayList<>();
     AnimalLoadingView loadingView;
    public ChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel, container, false);
        mList_videos = view.findViewById(R.id.mList_videos_channel);
        initList(mListData);
        new RequestYoutubeAPI().execute();
        return view;
    }




    private void initList(ArrayList<YoutubeDataModel> mListData) {
        mList_videos.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VideoPostAdapter(getActivity(), mListData, new OnItemClickListener() {
            @Override
            public void onItemClick(YoutubeDataModel item) {
                YoutubeDataModel youtubeDataModel = item;
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(YoutubeDataModel.class.toString(), youtubeDataModel);
                startActivity(intent);
            }
        });
        mList_videos.setAdapter(adapter);

    }


    //create an asynctask to get all the data from youtube
    private class RequestYoutubeAPI extends AsyncTask<Void, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConnectivityManager conMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ){
                // notify user you are online
                loadingView = new AnimalLoadingView();
                loadingView.show(getActivity().getSupportFragmentManager(),"");

            }else {

                // notify user you are not online
                RTLToast.Config.getInstance()
                        .setTextColor(Color.WHITE)
                        .apply();
                RTLToast.custom(getActivity(), "Please check your internet"+"\n"+"connection you might not be connected.", getResources().getDrawable(R.drawable.live_streaming),
                        Color.BLUE, Toast.LENGTH_LONG, true, true).show();
                RTLToast.Config.reset();



            }
        }

        @Override
        protected String doInBackground(Void... params) {


            //this method too works to convert the channels videos into string forms
//
//
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                        .url(CHANNLE_GET_URL)
//                         .build();
//            Log.e("URL", CHANNLE_GET_URL);
//            try {
//                Response response = client.newCall(request).execute();
//                assert response.body() != null;
//                String json = response.body().string();
//                return json;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }



            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(CHANNLE_GET_URL);
            Log.e("URL", CHANNLE_GET_URL);
            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                String json = EntityUtils.toString(httpEntity);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
                loadingView.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("response", jsonObject.toString());
                    mListData = parseVideoListFromResponse(jsonObject);
                    initList(mListData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<YoutubeDataModel> parseVideoListFromResponse(JSONObject jsonObject) {
        ArrayList<YoutubeDataModel> mList = new ArrayList<>();

        if (jsonObject.has("items")) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if (json.has("id")) {
                        JSONObject jsonID = json.getJSONObject("id");
                        String video_id = "";
                        if (jsonID.has("videoId")) {
                            video_id = jsonID.getString("videoId");
                        }
                        if (jsonID.has("kind")) {
                            if (jsonID.getString("kind").equals("youtube#video")) {
                                YoutubeDataModel youtubeObject = new YoutubeDataModel();
                                JSONObject jsonSnippet = json.getJSONObject("snippet");
                                String title = jsonSnippet.getString("title");
                                String description = jsonSnippet.getString("description");
                                String publishedAt = jsonSnippet.getString("publishedAt");
                                String thumbnail = jsonSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

                                youtubeObject.setTitle(title);
                                youtubeObject.setDescription(description);
                                youtubeObject.setPublishedAt(publishedAt);
                                youtubeObject.setThumbnail(thumbnail);
                                youtubeObject.setVideo_id(video_id);
                                mList.add(youtubeObject);

                            }
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return mList;

    }

}
