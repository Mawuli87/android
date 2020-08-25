package app.com.ghanadnt;



import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.drawerlayout.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

public class LiveTvFragment extends Fragment {

    public LiveTvFragment(){

    }



    private String videoURL = "https://youtu.be/Z7v5VUWJ_Lo";
    WebView webView;

    private ProgressDialog progressDialog;

    private DrawerLayout drawer;

    //video steaming url
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_tv,container,false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = getView().findViewById(R.id.videoView);
       // ((DrawerActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((DrawerActivity) getActivity()).getSupportActionBar().setTitle("DNT Live ");





        //firebase authentication


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Buffering.....");
        progressDialog.setCancelable(true);
        playVideo();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    private void playVideo() {
        try {


            //getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(webView);

            //WebView webView = findViewById(R.id.web);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://youtu.be/Z7v5VUWJ_Lo");


            //parse url s uri
            //Uri videoUri = Uri.parse(videoURL);
            //set media controller to videoview
          //  webView.setMediaController(mediaController);
            //set video uri
            //videoView.setVideoURI(videoUri);

           // videoView.requestFocus();
           // videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
               // @Override
               // public void onPrepared(MediaPlayer mp) {
                    //dismiss progressdialog and play video
                   // progressDialog.dismiss();
                   // webView.start();
               // }
           // });

        }catch (Exception e){

            //if anything goes wrong,get and display exception message
            progressDialog.dismiss();
            //Toast.makeText(this,"Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }








}
