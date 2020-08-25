package com.messieyawo.unityassembly.PopupMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.messieyawo.unityassembly.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrayerRequest extends Fragment {
private EditText mRequestName,mRequestType,mNumber,mLocation,mMessage;
private Button mButton;
    public PrayerRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_prayer_request, container, false);

        mRequestName = view.findViewById(R.id.editTextEmail);
        mRequestType = view.findViewById(R.id.editTextMessage);
        mNumber = view.findViewById(R.id.editTextNumber);
        mLocation = view.findViewById(R.id.editTextLocaton);
        mButton = view.findViewById(R.id.buttonSend);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW
                , Uri.parse("mailto: yawomessie@gmail.com"+mRequestName.getText().toString()));
                intent.putExtra(Intent.EXTRA_TITLE,mRequestName.getText().toString());
                intent.putExtra(Intent.EXTRA_SUBJECT,mNumber.getText().toString());
                intent.putExtra(Intent.EXTRA_SUBJECT,mLocation.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,mRequestType.getText().toString());
                startActivity(intent);
            }
        });

        return view;
    }
}
