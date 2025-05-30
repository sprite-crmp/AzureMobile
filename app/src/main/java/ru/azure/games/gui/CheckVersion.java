package ru.azure.games.gui;

import android.app.Activity;

import okhttp3.internal.Version;
import ru.azure.launcher.fragment.DownloadFragment;
import ru.azure.games.BuildConfig;
import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;

import android.os.CountDownTimer;
import android.os.Environment;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.nvidia.devtech.NvEventQueueActivity;

import org.ini4j.Wini;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class CheckVersion {
    public Activity activity;

    public CheckVersion(Activity aactivity)
    {
        activity = aactivity;
    }

    public void check(int reytiz)
    {
        try {
            Wini v = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/data/script/server.ini"));
            String ver_cache = v.get("check", "ver_cache");
            int vercache = Integer.parseInt(ver_cache);
            String ver_apk = BuildConfig.VERSION_NAME;
            int verapk = Integer.parseInt(ver_apk);
            NvEventQueueActivity.getInstance().checkversion(vercache, verapk);
            v.store();
        } catch (IOException e) {
            int n = 0;
            Runtime.getRuntime().exit(n);
        }
    }
}