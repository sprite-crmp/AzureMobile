package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class Promouter{
    public Activity activity;
    public FrameLayout promouter;
    public ImageView listovka, skotch;
    public Promouter(Activity aactivity) {
        activity = aactivity;
        promouter = activity.findViewById(R.id.promouterr);
        listovka = activity.findViewById(R.id.listovka);
        skotch = activity.findViewById(R.id.skotch);
        setListeners();
        close();

        skotch.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
        });

        listovka.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            close();
        });
    }

    public void setListeners() {

    }

    public void show(int reytiz)
    {
        Utils.ShowLayout(promouter, true);
    }

    public void close()
    {
        Utils.HideLayout(promouter, true);
    }
}
