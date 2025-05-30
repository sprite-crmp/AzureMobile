package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.os.Debug;
import android.view.View;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Adobnova {
    public Activity activity;
    public ConstraintLayout reytiz_layout;
    public ImageView adclose;
    public TextView text_message;

    public Adobnova(Activity aactivity) {
        activity = aactivity;
        reytiz_layout = activity.findViewById(R.id.adobnova);
        adclose = activity.findViewById(R.id.adclose);
        text_message = activity.findViewById(R.id.text_message);
        setListeners();
        close();
    }

    public void setListeners() {
        adclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    public void show(String text) {
        Utils.ShowLayout(reytiz_layout, true);
        text_message.setText(text);
    }

    public void close() {
        Utils.HideLayout(reytiz_layout, true);
    }
}
