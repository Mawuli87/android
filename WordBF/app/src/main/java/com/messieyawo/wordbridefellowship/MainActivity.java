package com.messieyawo.wordbridefellowship;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.messieyawo.wordbridefellowship.Adapters.MenuAdapter;
import com.messieyawo.wordbridefellowship.EventManager.MainActivityEvent;
import com.messieyawo.wordbridefellowship.EventManager.SignInActivity;
import com.messieyawo.wordbridefellowship.Fragments.AboutUsFragment;
import com.messieyawo.wordbridefellowship.Fragments.OurLocation;
import com.messieyawo.wordbridefellowship.Fragments.HomeFragment;
import com.messieyawo.wordbridefellowship.Fragments.PrivatePolicy;
import com.messieyawo.wordbridefellowship.Fragments.SetingsFragment;
import com.messieyawo.wordbridefellowship.m_FireBase.FirebaseHelper;
import com.messieyawo.wordbridefellowship.m_Model.Spacecraft;
import com.messieyawo.wordbridefellowship.m_UI.CustomAdapter;
import com.messieyawo.wordbridefellowship.popumenuFragments.EventFragment;
import com.messieyawo.wordbridefellowship.youtubeFragments.ChannelFragment;
import com.messieyawo.wordbridefellowship.youtubeFragments.ChannelPlayListFragment;

import lib.ar.arvind.typewriter.TypeWriterTextView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {



    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;

    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptionsHome)));

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        goToFragment(new HomeFragment(), false);
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));


    }



    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this); //Home is name of the activity
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                finish();
                Intent i=new Intent();
                i.putExtra("finish", true);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                //startActivity(i);
                finish();

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.show();

    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.replace(R.id.container, fragment).commit();
    }



    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {
            case 0:
                goToFragment(new HomeFragment(), false);
                break;
            case 1:
                goToFragment(new ChannelFragment(), false);
                break;
            case 2:
                Intent webIntent = new Intent(MainActivity.this,WebActivity.class);
                startActivity(webIntent);
              //  goToFragment(new ChannelPlayListFragment(), false);
                break;
            case 3:
                goToFragment(new AboutUsFragment(), false);
                break;
            case 4:
                goToFragment(new OurLocation(),false);
                break;
            case 5:goToFragment(new PrivatePolicy(),false);
            break;
            case 6:
                goToFragment(new SetingsFragment(),false);
                break;
            default:
                break;
        }
        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }



    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout =  findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar =  findViewById(R.id.toolbar);
        }
    }




    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            Intent newIntent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(newIntent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wordbride, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_menus:
                OpenDialogBox();
                break;
            case R.id.action_location:
               // OpenDialogBox();
                break;
            case R.id.action_settings:
                //
                break;
        }
//        if (id == R.id.action_menus) {
//            OpenDialogBox();
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void OpenDialogBox() {



        final PopupWindow mpopup;

        // getting the layout of the popup view . in this case it is about.xml
        final View popUpView = getLayoutInflater().inflate(R.layout.grid_item_list, null,false);


        mpopup = new PopupWindow(popUpView, 700, 1100, true); // here 400 and 500 is the height and width of layout
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        //location of popup view on the screen
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
        ImageView imageViewEvent =(ImageView)popUpView.findViewById(R.id.event_id);
        imageViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // goToFragment(new EventFragment(), false);
               Intent eventIntent = new Intent(MainActivity.this, SignInActivity.class);
               startActivity(eventIntent);


                // Close the drawer
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                mpopup.dismiss();
            }
        });
        Button mCansel_btn = popUpView.findViewById(R.id.cansel_btn);
        mCansel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpopup.dismiss();
            }
        });




//        LayoutInflater inflater = getLayoutInflater();
//        View alertLayout = inflater.inflate(R.layout.grid_item_list, null);
////        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
////        final EditText etEmail = alertLayout.findViewById(R.id.et_email);
////        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);
//
////        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                if (isChecked) {
////                    // to encode password in dots
////                    etEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
////                } else {
////                    // to display the password in normal text
////                    etEmail.setTransformationMethod(null);
////                }
////            }
////        });
//
//
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Menu");
//        // this is set the view from XML inside AlertDialog
//        alert.setView(alertLayout);
//        // disallow cancel of AlertDialog on click of back button and outside touch
//        alert.setCancelable(false);
//
//
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                String user = etUsername.getText().toString();
////                String pass = etEmail.getText().toString();
////                Toast.makeText(getBaseContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();
//            }
//        });
//        AlertDialog dialog = alert.create();
//        dialog.show();


    }


}
