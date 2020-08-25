package com.messieyawo.wordbridefellowship.Service;


import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class myFirebaseInstanceService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        sendNewTokenToSerVer(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendNewTokenToSerVer(String token) {


    }
}
