package com.messieyawo.unityassembly;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import com.messieyawo.unityassembly.Adapters.MenuAdapter;
import com.messieyawo.unityassembly.Fragments.AboutUsFragment;
import com.messieyawo.unityassembly.Fragments.OurLocation;
import com.messieyawo.unityassembly.Fragments.HomeFragment;
import com.messieyawo.unityassembly.Fragments.PrivatePolicy;
import com.messieyawo.unityassembly.Fragments.SetingsFragment;

import com.messieyawo.unityassembly.EvntManager.EventFragment;
import com.messieyawo.unityassembly.PopupMenu.DonateFragment;
import com.messieyawo.unityassembly.PopupMenu.PrayerRequest;
import com.messieyawo.unityassembly.Testimonials.TestimonialsFragment;
import com.messieyawo.unityassembly.youtubeFragments.ChannelFragment;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

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


        mpopup = new PopupWindow(this);// here 400 and 500 is the height and width of layout
        mpopup.setContentView(popUpView);
        mpopup.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mpopup.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        //location of popup view on the screen
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
        ImageView imageViewEvent =(ImageView)popUpView.findViewById(R.id.event_id);
        ImageView imageViewTestimony = popUpView.findViewById(R.id.testimonial_id);
        ImageView imageViewRequest = popUpView.findViewById(R.id.prayer_request_id);
        ImageView imageViewGive = popUpView.findViewById(R.id.give_id);
        ImageView imageWebsite = popUpView.findViewById(R.id.website_id);
        ImageView imageContact = popUpView.findViewById(R.id.contact_church_id);
        ImageView imageFacebook = popUpView.findViewById(R.id.church_facebook_id);
        ImageView imageSupportEvan = popUpView.findViewById(R.id.evengelism_id);
        imageSupportEvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new DonateFragment(), false);

                // Close the drawer
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                mpopup.dismiss();
            }
        });
        imageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//           Intent facebook = new Intent(MainActivity.this,FacebookActivity.class);
////           startActivity(facebook);

                DynamicToast.makeSuccess(MainActivity.this, "We need to put \n" +
                        "Unity assembly of God website link here...", Toast.LENGTH_LONG).show();
            }
        });

        imageContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new AboutUsFragment(), false);

                // Close the drawer
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                mpopup.dismiss();
            }
        });

        imageWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicToast.makeSuccess(MainActivity.this, "We need to put Unity website link here...", Toast.LENGTH_LONG).show();
            }
        });

        imageViewGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new DonateFragment(), false);

                // Close the drawer
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                mpopup.dismiss();

            }
        });
        imageViewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new PrayerRequest(), false);

                // Close the drawer
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                mpopup.dismiss();
            }
        });
        imageViewTestimony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(new TestimonialsFragment(), false);

                // Close the drawer
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                mpopup.dismiss();
            }
        });
        imageViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToFragment(new EventFragment(), false);

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






    }


}
