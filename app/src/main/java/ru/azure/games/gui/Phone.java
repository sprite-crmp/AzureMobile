package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;

public class Phone {
    public Activity activity;
    public ConstraintLayout reytiz_layout;
    public ImageView starline, sber, btn_phone;
    public FrameLayout offtel;
    public TextView titulorg;
    public int reytizz = 11;

    public Phone(Activity aactivity) {
        activity = aactivity;
        reytiz_layout = activity.findViewById(R.id.phone);
        offtel = activity.findViewById(R.id.offtel);
        starline = activity.findViewById(R.id.starline);
        btn_phone = activity.findViewById(R.id.btn_phone);
        sber = activity.findViewById(R.id.sber);
        setListeners(aactivity);
        close();
    }

    public void setListeners(Activity aactivity) {
        activity = aactivity;
        starline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                NvEventQueueActivity.getInstance().sendReytizClick(reytizz,0);
            }
        });
        offtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                NvEventQueueActivity.getInstance().sendBtnphoneClick(reytizz,0);
            }
        });
        sber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                NvEventQueueActivity.getInstance().sendSberClick(reytizz,0);
            }
        });
    }

    public void show(int reytiz) {
        Utils.ShowLayout(reytiz_layout, true);
    }

    public void close() {
        Utils.HideLayout(reytiz_layout, true);
    }
}
