package ru.azure.launcher.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ru.azure.games.R;
import ru.azure.launcher.adapter.SliderStoriesAdapter;
import ru.azure.launcher.fragment.MainFragment;
import ru.azure.launcher.other.Lists;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class StoryActivity extends AppCompatActivity {

    //private RoundCornerProgressBar progressStory;
    private SliderStoriesAdapter sliderStoriesAdapter;
    private SliderView sliderView;
    private CountDownTimer countDownTimer;
    private long progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        this.sliderView = (SliderView) findViewById(R.id.sliderView);
        //this.progressStory = (RoundCornerProgressBar) findViewById(R.id.progressStory);
        ((ImageView) findViewById(R.id.btn_exit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoryActivity.this.ponclose(view);
            }
        });
        SliderStoriesAdapter sliderStoriesAdapter = new SliderStoriesAdapter(this);
        this.sliderStoriesAdapter = sliderStoriesAdapter;
        this.sliderView.setSliderAdapter(sliderStoriesAdapter);
        this.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        this.sliderStoriesAdapter.addItems(Lists.nlist);
        //this.sliderView.setCurrentPageListener(i -> startTimer());
        int intExtra = getIntent().getIntExtra("position", 0);
        this.sliderView.getSliderPager().setCurrentItem(intExtra, false);
        this.sliderView.getPagerIndicator().setSelection(intExtra);
        //this.progressStory.setProgress(0.0f);
        //startTimer();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // отключение авто-блокировки экрана
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); // скрытие навигации
    }

    public void ponclose(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
        new Handler().postDelayed(this::closeStory, 200);
    }

    public void closeStory() {
        //countDownTimer.cancel();
        countDownTimer = null;
        progress = 0;
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(R.anim.scale, R.anim.fade_out);
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            progress = 0;
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(5000, 1) {
            @Override
            public void onTick(long j) {
                progress = 5000-j;
                //progressStory.setProgress(progress);
            }
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onFinish() {
                if(sliderView.getCurrentPagePosition() + 1 == sliderStoriesAdapter.getCount())
                    closeStory();
                else
                sliderView.setCurrentPagePosition(sliderView.getCurrentPagePosition() + 1);
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //countDownTimer.cancel();
        progress = 0;
        countDownTimer = null;
    }
}