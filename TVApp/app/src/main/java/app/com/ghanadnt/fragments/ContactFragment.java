package app.com.ghanadnt.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import app.com.ghanadnt.DrawerActivity;
import app.com.ghanadnt.MainActivity;
import app.com.ghanadnt.R;
import app.com.ghanadnt.WebActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       ImageView hotButton = view.findViewById(R.id.HotLine);
       ImageView whatsapp = view.findViewById(R.id.whatsApp);
       ImageView studioButton = view.findViewById(R.id.studioLine);
       ImageView studioLine2 = view.findViewById(R.id.studioLine2);
       ImageView sendEmail = view.findViewById(R.id.email);
       ImageView openWebsite = view.findViewById(R.id.openWebite);
       ImageView emergencyLine = view.findViewById(R.id.emergency);

        ((DrawerActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        //((DrawerActivity) getActivity()).getSupportActionBar().setTitle("DNT Live ");




hotButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String phone = "+233302940336";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }
});

whatsapp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String number = "+233272083688";
        String url = "https://api.whatsapp.com/send?phone="+ number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
});

studioButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String studiophone = "+233248142840";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", studiophone, null));
        startActivity(intent);
    }
});

studioLine2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String studiophone2 = "+233248147036";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", studiophone2, null));
        startActivity(intent);
    }
});

sendEmail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","info@dntghana.com", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Mail");
        intent.putExtra(Intent.EXTRA_TEXT   , "My Message");

        try {
            startActivity(Intent.createChooser(intent, "Send mail DNT..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
});

openWebsite.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Intent webIntent = new Intent(getActivity(), WebActivity.class);
       startActivity(webIntent);
    }
});

emergencyLine.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String number = "+17132402164";
        String url = "https://api.whatsapp.com/send?phone="+ number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
});

    }




}
