package com.messieyawo.unityassembly.EvntManager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.messieyawo.unityassembly.DalyVerses.DailyVerses;
import com.messieyawo.unityassembly.DalyVerses.FirebaseDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EventFirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<EventObject>  eventObjects = new ArrayList<>();

    public interface DataStatus{
          void EventDataIsLoaded(List<EventObject> eventObjects, List<String> keys);
         void EventDataInserted();
         void EventDataDeleted();
         void EventDataUpdated();
    }

    public EventFirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Event");
    }

    public void readEvent(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              eventObjects.clear();
              List<String> keys = new ArrayList<>();
              for(DataSnapshot keyNode : dataSnapshot.getChildren()){

                  keys.add(keyNode.getKey());
                  EventObject eventObject1 = keyNode.getValue(EventObject.class);
                  eventObjects.add(eventObject1);
              }
             dataStatus.EventDataIsLoaded(eventObjects,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addEventMethod(EventObject eventObject, final DataStatus dataStatus){
     String key = mReference.push().getKey();
     mReference.child(key).setValue(eventObject)
             .addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     dataStatus.EventDataInserted();
                 }
             });
    }

    public void updateEventMethod(String key,EventObject eventObject, DataStatus dataStatus){
        mReference.child(key).setValue(eventObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.EventDataUpdated();
                    }
                });
           }
           public void deleteEventMethod(String key,DataStatus dataStatus){
              mReference.child(key).setValue(null)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                            dataStatus.EventDataDeleted();
                          }
                      });

           }

    public void updateEvent(String key, EventObject eventObject, EventFirebaseDatabaseHelper.DataStatus dataStatus){
        mReference.child(key).setValue(eventObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.EventDataUpdated();
                    }
                });
    }
    public void deleteEvent(String key, EventFirebaseDatabaseHelper.DataStatus dataStatus){
        mReference.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.EventDataDeleted();
                    }
                });

    }




}
