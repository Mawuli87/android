package com.messieyawo.unityassembly.DalyVerses;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<DailyVerses>  dailyVerses = new ArrayList<>();

    public interface DataStatus{
          void DataIsLoaded(List<DailyVerses> dailyVerses,List<String> keys);
         void DataInserted();
         void DataDeleted();
         void DataUpdated();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("WordBride");
    }

    public void readVerses(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              dailyVerses.clear();
              List<String> keys = new ArrayList<>();
              for(DataSnapshot keyNode : dataSnapshot.getChildren()){

                  keys.add(keyNode.getKey());
                  DailyVerses dailyVerses1 = keyNode.getValue(DailyVerses.class);
                  dailyVerses.add(dailyVerses1);
              }
             dataStatus.DataIsLoaded(dailyVerses,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addDailyVerses(DailyVerses dailyVerses, final DataStatus dataStatus){
     String key = mReference.push().getKey();
     mReference.child(key).setValue(dailyVerses)
             .addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     dataStatus.DataInserted();
                 }
             });
    }

    public void updateDailyVerses(String key, DailyVerses dailyVerses, DataStatus dataStatus){
        mReference.child(key).setValue(dailyVerses)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataUpdated();
                    }
                });
           }
           public void deleteDailyVerses(String key,DataStatus dataStatus){
              mReference.child(key).setValue(null)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                            dataStatus.DataDeleted();
                          }
                      });

           }
}
