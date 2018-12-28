package com.example.mselvi.githubapi.activities;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mselvi.githubapi.modelLayer.Items;
import com.example.mselvi.githubapi.R;

public class RepoViewHolder extends RecyclerView.ViewHolder {

    Context context;
    CardView cv;
    TextView repoTitle;
    TextView userName;
    TextView description;
    TextView createdAt;
    TextView language;
    TextView stars;
    Button save;
    Button delete;
    ImageView repoImage;
    ImageView starImage;

    public RepoViewHolder(View itemView) {
        super(itemView);

        this.context = itemView.getContext();
        this.cv = itemView.findViewById(R.id.card_view);
        this.repoTitle = itemView.findViewById(R.id.full_name);
        this.description = itemView.findViewById(R.id.description);
        this.userName = itemView.findViewById(R.id.user_name);
        this.createdAt = itemView.findViewById(R.id.created_at);
        this.language = itemView.findViewById(R.id.language);
        this.stars = itemView.findViewById(R.id.stars);
        this.save = itemView.findViewById(R.id.save);
        this.delete = itemView.findViewById(R.id.delete);
        this.starImage = itemView.findViewById(R.id.star_image);
        this.repoImage = itemView.findViewById(R.id.user_photo);
    }

    public void configureWith(Items items) {

        createdAt.setText(items.getCreatedAt());
        description.setText(items.getDescription());
        language.setText(items.getLanguage());
        stars.setText(items.getStargazersCount().toString());
        repoTitle.setText(items.getFullName());
        userName.setText(items.getName());
        Glide.with(context).load(items.getOwner().getAvatarUrl()).into(repoImage);

    }
}
