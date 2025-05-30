package ru.azure.games.gui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.nvidia.devtech.AudioHelper;
import com.nvidia.devtech.NvEventQueueActivity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import java.util.Formatter;

public class Speedometer {
    public Activity activity;
    public TextView mCarHP;
    public FrameLayout mStrela;
    public FrameLayout mStrela2;
    public ImageView mEngine;
    public TextView mFuel;
    public LinearLayout mInputLayout;
    public ImageView mLight;
    public ImageButton btn_engine, btn_light, btn_lock;
    public ImageView mLock;
    public TextView mMileage;
    public ConstraintLayout carmenu, mMenu;
    public ProgressBar hpbar, fuelbarr;
    public TextView mSpeed;
    public CircularProgressBar mSpeedLine;
    public ImageView povv, povv2;
    public AudioHelper sound;
    public int Pov, Pov2;
    public int click = 0;
    native void sendClick(int clickId);
    public int SpeedClose;


    public Speedometer(Activity activity){
        LinearLayout relativeLayout = activity.findViewById(R.id.speedometr_matrp_edgar);
        mInputLayout = relativeLayout;
        mSpeed = activity.findViewById(R.id.speed_text);
        mMenu = activity.findViewById(R.id.btn_speedmenu);
        mStrela = activity.findViewById(R.id.turn_left);
        mStrela2 = activity.findViewById(R.id.turn_right);
        fuelbarr = activity.findViewById(R.id.fuelbar);
        hpbar = activity.findViewById(R.id.hpbar);
        mMileage = activity.findViewById(R.id.mileage);
        mSpeedLine = activity.findViewById(R.id.speed_line);
        mEngine = activity.findViewById(R.id.in_engine);
        mLock = activity.findViewById(R.id.in_key);
        povv = activity.findViewById(R.id.in_left);
        povv2 = activity.findViewById(R.id.in_right);
        carmenu = activity.findViewById(R.id.carmenu);
        btn_lock = activity.findViewById(R.id.btn_lock);
        btn_light = activity.findViewById(R.id.btn_light);
        btn_engine = activity.findViewById(R.id.btn_engine);

        btn_lock.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendLockClick(0, 0);
        });

        btn_light.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendLightClick(0, 0);
        });

        btn_engine.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendEngineClick(0, 0);
        });

        mMenu.setOnClickListener( view -> {
            if(click == 0)
            {
                view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
                Utils.ShowLayout(carmenu,true);
                click = 1;
            }
            else
            {
                view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
                Utils.HideLayout(carmenu,true);
                click = 0;
            }
        });

        mStrela.setOnClickListener( view -> {
            if (Pov == 0)
            {
                view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
                sendClick(0);
                povv.setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.SRC_IN);
            }else{
                view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
                sendClick(0);
                povv.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_IN);
            }
        });
        mStrela2.setOnClickListener( view -> {
            if (Pov2 == 0)
            {
                view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
                sendClick(0);
                povv2.setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.SRC_IN);
            }else{
                view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
                sendClick(0);
                povv2.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_IN);
            }
        });
        Utils.HideLayout(relativeLayout, false);
    }

    public void UpdateSpeedInfo(int speed, int fuel, int hp, int mileage, int engine, int light, int belt, int lock){
        hp= (int) hp/10;
        mMileage.setText(new Formatter().format("%06d", Integer.valueOf(mileage)).toString());
        hpbar.setProgress(hp);
        mSpeed.setText(String.valueOf(speed));
        if(engine == 1)
            mEngine.setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.SRC_IN);
        else
            mEngine.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_IN);
        if(lock == 1)
            mLock.setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.SRC_IN);
        else
            mLock.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_IN);
    }

    public void UpdateFuel(int fuelbar) {
        fuelbarr.setProgress(fuelbar);
    }

    public void ShowSpeedCall()
    {
        SpeedClose = 0;
        System.err.println("ShowSpeedCall = " + SpeedClose);
    }

    public void HideSpeedCall()
    {
        SpeedClose = 1;
        System.err.println("HideSpeedCall = " + SpeedClose);
    }

    public void HideSpeed() {
        Utils.HideLayout(mInputLayout,false);
    }

    public void SpeedStop(int stopped) {
        NvEventQueueActivity.getInstance().SpeedStop(stopped);
        Utils.HideLayout(mInputLayout,false);
    }
    public void ShowSpeed()
    {
        System.err.println("ShowSpeed = " + SpeedClose);

        if(SpeedClose == 0)
        {
            Utils.ShowLayout(mInputLayout,false);
        }
        else {
            Utils.HideLayout(mInputLayout,false);
        }
    }
}