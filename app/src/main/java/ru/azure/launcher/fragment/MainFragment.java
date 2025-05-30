package ru.azure.launcher.fragment;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Environment;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ru.azure.games.R;
import ru.azure.launcher.activity.BlockActivity;
import ru.azure.launcher.activity.MainActivity;
import ru.azure.launcher.adapter.NewsAdapter;
import ru.azure.launcher.model.News;

import ru.azure.launcher.other.Lists;

import com.google.firebase.database.*;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.ini4j.Wini;

public class MainFragment extends Fragment {

    ConstraintLayout select_server_layout;

	int pon, lite;
	String name;
	String hex;
	int max;
	String online;
	int id;
	public Boolean CheckVersion = false;
    //
	String name_client;

	public ImageView settings;
	private CountDownTimer countDownTimer;
	private VideoView videoView;

	TextView textserver, name_launcher, serverinfo_onlinee;
	CircularProgressBar progressBar;
	ImageView server_background;

	RecyclerView recyclerNews;
	DatabaseReference databaseNews;
	NewsAdapter newsAdapter;
	ArrayList<News> nlist;

    @SuppressLint({"MissingInflatedId", "WrongConstant"})
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_main, container, false);
    	Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_click);

		select_server_layout = inflate.findViewById(R.id.select_server_layout);
		textserver = inflate.findViewById(R.id.serverinfo_name);
		progressBar = inflate.findViewById(R.id.serverinfo_online_bar);
		server_background = inflate.findViewById(R.id.server_background);
		name_launcher = inflate.findViewById(R.id.serverinfo_person_name);
		serverinfo_onlinee = inflate.findViewById(R.id.serverinfo_online);
		videoView = inflate.findViewById(R.id.video_menu_id);

		File appDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/ru.azure.games/files/");
		appDir.mkdirs();

		try{
			if(IsGameInstalled()) {
				Wini client = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/SAMP/settings.ini"));
				//
				Wini w = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/SAMP/localsettings.ini"));
				pon = Integer.parseInt(w.get("server", "server"));
				name = w.get("server", "name");
				hex = w.get("server", "color");
				max = Integer.parseInt(w.get("server", "maxonline"));
				online = w.get("server", "online");
				lite = Integer.parseInt(w.get("server", "online"));
				id = Integer.parseInt(w.get("server", "id"));
				//
				name_client = client.get("client", "name");
				w.store();
				//
				if(pon == 0){
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(8);
					inflate.findViewById(R.id.select_layout).setVisibility(0);
				}else{
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(0);
					inflate.findViewById(R.id.select_layout).setVisibility(8);
					name_launcher.setText(name_client);
					textserver.setText(name);
					progressBar.setProgressBarColor(Color.parseColor("#" + hex));
					progressBar.setProgressBarColorEnd(Color.parseColor("#" + hex));
					progressBar.setProgress(lite);
					progressBar.setProgressMax(max);
					server_background.setColorFilter(Color.parseColor("#" + hex), PorterDuff.Mode.SRC_ATOP);
				}
				client.store();


			}else{
				Wini w = new Wini(new File("storage/emulated/0/Android/data/ru.azure.games/files/SAMP/localsettings.ini"));
				pon = Integer.parseInt(w.get("server", "server"));
				name = w.get("server", "name");
				hex = w.get("server", "color");
				max = Integer.parseInt(w.get("server", "maxonline"));
				online = w.get("server", "online");
				lite = Integer.parseInt(w.get("server", "online"));
				id = Integer.parseInt(w.get("server", "id"));
				w.store();
				if(pon == 0){
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(8);
					inflate.findViewById(R.id.select_layout).setVisibility(0);
				}else{
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(0);
					inflate.findViewById(R.id.select_layout).setVisibility(8);
					name_launcher.setText("Установите игру");
					textserver.setText(name);
					progressBar.setProgressBarColor(Color.parseColor("#" + hex));
					progressBar.setProgressBarColorEnd(Color.parseColor("#" + hex));
					progressBar.setProgress(lite);
					progressBar.setProgressMax(max);
					server_background.setColorFilter(Color.parseColor("#" + hex), PorterDuff.Mode.SRC_ATOP);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Видео на фоне меню
		VideoView videoView = inflate.findViewById(R.id.video_menu_id);
		String uriPath = "android.resource://ru.azure.games/" + R.raw.video_menu;
		Uri videoUri = Uri.parse(uriPath);
		videoView.setVideoURI(videoUri);
		videoView.start();
		videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				videoView.start();
			}
		});

		select_server_layout.setOnClickListener(view -> {
			select_server_layout.startAnimation(animation);
			MainActivity.getMainActivity().replaceServers();
		});

		settings = inflate.findViewById(R.id.btn_settings);

		settings.setOnClickListener(v ->{
			v.startAnimation(animation);
			MainActivity.getMainActivity().replaceSettings();
		});

		inflate.findViewById(R.id.btn_play).setOnClickListener(v -> {
			v.startAnimation(animation);
			if(pon == 1)
			{
				countDownTimer = new CountDownTimer(200, 1) {
					@Override
					public void onTick(long l) {

					}

					@Override
					public void onFinish() {
						MainActivity.getMainActivity().onClickPlay();
					}
				}.start();
			}
			else
			{
				Toast.makeText(getContext(), "Выберите сервер!", Toast.LENGTH_SHORT).show();
			}

		});

		inflate.findViewById(R.id.btn_forum).setOnClickListener(v -> {
			v.startAnimation(animation);
			startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://forum.azuremobile.ru/index.php")));
		});

		inflate.findViewById(R.id.btn_vk).setOnClickListener(v -> {
			v.startAnimation(animation);
			startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://vk.com/azuremobilerp")));
		});

		inflate.findViewById(R.id.btn_telegram).setOnClickListener(v -> {
			v.startAnimation(animation);
			startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/azuremobilerp")));
		});

		inflate.findViewById(R.id.btn_discord).setOnClickListener(v -> {
			v.startAnimation(animation);
			startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://discord.gg/aTn4ay6a")));
		});

		recyclerNews = inflate.findViewById(R.id.story_recycler);
		recyclerNews.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		recyclerNews.setLayoutManager(layoutManager);
		
		this.nlist = Lists.nlist;
		newsAdapter = new NewsAdapter(getContext(), this.nlist);
		recyclerNews.setAdapter(newsAdapter);

        return inflate;
    }
	private boolean IsGameInstalled()
	{
		String CheckFile = "storage/emulated/0/Android/data/ru.azure.games/files/texdb/gta3.img";
		File file = new File(CheckFile);
		return file.exists();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (videoView != null && !videoView.isPlaying()) {
			String uriPath = "android.resource://ru.azure.games/" + R.raw.video_menu;
			Uri videoUri = Uri.parse(uriPath);
			videoView.setVideoURI(videoUri);
			videoView.start();
		}
	}
}