package ru.azure.games.gui;

import android.app.Activity;

import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import android.view.View;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Formatter;

public class HudStata {
    public Activity activity;
    public ConstraintLayout hudstata;
    public TextView lvlhud;

    public HudStata(Activity aactivity)
    {
        activity = aactivity;
        hudstata = activity.findViewById(R.id.hudstata);
        lvlhud = activity.findViewById(R.id.lvlhud);
        setListeners();
    }

    public void setListeners() {

    }

    public void show(int lvlhudd)
    {
        DecimalFormat formatter = new DecimalFormat();
        String s = formatter.format(lvlhudd);
        lvlhud.setText(s);
        Utils.ShowLayout(hudstata, true);
    }

    public void close() {
        Utils.HideLayout(hudstata, true);
    }
}
