package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.launcher.fragment.DownloadFragment;
import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.nvidia.devtech.NvEventQueueActivity;

import org.w3c.dom.Text;

public class QuestDialog {
    public Activity activity;
    public FrameLayout questdialog;
    public TextView playerr, playerdialogg, timedialog;

    int count = 0;
    int isAnimating = 0;
    public CountDownTimer countDownTimer;

    public QuestDialog(Activity aactivity)
    {
        activity = aactivity;
        questdialog = activity.findViewById(R.id.questdialog);
        playerr = activity.findViewById(R.id.playerr);
        playerdialogg = activity.findViewById(R.id.playerdialogg);
        timedialog = activity.findViewById(R.id.timedialog);
        setListeners();
    }

    public void setListeners()
    {

    }

    public void show(String player, String playerdialog, int size, int time)
    {
        Utils.ShowLayout(questdialog, true);

        int timevar = time*1000;
        new CountDownTimer(timevar, 1000) {
            @Override
            public void onTick(long l) {
                timedialog.setText("" + l/1000);
            }

            @Override
            public void onFinish() {
                timedialog.setText("");
            }
        }.start();

        playerr.setText(player);
        playerdialogg.setText(playerdialog);
        playerdialogg.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

        /*if (countDownTimer != null)
        {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        playerdialogg.setText("");
        count = 0;
        countDownTimer = new CountDownTimer(999999999, 10) {
            @Override
            public void onTick(long l) {
                if (playerdialogg.getText(playerdialogg = playerdialog);
                {
                    countDownTimer.cancel();
                    countDownTimer.onFinish(); // сам не срабатывает при cancel :c
                }
                else
                {
                    playerdialogg.setText(playerdialogg.getText().toString()+playerdialog.charAt(count));
                    count++;
                }

            }
            @Override
            public void onFinish() {
                isAnimating = 1;
                countDownTimer.cancel();
                countDownTimer.onFinish(); // сам не срабатывает при cancel :c
            }
        }.start();*/
    }

    public void close() {
        Utils.HideLayout(questdialog, true);
    }
}


