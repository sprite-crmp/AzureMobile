package ru.azure.launcher.activity;

import android.app.Application;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessaging;

public class PushService extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }

            String token = task.getResult();
        });
    }
}
