package com.messieyawo.unityassembly.PopupMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.messieyawo.unityassembly.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonateFragment extends Fragment {

    public DonateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_donate, container, false);


        ImageView whatsapp = view.findViewById(R.id.whatsApp);
        ImageView studioButton = view.findViewById(R.id.studioLine);
        ImageView studioLine2 = view.findViewById(R.id.studioLine2);
        ImageView emergencyLine = view.findViewById(R.id.emergency);
        ImageView sendEmail = view.findViewById(R.id.email);



        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+233542996109";
                String url = "https://api.whatsapp.com/send?phone="+ number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        studioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studiophone = "+233542996109";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", studiophone, null));
                startActivity(intent);
            }
        });

        studioLine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studiophone2 = "+233542996109";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", studiophone2, null));
                startActivity(intent);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","We will put eagle cry email here", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "My Mail");
                intent.putExtra(Intent.EXTRA_TEXT   , "My Message");

                try {
                    startActivity(Intent.createChooser(intent, "Send Mail To Unity assembly of God haatso..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        emergencyLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+233542996109";
                String url = "https://api.whatsapp.com/send?phone="+ number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        return view;
    }
}
