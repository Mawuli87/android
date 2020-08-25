package com.messieyawo.unityassembly.Testimonials;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.messieyawo.unityassembly.EvntManager.EventFirebaseDatabaseHelper;
import com.messieyawo.unityassembly.EvntManager.EventObject;
import com.messieyawo.unityassembly.MainActivity;
import com.messieyawo.unityassembly.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import java.util.List;

public class UpdateTestmonialsActivity extends AppCompatActivity {
    private String key;
    private String testName;
    private String testDate;
    private String testDescription;
    private String testLocation;
    private String testType;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
   private EditText mEventName,mEventLocaon,mDescriptonEvent,mdateEvent,mEventType;
   private TextView test,location,description,date,type,testHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_testimonials);

        //get the data from intent object
        Intent getIntent = getIntent();
        key = getIntent.getStringExtra("key");
        testName = getIntent.getStringExtra("testName");
        testDate = getIntent.getStringExtra("testDate");
        testDescription = getIntent.getStringExtra("testDescription");
        testLocation = getIntent.getStringExtra("testLocation");
        testType = getIntent.getStringExtra("testTitle");
        //nitialise the textviews
        testHead = findViewById(R.id.headertxt);

        location = findViewById(R.id.eventLocationtxt);
        description = findViewById(R.id.eventDescriptontxt);
        date = findViewById(R.id.dateEventtxt);
        type = findViewById(R.id.typeEventtxt);

        testHead.setText(testName);

        date.setText(testDate);
        description.setText(testDescription);
        type.setText(testType);
        location.setText(testLocation);



        //init action button
        fab =findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

       fab2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               openAnotherDialog();
           }
       });

    }



    private void openAnotherDialog() {


        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateTestmonialsActivity.this);
        // this is set the view from XML inside AlertDialog
        alert.setTitle("You are about to delete this testimony. Are you sure ?");
        alert.setIcon(R.drawable.ic_delete_all);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                DynamicToast.makeSuccess(UpdateTestmonialsActivity.this, "Good, That's fine, wait it will be deleted soon by "+ testName, Toast.LENGTH_LONG).show();
            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                new TestiMonialsFirebaseDatabaseHelper().deleteTestimonials(key, new TestiMonialsFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Testimonials> testimonials, List<String> keys) {

                    }

                    @Override
                    public void DataInserted() {

                    }

                    @Override
                    public void DataDeleted() {
                        dialog.dismiss();
                           DynamicToast.makeWarning(UpdateTestmonialsActivity.this, "You've deleted successfully your testimony...",Toast.LENGTH_LONG).show();
//                           Intent deleteIntent = new Intent(UpdateTestmonialsActivity.this, MainActivity.class);
//                           startActivity(deleteIntent);
//                           finish();
                    }

                    @Override
                    public void DataUpdated() {

                    }
                });



            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    private void openDialog() {
//        LayoutInflater inflater = getLayoutInflater();
//        View alertLayout = inflater.inflate(R.layout.event_check_update, null);
//        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
//        final EditText etEmail = alertLayout.findViewById(R.id.et_code_event);
//        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);
//
//        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // to encode password in dots
//                    etEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                } else {
//                    // to display the password in normal text
//                    etEmail.setTransformationMethod(null);
//                }
//            }
//        });
//


        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateTestmonialsActivity.this);
        // this is set the view from XML inside AlertDialog
        alert.setTitle("You are about to edit this testimony. Are you sure ?");
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("No ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                DynamicToast.makeSuccess(UpdateTestmonialsActivity.this, "Good, That's fine, wait it will be updated soon buy..." + testName, Toast.LENGTH_LONG).show();
            }
        });

        alert.setPositiveButton("That's fine", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                String user = etUsername.getText().toString().trim();
//                String pass = etEmail.getText().toString().trim();
//                // Toast.makeText(getContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();
//
//                if (user.contains("Mawuli") && pass.contentEquals("1234") || user.contains("kokou") && pass.contentEquals("1235") ||
//                        user.contains("God") && pass.contains("1236")){


                   displayUdpdateEventInputDialog();


//
//                }else {
//
//                    // Toast.makeText(getContext(), "You are a liar " , Toast.LENGTH_SHORT).show();
//                    DynamicToast.makeWarning(UpdateTestmonialsActivity.this, "You've entered the wrong information.Seems you are not part of the daily verses team...",Toast.LENGTH_LONG).show();
//
//                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }


    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }


    //DISPLAY INPUT DIALOG
    private void displayUdpdateEventInputDialog() {

        Dialog d = new Dialog(UpdateTestmonialsActivity.this);
        d.setContentView(R.layout.input_dialog_event_update);

        mEventName =  d.findViewById(R.id.eventName);
        mEventName.setText(testName);
        mEventLocaon =  d.findViewById(R.id.eventLocation);
        mEventLocaon.setText(testLocation);
        mDescriptonEvent =  d.findViewById(R.id.eventDescripton);
        mDescriptonEvent.setText(testDescription);
        mdateEvent = d.findViewById(R.id.dateEvent);
        mdateEvent.setText(testDate);
        mEventType = d.findViewById(R.id.typeEvent);
        mEventType.setText(testType);



        Button saveBtn =  d.findViewById(R.id.saveBtn);
        Button cansel_btn = d.findViewById(R.id.dismiss_dialog);
        cansel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();

            }
        });

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
               Testimonials testimonials = new Testimonials();
                testimonials.setDate(mdateEvent.getText().toString());
                testimonials.setDescription(mDescriptonEvent.getText().toString());
                testimonials.setName(mEventName.getText().toString());
                testimonials.setLocation(mEventLocaon.getText().toString());
                testimonials.setTitle(mEventType.getText().toString());
//                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("Testimonials");
//                dbNode.setValue(null);

               new TestiMonialsFirebaseDatabaseHelper().updateTestimonials(key, testimonials, new TestiMonialsFirebaseDatabaseHelper.DataStatus() {
                   @Override
                   public void DataIsLoaded(List<Testimonials> testimonials, List<String> keys) {

                   }

                   @Override
                   public void DataInserted() {

                   }

                   @Override
                   public void DataDeleted() {

                   }

                   @Override
                   public void DataUpdated() {
                       d.dismiss();
                        DynamicToast.makeSuccess(UpdateTestmonialsActivity.this, "Data has been updated successfully...", Toast.LENGTH_LONG).show();

//                        Intent deleteIntent = new Intent(UpdateTestmonialsActivity.this, MainActivity.class);
//                        startActivity(deleteIntent);
//                        finish();
                   }
               });







            }
        });

        d.show();
        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


}
