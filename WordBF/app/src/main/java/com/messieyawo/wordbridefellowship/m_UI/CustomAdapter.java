package com.messieyawo.wordbridefellowship.m_UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.messieyawo.wordbridefellowship.R;
import com.messieyawo.wordbridefellowship.m_Model.Spacecraft;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */
public class CustomAdapter extends BaseAdapter {
    private Context c;
    ArrayList<Spacecraft> spacecrafts;

    public CustomAdapter(Context c, ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int pos) {
        return spacecrafts.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
        }

       DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());


        TextView nameTxt=  convertView.findViewById(R.id.nameTxt);
        TextView propTxt= convertView.findViewById(R.id.propellantTxt);
        TextView descTxt=  convertView.findViewById(R.id.descTxt);
        TextView dateText = convertView.findViewById(R.id.dateTxt);

        final Spacecraft s= (Spacecraft) this.getItem(position);

        nameTxt.setText("Updated by: "+s.getName());
        propTxt.setText("Taken from: "+s.getPropellant());
        descTxt.setText(s.getDescription());
        dateText.setText("Published at: " +s.getDate() +"\n"+"Current date is: "+date);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OPEN DETAIL
              //  openDetailActivity(s.getName(),s.getDescription(),s.getPropellant());
            }
        });

        return convertView;
    }
    //OPEN DETAIL ACTIVITY
//    private void openDetailActivity(String...details)
//    {
//        Intent i=new Intent(c,DetailActivity.class);
//        i.putExtra("NAME_KEY",details[0]);
//        i.putExtra("DESC_KEY",details[1]);
//        i.putExtra("PROP_KEY",details[2]);
//
//        c.startActivity(i);
//    }
}














