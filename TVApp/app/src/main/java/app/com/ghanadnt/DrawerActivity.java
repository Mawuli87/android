package app.com.ghanadnt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import app.com.ghanadnt.fragments.ContactFragment;

public class DrawerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private String videoURL = "http://app.pakistanvision.tv:1935/live/PTVnews/player.m3u8";
    VideoView videoView;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDntHome()).commit();
            //navigationView.setCheckedItem(R.id.nav_home);
        }

        //iniatiate the progress bar
        progressDialog = new ProgressDialog(DrawerActivity.this);

        //initialising variables of videoView
        videoView = findViewById(R.id.videoView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Buffering.....");
        progressDialog.setCancelable(true);
        playVideo();

        //initialize webView
        WebView webView = findViewById(R.id.web);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.nav_live_tv:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LiveTvFragment()).commit();
                break;

            case R.id.nav_visite_web:
                Intent webIntent = new Intent(this,WebActivity.class);
                startActivity(webIntent);
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentWebSite()).commit();
                break;

            case R.id.nav_video_library:
                Intent intent = new Intent(this,MainActivity.class);
              startActivity(intent);

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentDntHome()).commit();
                break;
            case R.id.contact:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ContactFragment()).commit();
                    break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void playVideo() {

        try {

            getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            //parse url s uri
            Uri videoUri = Uri.parse(videoURL);
            //set media controller to videoview
            videoView.setMediaController(mediaController);
            //set video uri
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //dismiss progressdialog and play video
                    progressDialog.dismiss();
                    videoView.start();
                }
            });

        }catch (Exception e){

            //if anything goes wrong,get and display exception message
            progressDialog.dismiss();
            // Toast.makeText(this,"Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    public void OpenVideoLibrary(View view) {
        Intent intent = new Intent(this,MainActivity.class);
         startActivity(intent);
    }

    public void OpenLiveTv(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LiveTvFragment()).commit();

    }

    public void OpenWebsite(View view) {
        Intent webIntent = new Intent(this,WebActivity.class);
        startActivity(webIntent);
    }


    public void ContactPage(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ContactFragment()).commit();
    }


}
