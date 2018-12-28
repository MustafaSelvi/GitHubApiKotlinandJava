package com.example.mselvi.githubapi.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mselvi.githubapi.helpers.CustomItemClickListener;
import com.example.mselvi.githubapi.modelLayer.Items;
import com.example.mselvi.githubapi.R;
import com.example.mselvi.githubapi.modelLayer.database.AppDatabase;
import com.example.mselvi.githubapi.modelLayer.database.ItemsDto;
import com.example.mselvi.githubapi.modelLayer.database.ItemsListModel;
import java.util.List;

public class RepoViewAdapter extends RecyclerView.Adapter<RepoViewHolder> {

    List<Items> items;
    CustomItemClickListener listener;
    private ItemsListModel itemsListModel;

    public RepoViewAdapter(List<Items> items, CustomItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View repoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_cell, parent, false);
        final RepoViewHolder repoViewHolder = new RepoViewHolder(repoView);
        itemsListModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ItemsListModel.class);

        return repoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int i) {
        Items item = items.get(i);
        if (listener != null) {

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, holder.getAdapterPosition());
                }
            });

            itemsListModel.getTitle(item.getFullName());

           int count = itemsListModel.getCount(item.getId());

           if(count >= 1){
               holder.save.setVisibility(View.INVISIBLE);
               holder.delete.setVisibility(View.VISIBLE);
           }
           else{
               holder.save.setVisibility(View.VISIBLE);
               holder.delete.setVisibility(View.INVISIBLE);
           }
           Log.v("RepoViewAdapter","Current view's row count on database :"+ String.valueOf(count));

            holder.save.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    itemsListModel.addItem(new ItemsDto(holder.repoTitle.getText().toString(),
                            holder.description.getText().toString(),
                            holder.userName.getText().toString(),
                            holder.createdAt.getText().toString(),
                            holder.language.getText().toString(),
                            item.getHtmlUrl(),
                            item.getStargazersCount(),item.getId()));

                    Log.i("position", String.valueOf(holder.getAdapterPosition()));

                    listener.onButtonClick(v, holder.getAdapterPosition());
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });

             holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemsListModel.deleteRow(item.getId());
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        }
        Items itemAdapter = items.get(i);
        holder.configureWith(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
