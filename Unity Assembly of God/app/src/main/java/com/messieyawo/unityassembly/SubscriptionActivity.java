package com.messieyawo.unityassembly;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class SubscriptionActivity extends AppCompatActivity {

    private TextView mArrow_home_a;
    private ImageView mArrow;
    private Button dialogBtn, mVision, mMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        mVision = findViewById(R.id.vision);
        mMission = findViewById(R.id.misson);
        mArrow_home_a = findViewById(R.id.arrow_home_a);
        mArrow = findViewById(R.id.arrow_home);

        mVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialog(SubscriptionActivity.this);
            }
        });
        mMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionStatement();
            }
        });

        mArrow_home_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubscriptionActivity.this, MainActivity.class));
            }
        });
        mArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubscriptionActivity.this, MainActivity.class));
            }
        });

    }

    private void missionStatement() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionActivity.this);
        builder.setMessage("My life went by month after month, day after day\n" +
                "Then came the day that I moved away\n" +
                "There was nothing I could do, nothing I could say\n" +
                "As I left my skies quickly started turning gray\n" +
                "\n" +
                "Till one day he sent a messenger to me saying \nI will restore all i have sent locust to destroy for you.\n  From this year i will start exploiting you.Be ready for surprises.")
                .setTitle("Mission")
                .setIcon(R.drawable.app_icon)
                .setPositiveButton("Okay..", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//
//                        RTLToast.Config.getInstance()
//                                .setTextColor(Color.GREEN)
//                                .apply();
//                        RTLToast.custom(SubscriptionActivity.this, "Please do not forget"+"\n"+"to meditate on it.",
//                                getResources().getDrawable(R.drawable.bible_toast),
//
//                                Color.BLACK, Toast.LENGTH_LONG,true, true).show();
//                        RTLToast.Config.reset();

                    }

                })
                .create()
                .show();

    }


    private void OpenDialog(Activity activity) {


        AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionActivity.this);
        builder.setMessage("My life went by month after month, day after day\n" +
                "Then came the day that I moved away\n" +
                "There was nothing I could do, nothing I could say\n" +
                "As I left my skies quickly started turning gray\n" +
                "\n" +
                "Till one day he sent a messenger to me saying \nI will restore all i have sent locust to destroy for you.\n  From this year i will start exploiting you.Be ready for surprises.")
                .setTitle("VISION")
                .setIcon(R.drawable.app_icon)
                .setPositiveButton("Okay..", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                })
                .create()
                .show();

    }

    public void EndTimeMessage(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SubscriptionActivity.this);
        builder.setMessage("Pastor can say something about the end time. See what we are living now.")
                .setTitle("End Time message")
                .setIcon(R.drawable.app_icon)
                .setPositiveButton("Okay..", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .create()
                .show();

    }
}

