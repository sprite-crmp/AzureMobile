package ru.azure.launcher.activity;

import com.google.firebase.messaging.FirebaseMessagingService;

public class PushActivity extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }
}
