package com.example.mselvi.githubapi.activities;

import com.example.mselvi.githubapi.helpers.RepositoryRowView;
import com.example.mselvi.githubapi.modelLayer.database.ItemsDto;

import java.util.List;

public class SavedReposPresenter {
    List<ItemsDto> itemsDtoList;


    public SavedReposPresenter(List<ItemsDto> itemsDtoList) {
        this.itemsDtoList = itemsDtoList;
    }


    public void onBindRepositoryRowViewAtPosition(RepositoryRowView rowView, int position) {
        ItemsDto itemsDto = itemsDtoList.get(position);
        rowView.setTitle(itemsDto.getRepoTitle());
        rowView.setStarCount(itemsDto.getStars());
        rowView.setDescription(itemsDto.getDescription());
        rowView.setUsername(itemsDto.getUserName());
        rowView.setCreatedAt(itemsDto.getCreatedAt());
        rowView.setLanguage(itemsDto.getLanguage());
    }

    public int getRepositoriesRowsCount() {
        return itemsDtoList.size();
    }

}
