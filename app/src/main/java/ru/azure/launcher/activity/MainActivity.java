package ru.azure.launcher.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.animation.AnimationUtils;
import android.view.animation.Animation;

import ru.azure.games.BuildConfig;
import ru.azure.games.R;
import ru.azure.games.gui.util.Utils;
import ru.azure.launcher.fragment.DownloadFragment;
import ru.azure.launcher.fragment.MainFragment;
import ru.azure.launcher.fragment.ServerSelectFragment;
import ru.azure.launcher.fragment.SettingsFragment;
import ru.azure.launcher.fragment.SplashFragment;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import android.widget.VideoView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.ini4j.Wini;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    public SplashFragment splashFragment;
    public ServerSelectFragment serverSelectFragment;
    public SettingsFragment settingsFragment;
    public MainFragment mainFragment;
    public DownloadFragment downloadFragment;
    public FrameLayout front_ui_layout;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public int Auth = 28540117;
    public int AuthCheck = 7;
    private String courseName;
    public String text3, text4, text6;
    public VideoView videoView;
    public
    Context pone;
    Activity activity;
    Timer t;
    File file = new File("storage/emulated/0/Android/data/ru.azure.games/files/SAMP/localsettings.ini");

    private static final String TAG = "BlockActivity";

    public ConstraintLayout block_screen_id, fragment_download;

    public TextView block_text;
    public ImageView btn_forum, btn_vk, btn_telegram, btn_discord;
    public Boolean isServerIni = false;
    public Boolean validAPK = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

		Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_click);

        instance = this;

        front_ui_layout = (FrameLayout) findViewById(R.id.front_ui_layout);
        fragment_download = findViewById(R.id.fragment_download);

        block_screen_id = findViewById(R.id.block_screen_id);
        block_text = findViewById(R.id.block_text);
        btn_forum = findViewById(R.id.btn_forum);
        btn_vk = findViewById(R.id.btn_vk);
        btn_telegram = findViewById(R.id.btn_telegram);
        btn_discord = findViewById(R.id.btn_discord);

		splashFragment = new SplashFragment();
        serverSelectFragment = new ServerSelectFragment();
        settingsFragment = new SettingsFragment();
        mainFragment = new MainFragment();
        downloadFragment = new DownloadFragment();
        t = new Timer();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // отключение авто-блокировки экрана
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); // скрытие навигации


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
                startTimer();
                mHandler.postDelayed(new AnimClose(), 7800);
                Toast.makeText(getApplicationContext(), "Загрузка!", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NOTIFICATION_POLICY, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
            } else {
                startTimerSpeed();
                mHandler.postDelayed(new AnimClose(), 1800);
            }
        } else {
            startTimerSpeed();
            mHandler.postDelayed(new AnimClose(), 1800);
        }
        if (PermissionUtils.hasPermissions(this)) {
            try {
                //startTimer(); mHandler.postDelayed(new AnimClose(), 7800);
                overridePendingTransition(0, 0);
            } catch (Exception e) {

            }
        } else if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, 101);
        }

        boolean externalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        CheckVersion();

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

        File phone_info = new File("storage/emulated/0/AzureGames/files/phone_info.ini");

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        try {
            File theDir = new File("storage/emulated/0/AzureGames/files");
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            if (phone_info.exists()) {
                FileWriter writer = new FileWriter (phone_info);
                writer.write("[phone_info]\n");
                writer.write("IMEI = " + deviceId);
                writer.close();
            }else {
                phone_info.createNewFile();
                FileWriter writer = new FileWriter (phone_info);
                writer.write("[phone_info]\n");
                writer.write("IMEI = " + deviceId);
                writer.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

	    replaceFragment(splashFragment);
    }


    public class AnimClose implements Runnable {
        public AnimClose() {
        }

        public final void run() {
            SplashFragment.getSplash().j();
        }
    }

    public void CheckVersion() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("controlAPK").document(BuildConfig.VERSION_NAME);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    Boolean block = document.getBoolean("block");
                    if (Boolean.TRUE.equals(block)) {
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
            isServerIni = true;
        } catch (IOException e) {
            Log.e(TAG, "Файл server.ini не найден");
            File gameDirectory = (new File("storage/emulated/0/Android/data/ru.azure.games/files/"));
            ru.azure.launcher.other.Utils.delete(gameDirectory);
            gameDirectory.mkdirs();
            Toast.makeText(getApplicationContext(), "Доступно обновление! Для установки файлов игры нажмите \"Играть\".", Toast.LENGTH_LONG).show();
        }
        if(isServerIni)
        {
            String ver_cache = v.get("check", "ver_cache");

            FirebaseFirestore db2 = FirebaseFirestore.getInstance();

            DocumentReference docRef2 = db2.collection("controlCACHE").document(ver_cache);
            docRef2.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Boolean block = document.getBoolean("block");
                        if (Boolean.TRUE.equals(block)) {
                            File gameDirectory = (new File("storage/emulated/0/Android/data/ru.azure.games/files/"));
                            ru.azure.launcher.other.Utils.delete(gameDirectory);
                            gameDirectory.mkdirs();

                            Toast.makeText(getApplicationContext(), "Доступно обновление! Для установки файлов игры нажмите \"Играть\".", Toast.LENGTH_LONG).show();
                        }
                        else {

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

    public void Auhtch(String text, String text2, String text5)
    {
            text3 = text;
            text4 = text2;
            text6 = text5;
            if(text == null){
            }else {System.out.println(text);}
            if(text2 == null){
            }else {System.out.println(text2);}
            if(text5 == null){
            }else {System.out.println(text5);}
    }

    public static MainActivity getMainActivity() {
        return instance;
    }
	
	public void onClickPlay() {
        if(IsGameInstalled()) {
            t.cancel();
            startActivity(new Intent(getApplicationContext(), ru.azure.games.core.GTASA.class));
		} else {
            t.cancel();
            if(DownloadFragment.nick == null) {
                Toast.makeText(getApplicationContext(), "Установите ник!", Toast.LENGTH_SHORT).show();
                replaceSettings();
            }else{
                replaceFragment(downloadFragment);
            }
		}
    }

    public void onUpdateCache() {
        replaceFragment(downloadFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.mtrl_bottom_sheet_slide_out);
        beginTransaction.replace(R.id.front_ui_layout, fragment);
        beginTransaction.
                commit();
    }
    public void replaceServers() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.fade_out);
        beginTransaction.replace(R.id.front_ui_layout, serverSelectFragment);
        beginTransaction.commit();
    }
    public void replaceSettings(){
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.fade_out);
        beginTransaction.replace(R.id.front_ui_layout, settingsFragment);
        beginTransaction.commit();
    }
    public void closeServers() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.fade_in, R.anim.slide_out_bottom);
        beginTransaction.replace(R.id.front_ui_layout, mainFragment);
        beginTransaction.commit();
    }

	public boolean isRecordAudioPermissionGranted() {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.RECORD_AUDIO") == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, 2);
        return false;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        return false;
    }
    public void onRequestPermissionsResultBr(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            replaceFragment(mainFragment);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && Build.VERSION.SDK_INT >= 30 && !PermissionUtils.hasPermissions(this)) {
            Toast.makeText(getApplicationContext(), "Выдайте разрешение!", Toast.LENGTH_SHORT).show();
        } else replaceFragment(mainFragment);
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	private boolean IsGameInstalled()
    {
        String CheckFile = "storage/emulated/0/Android/data/ru.azure.games/files/texdb/gta3.img";
        File file = new File(CheckFile);
        return file.exists();
    }

	private void startTimer()
    {
        t.schedule(new TimerTask(){
            @Override
            public void run() {
                try {
                    //Connect();
                    File theDir = new File(getExternalFilesDir(null), "SAMP");
                    if (!theDir.exists()){
                       theDir.mkdirs();
                    }
                    if (file.exists()) {
                        //file.delete();
                    }else {
                        file.createNewFile();
                        FileWriter writer = new FileWriter (file);
                        writer.write("[server]\n");
                        writer.write("server = 0\n");
                        writer.write("name = 0\n");
                        writer.write("color = 0\n");
                        writer.write("maxonline = 0\n");
                        writer.write("online = 0\n");
                        writer.write("host = 0\n");
                        writer.write("port = 0\n");
                        writer.write("id = 0\n");
                        writer.close();
                        System.out.println("File is created!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                replaceFragment(mainFragment);
            }
        }, 8000L);
    }
    private void startTimerSpeed()
    {
        t.schedule(new TimerTask(){
            @Override
            public void run() {
                try {
                    File theDir = new File(getExternalFilesDir(null), "SAMP");
                    if (!theDir.exists()){
                      theDir.mkdirs();
                    }
                    if (file.exists()) {
                        //file.delete();
                    }else {
                        file.createNewFile();
                        FileWriter writer = new FileWriter (file);
                        writer.write("[server]\n");
                        writer.write("server = 0\n");
                        writer.write("name = 0\n");
                        writer.write("color = 0\n");
                        writer.write("maxonline = 0\n");
                        writer.write("online = 0\n");
                        writer.write("host = 0\n");
                        writer.write("port = 0\n");
                        writer.write("id = 0\n");
                        writer.close();
                        System.out.println("File is created!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                replaceFragment(mainFragment);
            }
        }, 2000L);
    }

    public static class PermissionUtils {
        public static boolean hasPermissions(Context context) {
            if (Build.VERSION.SDK_INT >= 30) {
                return Environment.isExternalStorageManager();
            }
            if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                return true;
            }
            return false;
        }

        public static void requestPermissions(Activity activity, int requestCode) {
            if (Build.VERSION.SDK_INT >= 30) {
                try {
                    Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", new Object[]{activity.getPackageName()})));
                    activity.startActivityForResult(intent, requestCode);
                } catch (Exception e) {
                    Intent intent2 = new Intent();
                    intent2.setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                    activity.startActivityForResult(intent2, requestCode);
                }
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, requestCode);
            }
        }
    }
    public void PermissionPon(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
            } else {
                Toast.makeText(this, "Выдайте разрешение, иначе вы не сможите пользоваться лаунчером", Toast.LENGTH_SHORT).show();
                startTimer();
            }
        } else startTimer();
    }


	
	public void onDestroy() {
        super.onDestroy();
        
    }

    public void onRestart() {
        super.onRestart();
        
    }
	
	public boolean checkValidNick(){
		EditText nick = (EditText) findViewById(R.id.account_text);
		if(nick.getText().toString().isEmpty()) {
			Toast.makeText(this, "Введите ник", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!(nick.getText().toString().contains("_"))){
			Toast.makeText(this, "Ник должен содержать символ \"_\"", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(nick.getText().toString().length() < 4){
			Toast.makeText(this, "Длина ника должна быть не менее 4 символов", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
} 