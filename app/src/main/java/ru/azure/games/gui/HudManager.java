package ru.azure.games.gui;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nvidia.devtech.NvEventQueueActivity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;

public class HudManager {
    public Activity activity;
    public FrameLayout hud_layout, speedometr_matrp_edgar;
    public TextView hud_money, quest_btn_optional;
    public LinearLayout hud_layou, quest_layout, quest_btn_hide;

    public FrameLayout btn_quest, hud_main;

    public ConstraintLayout cameditgui, hudstata;

    public ImageView hud_weapon, pass, closehud, btn_phone, btn_shop;

    public ProgressBar progressHP, wantedhud;
    int q = 0;
    int reytizz = 0;

    public HudManager(Activity aactivity) {
        activity = aactivity;

        hud_main = activity.findViewById(R.id.hud_main);
        hud_layout = aactivity.findViewById(R.id.hud_main);
        hud_layou = aactivity.findViewById(R.id.hud_layout);

        progressHP = aactivity.findViewById(R.id.progresshp);
        wantedhud = aactivity.findViewById(R.id.wantedhud);

        hud_money = aactivity.findViewById(R.id.money_text);
        quest_btn_optional = aactivity.findViewById(R.id.quest_btn_optional);
        quest_btn_hide = aactivity.findViewById(R.id.quest_btn_hide);
        hud_weapon = aactivity.findViewById(R.id.weapon_melee_image);

        btn_quest = aactivity.findViewById(R.id.btn_quest);
        quest_layout = aactivity.findViewById(R.id.quest_layout);
        btn_shop = aactivity.findViewById(R.id.btn_shop);
        btn_phone = aactivity.findViewById(R.id.btn_phone);
        closehud = aactivity.findViewById(R.id.closehud);

        setListeners(aactivity);
        close();

        btn_shop.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendCommand("/donate".getBytes(StandardCharsets.UTF_8));

        });

        btn_phone.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendBtnphoneClick(reytizz,0);
        });

        Utils.HideLayout(hud_layout, true);
    }

    public void UpdateHudInfo(int health, int armour, int hunger, int weaponidweik, int ammo, int playerid, int money, int wanted) {
        progressHP.setProgress(health);

        DecimalFormat formatter = new DecimalFormat();
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        String s = formatter.format(money);
        hud_money.setText(String.format("%s₽", s));

        int id = activity.getResources().getIdentifier(new Formatter().format("weapon_%d", Integer.valueOf(weaponidweik)).toString(), "drawable", activity.getPackageName());
        hud_weapon.setImageResource(id);

        hud_weapon.setOnClickListener(v -> NvEventQueueActivity.getInstance().onWeaponChanged());

    }

    public void UpdateWanted(int wantedhudd)
    {
        wantedhud.setProgress(wantedhudd);
    }

    public void HideCameditgui(int clickhide)
    {
        if(clickhide == 0)
        {
            close();
        }
        else
        {
            ShowHud();
        }
    }

    public void setListeners(Activity aactivity) {
        activity = aactivity;
        closehud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
                HideChat();
                ShowCameditgui();
                HideHudStata();
                HideSpeedometr();
            }
        });
    }

    public void close() {
        Utils.HideLayout(hud_main, true);
    }

    public void ShowGps()
    {

    }

    public void HideGps()
    {

    }

    public void ShowX2()
    {

    }

    public void HideX2()
    {

    }

    public void ShowZona()
    {

    }

    public void HideZona()
    {

    }

    public void ShowRadar()
    {

    }

    public void HideRadar()
    {

    }

    public void ShowHud()
    {
        Utils.ShowLayout(hud_main, true);
        //Utils.ShowLayout(hud_micro, false);
    }
    public void ShowCameditgui()
    {
        Utils.ShowLayout(cameditgui, true);
        //Utils.ShowLayout(hud_micro, false);
    }

    public void HideHudStata()
    {
        Utils.HideLayout(hudstata, true);
    }

    public void HideSpeedometr()
    {
        Utils.HideLayout(speedometr_matrp_edgar, true);
    }

    public void HideHud()
    {
        Utils.HideLayout(hud_layout, true);
        //Utils.HideLayout(hud_micro, false);
    }

    public void HideChat()
    {
        NvEventQueueActivity.getInstance().sendCameditguiClick(reytizz,0);
    }

}