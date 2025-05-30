package ru.azure.games.gui;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

public class Rudasklad {

    private FrameLayout rudasklad;
    private ProgressBar progressbarshahta;
    private ProgressBar progressbarshahtaa;
    private ImageView button_shahta;
    private ImageView button_shahtaa;
    private TextView shahtaskladtext;
    private TextView shahtaskladtextt;
    //private View oil_factory_exit_button;
    public CountDownTimer countDownTimer;

    native void onOilFactoryGameClose(boolean success);

    int oliwaterstate, oiloilstate;

    public void startCountdown() {
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(999999999, 10) {
            @Override
            public void onTick(long j) {
                if(oiloilstate >= 998 && oliwaterstate >= 998){
                    countDownTimer.cancel();
                    countDownTimer.onFinish(); // сам не срабатывает при cancel :c
                    return;
                }
                oliwaterstate --;
                oiloilstate --;

                if (oliwaterstate < 0)      oliwaterstate = 0;
                if (oliwaterstate > 1000)    oliwaterstate = 1000;
                if (oiloilstate < 0)        oiloilstate = 0;
                if (oiloilstate > 1000)      oiloilstate = 1000;

                UpdateWater(oliwaterstate);
                UpdateOil(oiloilstate);
            }
            @Override
            public void onFinish() {
                if(oiloilstate >= 998 && oliwaterstate >= 998){
                    Hide();
                }else{
                    Hide();
                }
            }
        }.start();
    }
    public void UpdateWater(int percent){
        String stroilwaterproc = String.format("%d%%", percent / 10);
        shahtaskladtext.setText(stroilwaterproc);
        progressbarshahta.setProgress(percent);
    }
    public void UpdateOil(int percent) {
        String stroiloilproc = String.format("%d%%", percent / 10);
        shahtaskladtextt.setText(stroiloilproc);
        progressbarshahtaa.setProgress(percent);
    }
    public Rudasklad(Activity activity)
    {
        rudasklad = activity.findViewById(R.id.rudasklad);
        progressbarshahta = activity.findViewById(R.id.progressbarshahta);
        progressbarshahtaa = activity.findViewById(R.id.progressbarshahtaa);
        button_shahta = activity.findViewById(R.id.button_shahta);
        button_shahtaa = activity.findViewById(R.id.button_shahtaa);
        shahtaskladtext = activity.findViewById(R.id.shahtaskladtext);
        shahtaskladtextt = activity.findViewById(R.id.shahtaskladtextt);

        button_shahta.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            oliwaterstate += 200;
            UpdateWater(oliwaterstate);
        });

        button_shahtaa.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            oiloilstate += 200;
            UpdateOil(oiloilstate);
        });

        Utils.HideLayout(rudasklad, false);
    }

    public void Show(int rudaskladvar) {
        oliwaterstate = 0;
        oiloilstate = 0;
        Utils.ShowLayout(rudasklad, true);
        startCountdown();
    }

    public void Hide()
    {
        Utils.HideLayout(rudasklad, true);
    }
}
