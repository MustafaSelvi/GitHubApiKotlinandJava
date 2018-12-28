package com.example.mselvi.githubapi.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mselvi.githubapi.R;
import com.example.mselvi.githubapi.helpers.Constants;
import com.example.mselvi.githubapi.helpers.CustomItemClickListener;
import com.example.mselvi.githubapi.modelLayer.Items;
import com.example.mselvi.githubapi.modelLayer.database.ItemsDto;
import com.example.mselvi.githubapi.modelLayer.database.ItemsListModel;
import com.example.mselvi.githubapi.modelLayer.enums.Source;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Binds;

public class SavedReposActivity extends AppCompatActivity {
    private static final String TAG = "SavedReposActivity";
    private ItemsListModel viewModel;
    private ItemsDtoAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private SavedReposPresenter savedReposPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_repos);
        recyclerView = findViewById(R.id.repo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(ItemsListModel.class);
        viewModel.getRepoList().observe(SavedReposActivity.this, new Observer<List<ItemsDto>>() {
            @Override
            public void onChanged(@Nullable List<ItemsDto> itemAndPeople) {
                savedReposPresenter = new SavedReposPresenter(itemAndPeople);
                recyclerViewAdapter = new ItemsDtoAdapter(savedReposPresenter);
                recyclerViewAdapter.addItems(savedReposPresenter.itemsDtoList,viewModel);

                recyclerView.setAdapter(recyclerViewAdapter);
                Log.v(TAG,"Database entry size :"+String.valueOf(itemAndPeople.size()));
            }
        });
    }
}
