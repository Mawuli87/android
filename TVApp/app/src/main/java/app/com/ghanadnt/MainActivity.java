package app.com.ghanadnt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    WebView yt;
    ProgressBar pb,hb;
    String url = "https://www.youtube.com/channel/UCB_ZwuWCAuB7y0B93qvnkWw";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  Toolbar toolbar = findViewById(R.id.toolbar);
  setSupportActionBar(toolbar);
  toolbar.setLogo(R.drawable.ic_video_library);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        yt=findViewById(R.id.YTWeb);
        pb=findViewById(R.id.progressbar);
        hb=findViewById(R.id.horizontalPb);
        if(isNetworkAvailable()){
            yt.loadUrl(url);
        }
        else{
            pb.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(toolbar, "No Internet Connection..!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            finish();
                            startActivity(i);
                        }
                    });
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();
        }
        WebSettings webSettings = yt.getSettings();
        webSettings.setJavaScriptEnabled(true);
        yt.setWebViewClient(new MyWebViewClient());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open) {
            boolean isAppExists = false;
            isAppExists = isInstalled("com.google.android.youtube");
            if(isAppExists=true) {
                Intent i = new Intent("android.intent.action.VIEW", Uri.parse(url));
                startActivity(i);
            }
            else {
                Toast.makeText(this, "YouTube App Not Installed", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && yt.canGoBack()) {
            yt.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    private boolean isInstalled(String uri){
        PackageManager pm = getPackageManager();
        boolean isIn;
        try{
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            isIn=true;
        }catch(PackageManager.NameNotFoundException e){
            isIn=false;
        }
        return isIn;
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit")



                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // showInterstitial();
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  showInterstitial();
                        dialog.cancel();
                    }

                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Exit.?");
        alertDialog.show();


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
