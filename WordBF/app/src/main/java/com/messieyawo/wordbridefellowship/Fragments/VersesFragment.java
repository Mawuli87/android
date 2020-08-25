package com.messieyawo.wordbridefellowship.Fragments;


import android.app.Activity;
import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.messieyawo.wordbridefellowship.MainActivity;
import com.messieyawo.wordbridefellowship.R;
import com.messieyawo.wordbridefellowship.m_FireBase.FirebaseHelper;
import com.messieyawo.wordbridefellowship.m_Model.Spacecraft;
import com.messieyawo.wordbridefellowship.m_UI.CustomAdapter;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.prush.typedtextview.TypedTextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class VersesFragment extends Fragment {
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;
   EditText nameEditTxt,propTxt,descTxt, etDate;


    public VersesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_daily_verses, container, false);



        lv = view.findViewById(R.id.lv);

        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference();
        helper=new FirebaseHelper(db);

        //ADAPTER
        adapter=new CustomAdapter(getActivity(),helper.retrieve());
       lv.setAdapter(adapter);

        //refresh adapter to retreive existing data
         FloatingActionButton fabRefresh = view.findViewById(R.id.fab_b);
         fabRefresh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 adapter = new CustomAdapter(getActivity(), helper.retrieve());
                 lv.setAdapter(adapter);


             }
         });


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogBox();
            }

        });


        //typewriter

        TypedTextView typedTextView = view.findViewById( R.id.typedTextView );
            //Using Builder
        TypedTextView.Builder builder = new TypedTextView.Builder( typedTextView )
                .setTypingSpeed( 175 )
                .splitSentences( true )
                .setSentencePause( 1500 )
                .setCursorBlinkSpeed( 530 )
                .randomizeTypingSpeed( true )
                .showCursor( false )
                .playKeyStrokesAudio(false )
                .randomizeTypeSeed( 250 );

        typedTextView = builder.build();

        typedTextView.setTypedText( "Welcome to Word Bride Fellowship.We are glad you are here..below we display our daily Bible meditation verses.Click refresh to view it if not yet displayed.God bless you." );

        return view;
    }



    private void openDialogBox() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_two, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        final EditText etEmail = alertLayout.findViewById(R.id.et_email);
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
        alert.setTitle("Let's Check if you are part of the verse of the day team.");
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

                if (user.contains("Mawuli") && pass.contentEquals("1234") || user.contains("kokou") && pass.contentEquals("1235") ||
                user.contains("God") && pass.contains("1236")){

                    displayInputDialog();




                }else {

                   // Toast.makeText(getContext(), "You are a liar " , Toast.LENGTH_SHORT).show();
                    DynamicToast.makeWarning(getContext(), "You've entered the wrong information.Seems you are not part of the daily verses team...",Toast.LENGTH_LONG).show();

                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    //DISPLAY INPUT DIALOG
    private void displayInputDialog() {

     DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().child("WordBrideVerses");
     dbNode.getRoot().child("WordBrideVerses").setValue(null);
        Dialog d = new Dialog(getActivity());
        d.setContentView(R.layout.input_dialog);

        nameEditTxt =  d.findViewById(R.id.nameEditText);
        propTxt =  d.findViewById(R.id.propellantEditText);
        descTxt =  d.findViewById(R.id.descEditText);
       etDate =  d.findViewById(R.id.dateEditText);


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
                String name = nameEditTxt.getText().toString();
                String propellant = propTxt.getText().toString();
                String desc = descTxt.getText().toString();
                String dateA = etDate.getText().toString();





                //SET DATA
                Spacecraft s = new Spacecraft();
                s.setName(name);
                s.setPropellant(propellant);
                s.setDescription(desc);
                s.setDate(dateA);





                //SIMPLE VALIDATION
                if (name != null && name.length() > 0) {
                    //THEN SAVE
                    if (helper.save(s)) {
                        //IF SAVED CLEAR EDITXT
                        nameEditTxt.setText("");
                        propTxt.setText("");
                        descTxt.setText("");
                        etDate.setText("");



                        adapter = new CustomAdapter(getActivity(), helper.retrieve());
                        lv.setAdapter(adapter);
                        DynamicToast.makeSuccess(getActivity(), "Today's bible verse is updated successfully..", Toast.LENGTH_SHORT).show();
                        Intent newIntent = new Intent(getActivity(), MainActivity.class);
                        startActivity(newIntent);

                        d.dismiss();

                    }
                } else {
                    DynamicToast.makeWarning(getActivity(), "Content must not be empty please...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        d.show();
        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        }
    }
}
