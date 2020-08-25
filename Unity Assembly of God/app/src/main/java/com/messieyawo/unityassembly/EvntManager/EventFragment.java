package com.messieyawo.unityassembly.EvntManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.allyants.notifyme.NotifyMe;
import com.github.clans.fab.FloatingActionButton;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;
import com.messieyawo.unityassembly.DalyVerses.DailyVerses;
import com.messieyawo.unityassembly.DalyVerses.FirebaseDatabaseHelper;
import com.messieyawo.unityassembly.EvntManager.EventFirebaseDatabaseHelper;
import com.messieyawo.unityassembly.EvntManager.EventObject;
import com.messieyawo.unityassembly.EvntManager.EventRecyclerViewConfig;
import com.messieyawo.unityassembly.MainActivity;
import com.messieyawo.unityassembly.R;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    private RecyclerView mRecyclerView;
    ProgressBar progressBar;
    EditText mEventName, mEventLocaon, mDescriptonEvent, mdateEvent, mEventType;
    private FloatingActionButton fab1;




    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);


        //can also be used for notification

//              FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//              DatabaseReference reference = firebaseDatabase.getReference();
//              reference.child("WordBride")
//                      .addValueEventListener(new ValueEventListener() {
//                          @Override
//                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Iterable<DataSnapshot> chldren = dataSnapshot.getChildren();
//
//                            for(DataSnapshot child: chldren){
//
//                             DailyVerses value = child.getValue(DailyVerses.class);
//
//                          //   Toast.makeText(getActivity(),"data: "+ value.toString(),Toast.LENGTH_LONG).show();
//                              //  EventNotify();
//
//
//                            }
//                          }
//
//                          @Override
//                          public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                          }
//                      });

        fab1 = view.findViewById(R.id.fab1);
        mRecyclerView = view.findViewById(R.id.recyclerViewEvent);
        progressBar = view.findViewById(R.id.loading_event_main);
        new EventFirebaseDatabaseHelper().readEvent(new EventFirebaseDatabaseHelper.DataStatus() {
            @Override
            public void EventDataIsLoaded(List<EventObject> eventObjects, List<String> keys) {
                progressBar.setVisibility(View.GONE);
                new EventRecyclerViewConfig().setConfg(mRecyclerView, getActivity(), eventObjects, keys);
            }

            @Override
            public void EventDataInserted() {

            }

            @Override
            public void EventDataDeleted() {

            }

            @Override
            public void EventDataUpdated() {

            }

        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.event_check, null);
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


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("No let me Go back", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                        DynamicToast.makeSuccess(getActivity(), "Good, That's fine, wait it will be updated soon...", Toast.LENGTH_LONG).show();
                    }
                });

                alert.setPositiveButton("That's fine", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user = etUsername.getText().toString().trim();
                        String pass = etEmail.getText().toString().trim();
                        // Toast.makeText(getContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();

                        if (user.contains("Word") && pass.contentEquals("1234") || user.contains("Paul") && pass.contentEquals("6789") ||
                                user.contains("Bride") && pass.contains("1111")) {


                            displayInputDialog();


                        } else {

                            // Toast.makeText(getContext(), "You are a liar " , Toast.LENGTH_SHORT).show();
                            DynamicToast.makeWarning(getContext(), "You've entered the wrong information.Seems you are not part of the daily verses team...", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();


            }

        });


        return view;
    }


    //DISPLAY INPUT DIALOG

    //DISPLAY INPUT DIALOG
    private void displayInputDialog() {

        Dialog d = new Dialog(getActivity());
        d.setContentView(R.layout.input_dialog_event);

        mEventName = d.findViewById(R.id.eventName);
        mEventLocaon = d.findViewById(R.id.eventLocation);
        mDescriptonEvent = d.findViewById(R.id.eventDescripton);
        mdateEvent = d.findViewById(R.id.dateEvent);
        mEventType = d.findViewById(R.id.typeEvent);

        Button saveBtn = d.findViewById(R.id.saveBtn);
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
                new EventFirebaseDatabaseHelper().addEventMethod(eventObject, new EventFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void EventDataIsLoaded(List<EventObject> eventObjects, List<String> keys) {

                    }

                    @Override
                    public void EventDataInserted() {

                        d.dismiss();
                        DynamicToast.makeSuccess(getActivity(), "Today's bible verse is updated successfully..", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void EventDataDeleted() {

                    }

                    @Override
                    public void EventDataUpdated() {

                    }


                });


            }
        });

        d.show();
        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }






//public void EventNotify(){
//
//    //notiffication
//    NotifyMe notifyMe = new NotifyMe.Builder(getContext())
//            .title("PURCHASE PREPAID CREDIT.")
//            .content("valued customer, this is a reminder to purchase prepaid credit today.Thank you.")
//            .color(255,0,0,255)
//            .led_color(255,255,255,255)
//            .small_icon(R.drawable.small_eagle)
//            .addAction(new Intent(),"Snooze",false)
//            .key("test")
//            .addAction(new Intent(getActivity(), MainActivity.class),"Dismiss",false,true)
//            .addAction(new Intent(),"Done")
//
//
//            .build();
//
//}



}