package ru.azure.games.gui;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import java.io.UnsupportedEncodingException;

public class Notification {
    public Activity aactivity;

    public ConstraintLayout constraintLayout;

    public View view;

    public LinearLayout main;

    public FrameLayout button;

    public ProgressBar mProgressBar;

    public ImageView ruble;

    public TextView noty_btn_text_1, noty_text;
    public TextView text_notif;

    public static int type, duration;

    public static String text, actionforBtn, textBtn;

    public CountDownTimer countDownTimer;

    public Notification (Activity activity) {
        aactivity = activity;
        constraintLayout = activity.findViewById(R.id.constt);
        button = activity.findViewById(R.id.noty_btn_1);
        noty_text = activity.findViewById(R.id.noty_text);
        ruble = activity.findViewById(R.id.noty_bg_image);
        text_notif = activity.findViewById(R.id.noty_text);
        noty_btn_text_1 = activity.findViewById(R.id.noty_btn_text_1);
        mProgressBar = activity.findViewById(R.id.noty_progress);

        button.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            HideNotification();
        });
        Utils.HideLayout(constraintLayout, false);
        noty_text.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            HideNotification();
        });
        Utils.HideLayout(constraintLayout, false);
    }

    public void ShowNotification (int type, String text, int duration, String actionforBtn, String textBtn) {
        Utils.HideLayout(constraintLayout, false);
        clearData();

        this.type = type;
        this.text = text;
        this.duration = duration;
        this.actionforBtn = actionforBtn;
        this.textBtn = textBtn;

        this.text_notif.setText(this.text);

        this.mProgressBar.setMax(this.duration * 1000);
        this.mProgressBar.setProgress(this.duration * 1000);

        //mProgressBar.setProgressDrawable(ContextCompat.getDrawable(aactivity, R.drawable.background_br_notification_orange));
        ruble.setColorFilter(Color.parseColor("#6A00FF"));

        switch (this.type) {
            case 0:
                button.setVisibility(View.GONE);
                break;
            case 1:
                button.setVisibility(View.GONE);
                break;
            case 2:
                button.setVisibility(View.GONE);
                break;
            case 3:
                button.setVisibility(View.GONE);
                break;
            case 4:
                button.setVisibility(View.VISIBLE);
                break;
            case 5:
                button.setVisibility(View.VISIBLE);
                break;
        }

        if (this.type == 5 || this.type == 4) {
            noty_btn_text_1.setText(textBtn);
            button.setOnClickListener(view -> {
                view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                try {
                    NvEventQueueActivity.getInstance().sendCommand((Notification.actionforBtn).getBytes("windows-1251"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                HideNotification();
            });
        }
        startCountdown();
        Utils.ShowLayout(constraintLayout, true);
    }

    private void clearData() {
        this.text = "";
        this.type = -1;
        this.duration = -1;
        this.actionforBtn = "";
        this.textBtn = "";
    }

    public void startCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(mProgressBar.getProgress(), 1) {
            @Override
            public void onTick(long j) {
                mProgressBar.setProgress((int) j);
            }
            @Override
            public void onFinish() {
                HideNotification();
            }
        }.start();
    }
    public void HideNotification () {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        constraintLayout.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.popup_hide_notification));
        constraintLayout.setVisibility(View.GONE);
    }
}
