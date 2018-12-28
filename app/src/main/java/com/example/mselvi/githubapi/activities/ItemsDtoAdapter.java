package com.example.mselvi.githubapi.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mselvi.githubapi.helpers.RepositoryRowView;
import com.example.mselvi.githubapi.modelLayer.database.ItemsDto;
import com.example.mselvi.githubapi.R;
import com.example.mselvi.githubapi.modelLayer.database.ItemsListModel;
import com.example.mselvi.githubapi.modelLayer.enums.Source;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;

public class ItemsDtoAdapter extends RecyclerView.Adapter<ItemsDtoAdapter.ItemsDtoViewHolder> {

    private List<ItemsDto> itemsDtoList;
    private ItemsListModel viewModel;
    private final SavedReposPresenter savedReposPresenter;

    public ItemsDtoAdapter(SavedReposPresenter savedReposPresenter) {
        this.savedReposPresenter = savedReposPresenter;
    }

    @NonNull
    @Override
    public ItemsDtoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemDtoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_cell, parent, false);
        final ItemsDtoViewHolder itemsDtoViewHolder = new ItemsDtoViewHolder(itemDtoView);
        viewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ItemsListModel.class);

        return itemsDtoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsDtoViewHolder holder, int position) {
        savedReposPresenter.onBindRepositoryRowViewAtPosition(holder, position);
        ItemsDto iDto = savedReposPresenter.itemsDtoList.get(position);

        holder.itemView.setTag(savedReposPresenter.itemsDtoList);
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(iDto.getHtmlUrl()));
                v.getContext().startActivity(browserIntent);
                Log.v("Repository url:", iDto.getHtmlUrl());
            }
        });
        holder.delete.setVisibility(View.INVISIBLE);
        holder.repoImage.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return savedReposPresenter.getRepositoriesRowsCount();
    }

    public void addItems(List<ItemsDto> itemsDtoList, ItemsListModel viewModel) {
        this.viewModel = viewModel;
        this.itemsDtoList = itemsDtoList;
        notifyDataSetChanged();
    }

    class ItemsDtoViewHolder extends RecyclerView.ViewHolder implements RepositoryRowView, View.OnLongClickListener {
        TextView repoTitle, userName, description, createdAt, language, stars;
        CardView cv;
        Button save, delete;
        ImageView repoImage;

        public ItemsDtoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.card_view);
            this.repoTitle = itemView.findViewById(R.id.full_name);
            this.description = itemView.findViewById(R.id.description);
            this.userName = itemView.findViewById(R.id.user_name);
            this.createdAt = itemView.findViewById(R.id.created_at);
            this.save = itemView.findViewById(R.id.save);
            this.delete = itemView.findViewById(R.id.delete);
            this.language = itemView.findViewById(R.id.language);
            this.stars = itemView.findViewById(R.id.stars);
            this.repoImage = itemView.findViewById(R.id.user_photo);
            this.cv = itemView.findViewById(R.id.card_view);
            save.setBackgroundResource(R.drawable.ic_arrow_forward_black_24dp);

            cv.setOnLongClickListener(this);
        }

        @Override
        public void setTitle(String ITitle) {
            repoTitle.setText(ITitle);
        }

        @Override
        public void setStarCount(int IStarCount) {
            stars.setText(String.valueOf(IStarCount));
        }

        @Override
        public void setDescription(String IDescription) {
            description.setText(IDescription);
        }

        @Override
        public void setUsername(String IUsername) {
            userName.setText(IUsername);
        }

        @Override
        public void setCreatedAt(String ICreatedAt) {
            createdAt.setText(ICreatedAt);
        }

        @Override
        public void setLanguage(String ILanguage) {
            language.setText(ILanguage);
        }

        @Override
        public boolean onLongClick(View v) {
            viewModel.deleteItem(itemsDtoList.get(getAdapterPosition()));
            return false;
        }
    }
}

