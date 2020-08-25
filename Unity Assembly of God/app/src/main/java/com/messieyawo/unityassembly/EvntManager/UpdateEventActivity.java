package com.messieyawo.unityassembly.EvntManager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.messieyawo.unityassembly.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;

public class UpdateEventActivity extends AppCompatActivity {
    private String key;
    private String eventName;
    private String eventDate;
    private String eventDescription;
    private String eventLocation;
    private String eventType;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
   private EditText mEventName,mEventLocaon,mDescriptonEvent,mdateEvent,mEventType;
   private TextView event,location,description,date,type,eventHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        //get the data from intent object
        Intent getIntent = getIntent();
        key = getIntent.getStringExtra("key");
        eventName = getIntent.getStringExtra("eventName");
        eventDate = getIntent.getStringExtra("eventDate");
        eventDescription = getIntent.getStringExtra("eventDescription");
        eventLocation = getIntent.getStringExtra("eventLocation");
        eventType = getIntent.getStringExtra("eventType");
        //nitialise the textviews
        eventHead = findViewById(R.id.headertxt);

        location = findViewById(R.id.eventLocationtxt);
        description = findViewById(R.id.eventDescriptontxt);
        date = findViewById(R.id.dateEventtxt);
        type = findViewById(R.id.typeEventtxt);
        eventHead.setText(eventName);

        date.setText(eventDate);
        description.setText(eventDescription);
        type.setText(eventType);
        location.setText(eventLocation);



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

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.event_delete, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        final EditText etEmail = alertLayout.findViewById(R.id.et_code_event);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);

        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // to encode password in dots
                    etEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // to display the password in normal text
                    etEmail.setTransformationMethod(null);
                }
            }
        });



        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateEventActivity.this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("No let me Go back", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                DynamicToast.makeSuccess(UpdateEventActivity.this, "Good, That's fine, wait it will be deleted soon by "+ eventName, Toast.LENGTH_LONG).show();
            }
        });

        alert.setPositiveButton("That's fine", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = etUsername.getText().toString().trim();
                String pass = etEmail.getText().toString().trim();
                // Toast.makeText(getContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();

                if (user.contains("Word") && pass.contentEquals("1234") || user.contains("Paul") && pass.contentEquals("6789") ||
                        user.contains("Bride") && pass.contains("1111")){


                   new EventFirebaseDatabaseHelper().deleteEvent(key, new EventFirebaseDatabaseHelper.DataStatus() {
                       @Override
                       public void EventDataIsLoaded(List<EventObject> eventObjects, List<String> keys) {

                       }

                       @Override
                       public void EventDataInserted() {

                       }

                       @Override
                       public void EventDataDeleted() {
                          dialog.dismiss();
                           DynamicToast.makeWarning(UpdateEventActivity.this, "You've deleted successfully your event...",Toast.LENGTH_LONG).show();
//                           Intent deleteIntent = new Intent(UpdateEventActivity.this, MainActivity.class);
//                           startActivity(deleteIntent);
//                           finish();
                       }

                       @Override
                       public void EventDataUpdated() {

                       }
                   });



                }else {

                    // Toast.makeText(getContext(), "You are a liar " , Toast.LENGTH_SHORT).show();
                    DynamicToast.makeWarning(UpdateEventActivity.this, "You've entered the wrong information.Seems you are not part of the daily verses team "+eventName,Toast.LENGTH_LONG).show();

                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    private void openDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.event_check_update, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        final EditText etEmail = alertLayout.findViewById(R.id.et_code_event);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);

        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // to encode password in dots
                    etEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // to display the password in normal text
                    etEmail.setTransformationMethod(null);
                }
            }
        });



        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateEventActivity.this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("No let me Go back", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                DynamicToast.makeSuccess(UpdateEventActivity.this, "Good, That's fine, wait it will be updated soon buy..." + eventName, Toast.LENGTH_LONG).show();
            }
        });

        alert.setPositiveButton("That's fine", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = etUsername.getText().toString().trim();
                String pass = etEmail.getText().toString().trim();
                // Toast.makeText(getContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();

                if (user.contains("Word") && pass.contentEquals("1234") || user.contains("Paul") && pass.contentEquals("6789") ||
                        user.contains("Bride") && pass.contains("1111")){


                   displayUdpdateEventInputDialog();



                }else {

                    // Toast.makeText(getContext(), "You are a liar " , Toast.LENGTH_SHORT).show();
                    DynamicToast.makeWarning(UpdateEventActivity.this, "You've entered the wrong information.Seems you are not part of the daily verses team...",Toast.LENGTH_LONG).show();

                }
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

        Dialog d = new Dialog(UpdateEventActivity.this);
        d.setContentView(R.layout.input_dialog_event_update);

        mEventName =  d.findViewById(R.id.eventName);
        mEventName.setText(eventName);
        mEventLocaon =  d.findViewById(R.id.eventLocation);
        mEventLocaon.setText(eventLocation);
        mDescriptonEvent =  d.findViewById(R.id.eventDescripton);
        mDescriptonEvent.setText(eventDescription);
        mdateEvent = d.findViewById(R.id.dateEvent);
        mdateEvent.setText(eventDate);
        mEventType = d.findViewById(R.id.typeEvent);
        mEventType.setText(eventType);



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
                EventObject eventObject = new EventObject();
                eventObject.setEventDate(mdateEvent.getText().toString());
                eventObject.setEventDescription(mDescriptonEvent.getText().toString());
                eventObject.setEventName(mEventName.getText().toString());
                eventObject.setEventlocaton(mEventLocaon.getText().toString());
                eventObject.setEventType(mEventType.getText().toString());

//                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("WordBrideEvent");
//                dbNode.setValue(null);
                new EventFirebaseDatabaseHelper().updateEvent(key,eventObject,new EventFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void EventDataIsLoaded(List<EventObject> eventObjects, List<String> keys) {

                    }

                    @Override
                    public void EventDataInserted() {
                        d.dismiss();
                        DynamicToast.makeSuccess(UpdateEventActivity.this, "Today's event or announcement is updated successfully..", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void EventDataDeleted() {

                    }

                    @Override
                    public void EventDataUpdated() {
                        d.dismiss();
                        DynamicToast.makeSuccess(UpdateEventActivity.this, "Data has been updated successfully...", Toast.LENGTH_LONG).show();

//                        Intent deleteIntent = new Intent(UpdateEventActivity.this, MainActivity.class);
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
