package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.launcher.fragment.DownloadFragment;
import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.view.animation.AnimationUtils;
import android.widget.*;

public class Ruda {
    public Activity activity;
    public FrameLayout ruda;
    public ImageView kamenruda, zhelesoruda, zolotoruda, ugolruda;

    public Ruda(Activity aactivity)
    {
        activity = aactivity;
        ruda = activity.findViewById(R.id.ruda);
        kamenruda = activity.findViewById(R.id.kamenruda);
        zhelesoruda = activity.findViewById(R.id.zhelesoruda);
        zolotoruda = activity.findViewById(R.id.zolotoruda);
        ugolruda = activity.findViewById(R.id.ugolruda);
        setListeners();

        kamenruda.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
        });
        zhelesoruda.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
        });
        zolotoruda.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
        });
        ugolruda.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            close();
        });
    }

    public void setListeners()
    {

    }

    public void show(int rudavar)
    {
        Utils.ShowLayout(ruda, true);
    }

    public void close() {
        Utils.HideLayout(ruda, true);
    }
}
