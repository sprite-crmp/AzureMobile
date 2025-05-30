package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Sberreg {
    public Activity activity;
    public FrameLayout sberclose, layout;
    public Sberreg(Activity aactivity) {
        activity = aactivity;
        layout = activity.findViewById(R.id.sberreg);
        sberclose = activity.findViewById(R.id.sberclose);
        setListeners();
        close();
    }

    public void setListeners() {
        sberclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    public void show(int reytiz) {
        Utils.ShowLayout(layout, true);
    }

    public void close() {
        Utils.HideLayout(layout, true);
    }
}
