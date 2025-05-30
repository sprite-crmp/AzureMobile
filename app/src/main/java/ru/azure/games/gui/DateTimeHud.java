package ru.azure.games.gui;
import android.app.Activity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DecimalFormat;

public class DateTimeHud {
    public Activity activity;
    public FrameLayout hud_main;
    public TextView datetimee;
    public ImageView datetimeImage;

    public DateTimeHud(Activity aactivity)
    {
        activity = aactivity;
        hud_main = activity.findViewById(R.id.hud_main);
        datetimeImage = activity.findViewById(R.id.datetimeImage);
        datetimee = activity.findViewById(R.id.datetime);
        setListeners();
    }

    public void setListeners() {

    }

    public void show(String datetime, int action)
    {
        if(action == 1)
        {
            datetimee.setText(datetime);
            Utils.ShowLayout(datetimeImage, false);
            Utils.ShowLayout(datetimee, false);
        }
        if(action == 2)
        {
            Utils.HideLayout(datetimeImage, false);
            Utils.HideLayout(datetimee, false);
        }
    }

    public void opentime(int action)
    {
        if(action == 0)
        {
            Utils.ShowLayout(datetimeImage, true);
            Utils.ShowLayout(datetimee, true);
        }
        if(action == 1)
        {
            Utils.HideLayout(datetimeImage, false);
            Utils.HideLayout(datetimee, false);
        }
    }

    public void close() {
        Utils.HideLayout(datetimeImage, false);
        Utils.HideLayout(datetimee, false);
    }
}