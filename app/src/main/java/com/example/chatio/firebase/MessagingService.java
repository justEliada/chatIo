package com.example.chatio.firebase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import javax.annotation.Nonnull;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@Nonnull String token){
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@Nonnull RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);

    }
}
