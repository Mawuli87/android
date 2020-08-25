package com.messieyawo.unityassembly.Testimonials;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.messieyawo.unityassembly.DalyVerses.DailyVerses;

import java.util.ArrayList;
import java.util.List;

public class TestiMonialsFirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Testimonials>  testimonials = new ArrayList<>();

    public interface DataStatus{
          void DataIsLoaded(List<Testimonials> testimonials, List<String> keys);
         void DataInserted();
         void DataDeleted();
         void DataUpdated();
    }

    public TestiMonialsFirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Testimonials");
    }

    public void readTestimonials(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              testimonials.clear();
              List<String> keys = new ArrayList<>();
              for(DataSnapshot keyNode : dataSnapshot.getChildren()){

                  keys.add(keyNode.getKey());
                  Testimonials testimonials1 = keyNode.getValue(Testimonials.class);
                  testimonials.add(testimonials1);
              }
             dataStatus.DataIsLoaded(testimonials,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addTestimonials(Testimonials testimonials, final DataStatus dataStatus){
     String key = mReference.push().getKey();
     mReference.child(key).setValue(testimonials)
             .addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     dataStatus.DataInserted();
                 }
             });
    }

    public void updateTestimonials(String key, Testimonials testimonials, DataStatus dataStatus){
        mReference.child(key).setValue(testimonials)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataUpdated();
                    }
                });
           }
           public void deleteTestimonials(String key,DataStatus dataStatus){
              mReference.child(key).setValue(null)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                            dataStatus.DataDeleted();
                          }
                      });

           }
}
