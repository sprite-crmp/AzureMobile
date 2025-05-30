package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.launcher.fragment.DownloadFragment;
import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.os.CountDownTimer;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.nvidia.devtech.NvEventQueueActivity;

import org.w3c.dom.Text;

public class LoadingBackground {
    public Activity activity;
    public FrameLayout loading;
    public ProgressBar progressLoading;
    public TextView barprocent;

    public LoadingBackground(Activity aactivity)
    {
        activity = aactivity;
        loading = activity.findViewById(R.id.loading);
        progressLoading = activity.findViewById(R.id.progressLoading);
        barprocent = activity.findViewById(R.id.barprocent);
        setListeners();
    }

    public void setListeners()
    {

    }

    public void show(int loadingbar)
    {
        Utils.ShowLayout(loading, true);
        int loadingbarvar = loadingbar*1000;
        new CountDownTimer(loadingbarvar, 1000)
        {
            @Override
            public void onTick(long l) {
                barprocent.setText("Осталось: " + l/1000 + " секунд");
                progressLoading.setMax(loadingbar);
                progressLoading.setProgress((int) (l/1000));
            }

            @Override
            public void onFinish() {
                close();
                NvEventQueueActivity.getInstance().sendLoadingClick(0, 0);
            }
        }.start();
    }

    public void close() {
        Utils.HideLayout(loading, true);
    }
}

