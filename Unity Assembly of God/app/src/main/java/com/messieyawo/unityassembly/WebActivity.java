package com.messieyawo.unityassembly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.messieyawo.unityassembly.youtubeFragments.ChannelPlayListFragment;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

public class WebActivity extends AppCompatActivity {
    WebView yt;
    ProgressBar pb,hb;
   // String url = "https://www.youtube.com/channel/UCmNuVBKQmvm_pZYm3RteMog";
    WebChromeClient myNewWebClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_channel_play_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Unity assembly Channel");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        yt= findViewById(R.id.YTWeb);
        pb=findViewById(R.id.progressbar);
        hb=findViewById(R.id.horizontalPb);
        if(isNetworkAvailable()){
           // yt.loadUrl(url);
          //  DynamicToast.makeSuccess(WebActivity.this,"Your Youtube Channel wil be here", Toast.LENGTH_LONG).show();

            new FancyAlertDialog.Builder(this)
                    .setTitle("Youtube Channel")
                    .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                    .setMessage("Your youtube channel will be here")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                    .setAnimation(Animation.POP)
                    .isCancellable(true)
                    .setIcon(R.drawable.testimony_48, Icon.Visible)
                    .OnPositiveClicked(new FancyAlertDialogListener() {
                        @Override
                        public void OnClick() {
                            Intent newIntent = new Intent(WebActivity.this,MainActivity.class);
                            startActivity(newIntent);
                        }
                    })
                    .build();

        }
        else{
            pb.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(toolbar, "No Internet Connection..!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(WebActivity.this, WebActivity.class);
                            startActivity(i);
                        }
                    });
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();
        }
        WebSettings webSettings = yt.getSettings();
        webSettings.setJavaScriptEnabled(true);
        yt.setWebViewClient(new WebViewClient());

        yt.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                hb.setProgress(newProgress);
                if(newProgress==100){
                    pb.setVisibility(View.GONE);
                    hb.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(WebActivity.this); //Home is name of the activity
//        builder.setMessage("Are you sure you want to Quit?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//
//                finish();
//                Intent i=new Intent(WebActivity.this,MainActivity.class);
//               // i.putExtra("finish", true);
//                 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
//                 startActivity(i);
//                finish();
//
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent i1 = new Intent(WebActivity.this,WebActivity.class);
//                startActivity(i1);
//            }
//        });
//    }
}
