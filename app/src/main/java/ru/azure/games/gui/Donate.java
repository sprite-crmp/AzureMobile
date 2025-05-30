package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Donate {
    public Activity activity;
    public FrameLayout donate_layout;
    public TextView buytext, baltext;
    public ConstraintLayout strone, strtwo, buyokno, buyoknoda, buyoknominus;
    public ImageView donate_close, btn_strone, btn_strtwo, donate_nick, donate_med, donate_lic, btn_da, btn_net, btn_yra, btn_ex;
    int product = 0;
    int money = 0;
    public Donate(Activity aactivity) {
        activity = aactivity;
        donate_layout = activity.findViewById(R.id.fragment_donate);
        donate_close = activity.findViewById(R.id.donate_close);
        strone = activity.findViewById(R.id.strone);
        strtwo = activity.findViewById(R.id.strtwo);
        btn_strone = activity.findViewById(R.id.btn_strone);
        btn_strtwo = activity.findViewById(R.id.btn_strtwo);
        donate_nick = activity.findViewById(R.id.donate_nick);
        donate_med = activity.findViewById(R.id.donate_med);
        donate_lic = activity.findViewById(R.id.donate_lic);
        buyokno = activity.findViewById(R.id.buyokno);
        btn_da = activity.findViewById(R.id.btn_da);
        btn_net = activity.findViewById(R.id.btn_net);
        buyoknoda = activity.findViewById(R.id.buyoknoda);
        btn_yra = activity.findViewById(R.id.btn_yra);
        buytext = activity.findViewById(R.id.buytext);
        baltext = activity.findViewById(R.id.baltext);
        buyoknominus = activity.findViewById(R.id.buyoknominus);
        btn_ex = activity.findViewById(R.id.btn_ex);
        setListeners();
        close();

        btn_da.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            if(product == 1)
            {
                if(money >= 100)
                {
                    Utils.ShowLayout(buyoknoda, true);
                    Utils.HideLayout(buyokno, true);
                    money -= 100;
                    baltext.setText(String.format("Баланс: %s Coins", money));
                    // отправление сигнала в мод (покупка лицензий за 100 Coins)
                }
                else
                {
                    Utils.ShowLayout(buyoknominus, true);
                    Utils.HideLayout(buyokno, true);
                }
            }
            if(product == 2)
            {
                if(money >= 50)
                {
                    Utils.ShowLayout(buyoknoda, true);
                    Utils.HideLayout(buyokno, true);
                    money -= 50;
                    baltext.setText(String.format("Баланс: %s Coins", money));
                    // отправление сигнала в мод (покупка мед карты за 50 Coins)
                }
                else
                {
                    Utils.ShowLayout(buyoknominus, true);
                    Utils.HideLayout(buyokno, true);
                }
            }
            if(product == 3)
            {
                if(money >= 150)
                {
                    Utils.ShowLayout(buyoknoda, true);
                    Utils.HideLayout(buyokno, true);
                    money -= 150;
                    baltext.setText(String.format("Баланс: %s Coins", money));
                    // отправление сигнала в мод (смена имени за 150 Coins)
                }
                else
                {
                    Utils.ShowLayout(buyoknominus, true);
                    Utils.HideLayout(buyokno, true);
                }
            }
        });

        btn_net.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.HideLayout(buyokno, true);
        });

        btn_yra.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.HideLayout(buyoknoda, true);

            product = 0;
        });

        btn_ex.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.HideLayout(buyoknominus, true);
        });


        btn_strone.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.ShowLayout(strone, true);
            Utils.HideLayout(strtwo, true);
        });

        btn_strtwo.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.ShowLayout(strtwo, true);
            Utils.HideLayout(strone, true);
        });

        donate_close.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            close();
        });

        donate_lic.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.ShowLayout(buyokno, true);

            String price = ("100 Coins?");
            buytext.setText(price);

            product = 1;
        });

        donate_med.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.ShowLayout(buyokno, true);

            String price = ("50 Coins?");
            buytext.setText(price);

            product = 2;
        });

        donate_nick.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            Utils.ShowLayout(buyokno, true);

            String price = ("150 Coins?");
            buytext.setText(price);

            product = 3;
        });
    }

    public void setListeners() {

    }

    public void show(int reytiz) {
        Utils.ShowLayout(donate_layout, true);
        Utils.HideLayout(strtwo, true);

        baltext.setText(String.format("Баланс: %s Coins", reytiz));

        money = reytiz;
    }

    public void close() {
        Utils.HideLayout(donate_layout, true);
    }
}
