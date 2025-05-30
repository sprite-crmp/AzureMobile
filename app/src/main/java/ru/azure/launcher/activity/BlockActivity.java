package ru.azure.launcher.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

import okhttp3.internal.Util;
import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;
import ru.azure.launcher.fragment.DownloadFragment;

public class BlockActivity extends AppCompatActivity {
    private static final String TAG = "BlockActivity";

    public ConstraintLayout block_screen_id;

    public TextView block_text;
    public ImageView btn_forum, btn_vk, btn_telegram, btn_discord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_screen);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_click);

        CheckVersion();

        block_screen_id = findViewById(R.id.block_screen_id);
        block_text = findViewById(R.id.block_text);
        btn_forum = findViewById(R.id.btn_forum);
        btn_vk = findViewById(R.id.btn_vk);
        btn_telegram = findViewById(R.id.btn_telegram);
        btn_discord = findViewById(R.id.btn_discord);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // отключение авто-блокировки экрана
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); // скрытие навигации

        btn_forum.setOnClickListener(v -> {
            v.startAnimation(animation);
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://forum.azuremobile.ru/index.php")));
        });

        btn_vk.setOnClickListener(v -> {
            v.startAnimation(animation);
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://vk.com/azuremobilerp")));
        });

        btn_telegram.setOnClickListener(v -> {
            v.startAnimation(animation);
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/azuremobilerp")));
        });

        btn_discord.setOnClickListener(v -> {
            v.startAnimation(animation);
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://discord.gg/aTn4ay6a")));
        });
    }

    public void CheckVersion()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("controlAPK").document("2");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    Boolean block = document.getBoolean("block");
                    if(Boolean.TRUE.equals(block))
                    {
                        String blockText = document.getString("blockText");
                        Utils.ShowLayout(block_screen_id, false);
                        block_text.setText(blockText);
                    }
                } else {
                    Log.e(TAG, "Документ не найден.");
                    Runtime.getRuntime().exit(0);
                }
            } else {
                Log.e(TAG, "Ошибка при чтении документа:", task.getException());
                Runtime.getRuntime().exit(0);
            }
        });

        Wini v = null;
        try {
            v = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/data/script/server.ini"));
        } catch (IOException e) {
            Log.e(TAG, "Файл server.ini не найден");
            Toast.makeText(getApplicationContext(), "Доступно обновление! Ожидайте установку.", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
        String ver_cache = v.get("check", "ver_cache");

        FirebaseFirestore db2 = FirebaseFirestore.getInstance();

        DocumentReference docRef2 = db2.collection("controlCACHE").document(ver_cache);
        docRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    Boolean block = document.getBoolean("block");
                    if(Boolean.TRUE.equals(block))
                    {
                        File gameDirectory = (new File("storage/emulated/0/Android/data/ru.azure.games/files/"));
                        ru.azure.launcher.other.Utils.delete(gameDirectory);
                        startActivity(new Intent(getApplicationContext(), DownloadFragment.class));
                        Toast.makeText(getApplicationContext(), "Доступно обновление! Ожидайте установку.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        super.onBackPressed();
                    }
                } else {
                    Log.e(TAG, "Документ не найден.");
                    Runtime.getRuntime().exit(0);
                }
            } else {
                Log.e(TAG, "Ошибка при чтении документа:", task.getException());
                Runtime.getRuntime().exit(0);
            }
        });
    }


}
