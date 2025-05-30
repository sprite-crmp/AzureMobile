package ru.azure.launcher.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.hzy.libp7zip.P7ZipApi;

import org.ini4j.Wini;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Formatter;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ru.azure.launcher.activity.MainActivity;
import ru.azure.launcher.other.Utils;
import ru.azure.games.R;
import ru.azure.games.core.GTASA;

public class DownloadFragment extends Fragment {
    public Activity activity;
    public File folder;
    public static String nick = null;
    public ImageView download_render;
    public TextView download_guide_text, faq_text;
    public CountDownTimer countDownTimer;
    public int idText = 0;
    public int idImage = 0;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final String[] TextInfo = {
            "Azure Mobile раньше назывался Wolf Russia",
            "Команда проекта уделяет больше внимания именно фракциям",
            "У нас есть работы, которых нет на других проектах",
            "Все пожертвования проекту сразу уходят в его развитие",
            "Команда Azure Mobile состоит лишь из 4 человек",
            "Вы можете подать свое предложение по улучшению игры на форуме",
            "Команда проекта умеет готовить вкусные блинчики",
            "Пока вы ждете загрузку, мы сажаем и поливаем растения в Батырево",
            "Сервер и город Киров в игре добавлен в честь основателя",
            "Если в тесто добавить разработчиков, основателя и администрацию, то получится вкусный пирог"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_download, container, false);
        download_render = inflate.findViewById(R.id.download_render);
        download_guide_text = inflate.findViewById(R.id.download_guide_text);
        faq_text = inflate.findViewById(R.id.faq_text);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_click);

        startDownload(inflate);

        return inflate;
    }

    public void startDownload(View inflate) {
        folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String url = "http://wh24696.web3.maze-tech.ru/Files/cache.zip";
        startAnim();
        PRDownloader.download(url, folder.getPath(), "cache.zip")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(getContext(), "Установка отменена", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        float floor = ((float) Math.floor((double) (progress.currentBytes / 20.0f))) * 20.0f;
                        ProgressBar progressbar = (ProgressBar) inflate.findViewById(R.id.download_progressbar);

                        TextView textloading = (TextView) inflate.findViewById(R.id.download_text);

                        textloading.setText(new Formatter().format("Скачивание архивов %.2f%s", new Object[]{Float.valueOf((int)progressPercent), "%"}).toString());//(new Formatter().format("Скачивание архивов: %s.0f %", progressPercent).toString());
                        progressbar.setProgress((int) progressPercent);

                        if((int) progressPercent == 100)
                        {
                            textloading.setText("Распаковка архивов...");
                        }
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        TextView textloading = (TextView) inflate.findViewById(R.id.download_text);

                        textloading.setText("Распаковка архивов...");

                        UnZipCache();

                        Toast.makeText(getContext(), "Если у вас мало места, игра может не установится", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(getContext(), "Произошла ошибка начните заново установку", Toast.LENGTH_SHORT).show();
                        Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cache.zip"));
                        Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cache.zip.temp"));
                        startActivity(new Intent(getContext(), MainActivity.class));
                    }
                });
    }

    public void UnZipCache(){
        String mInputFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cache.zip";
        String mOutputPath = "storage/emulated/0/Android/data/ru.azure.games/files/";

        new Thread(() -> {
            try {
                unzip(mInputFilePath, mOutputPath);
                OnLoaded();
            } catch (IOException e) {
                Toast.makeText(getContext(), "Ошибка распаковки архива", Toast.LENGTH_LONG).show();
                Log.e("UnzipError", "Ошибка распаковки архива", e);
            }
        }).start();
    }

    public static void unzip(String sourceFilePath, String destinationDirectory) throws IOException {
        File destDir = new File(destinationDirectory);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFilePath));
             ZipInputStream zis = new ZipInputStream(bis)) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(destDir, entry.getName());

                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parentDir = file.getParentFile();
                    if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                    try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                        while ((bytesRead = zis.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }

    /*public class Un7z extends AsyncTask<String, Void, Void> { // в android/data не ворк
        public String str;
        @Override
        protected void onPreExecute() {

        }

        public Void doInBackground(String... strings) {
            String str = strings[0];
            P7ZipApi.executeCommand(String.format("7z x '%s' '-o%s' -aoa", str, strings[1]));
            return null;
        }

        public void onPostExecute(Void aVoid) {
            OnLoaded();
        }
    }*/

    public void OnLoaded() {
        Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cache.zip"));
        Utils.delete(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/cache.zip.temp"));
        try {
            Wini w = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/SAMP/settings.ini"));
            w.put("client", "name", nick);
            w.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(getContext(), GTASA.class));
    }

    public class UpdateImage implements Runnable {
        public UpdateImage() {
        }

        public final void run() {
           // if (!getActivity().isDestroyed()) {
                ImageView imageView = download_render;
                imageView.setTranslationX((float) imageView.getWidth());
                download_render.setAlpha(0.0f);
                ImageView imageView2 = download_render;
                Resources resources = getResources();
                StringBuilder v9 = new StringBuilder("render_pic_");
                v9.append(idImage);
                imageView2.setImageResource(resources.getIdentifier(v9.toString(), "drawable", getContext().getPackageName()));
                download_render.animate().translationX(0.0f).alpha(1.0f).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
            //}
        }
    }

    public final void startAnim() {
            mHandler.removeCallbacksAndMessages((Object) null);
            this.idText = new Random().nextInt(this.TextInfo.length);
            idImage = 0;
            ImageView imageView = this.download_render;
            Resources resources = getResources();
            StringBuilder v9 = new StringBuilder("render_pic_");
            v9.append(idImage);
            imageView.setImageResource(resources.getIdentifier(v9.toString(), "drawable", getContext().getPackageName()));
            this.download_guide_text.setText(this.TextInfo[this.idText]);
            this.download_guide_text.setOnClickListener(new ponClick());
            mHandler.postDelayed(new pon(), 5000);
    }
    public class pon implements Runnable {
        public pon() {
        }

        public final void run() {
            Update();
        }
    }

    public class ponClick implements View.OnClickListener {
        public ponClick() {
        }

        public final void onClick(View view) {
            Update();
        }
    }
    public final void Update() {
        mHandler.removeCallbacksAndMessages((Object) null);
        int i10 = this.idText + 1;
        this.idText = i10;
        int i11 = idImage + 1;
        idImage = i11;
        if (i10 >= this.TextInfo.length) {
            this.idText = 0;
        }
        if (i11 >= 6) {
            idImage = 0;
        }
        download_render.clearAnimation();
        download_render.animate().translationX((float) (-this.download_render.getWidth())).alpha(0.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).withEndAction(new UpdateImage()).start();
        download_guide_text.clearAnimation();
        download_guide_text.animate().translationX((float) (-this.download_guide_text.getWidth())).alpha(0.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).withEndAction(new textEdit()).start();
        mHandler.postDelayed(new pon(), 5000);
    }
    public class textEdit implements Runnable {
        public textEdit() {
        }

        public final void run() {
            TextView textView = download_guide_text;
            textView.setTranslationX((float) textView.getWidth());
            download_guide_text.setAlpha(0.0f);
            download_guide_text.setText(TextInfo[idText]);
            download_guide_text.animate().translationX(0.0f).alpha(1.0f).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
        }
    }
}