package ru.azure.launcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import ru.azure.games.R;
import ru.azure.launcher.model.News;

import ru.azure.games.R;
import ru.azure.launcher.other.Utils;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class SliderStoriesAdapter extends SliderViewAdapter<SliderStoriesAdapter.Holder> {

    private final Context context;
    private List<News> stories = new ArrayList();

    public SliderStoriesAdapter(Context context) {
        this.context = context;
    }

    public void addItems(List<News> list) {
        this.stories = list;
        notifyDataSetChanged();
    }

    public void deleteItem(int i) {
        this.stories.remove(i);
        notifyDataSetChanged();
    }

    public void addItem(News story) {
        this.stories.add(story);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_story, (ViewGroup) null));
    }

    public void onBindViewHolder(Holder holder, int i) {
        News story = this.stories.get(i);
        holder.title.setText(story.getTitle());
        holder.titleBig.setText(story.getTitleBig());
        Glide.with(this.context).load(story.getImageFullUrl()).into(holder.image);
        holder.more.setVisibility(story.getUrl().isEmpty() ? View.GONE : View.VISIBLE);
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(holder.hcontext, R.anim.button_click));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.hcontext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(story.getUrl())));
                    }
                }, 200);
            }
        });
    }

    @Override
    public int getCount() {
        return this.stories.size();
    }

    public class Holder extends SliderViewAdapter.ViewHolder {
        ImageView image;
        View itemView;
        CardView more;
        TextView title, titleBig;
        Context hcontext;

        public Holder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.story_caption_top);
            this.titleBig = (TextView) view.findViewById(R.id.story_caption_bottom);
            this.image = (ImageView) view.findViewById(R.id.story_picture_back);
            this.more = (CardView) view.findViewById(R.id.story_details);
            this.hcontext = context;
            this.itemView = view;
        }
    }
}
