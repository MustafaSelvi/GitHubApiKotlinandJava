package com.example.mselvi.githubapi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.mselvi.githubapi.helpers.Constants;
import com.example.mselvi.githubapi.helpers.CustomItemClickListener;
import com.example.mselvi.githubapi.modelLayer.enums.Languages;
import com.example.mselvi.githubapi.modelLayer.enums.Source;
import com.example.mselvi.githubapi.modelLayer.Items;
import com.example.mselvi.githubapi.R;
import com.example.mselvi.githubapi.utilities.DialogsUtils;

import java.util.ArrayList;
import java.util.List;

public class RepoListActivity extends AppCompatActivity {
    private static final String TAG = "RepoListActivity";

    private RepoListPresenter repoListPresenter = new RepoListPresenter();
    private List<Items> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    RepoViewAdapter adapter;
    LinearLayoutManager manager = new LinearLayoutManager(this);

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);

        attachUI();
        loadData();
        onReachLastItem();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Constants.currentPage = 1;
        switch (item.getItemId()) {
            case R.id.java:
                Constants.languageOptions = String.valueOf(Languages.java);
                loadData();
                break;
            case R.id.kotlin:
                Constants.languageOptions = String.valueOf(Languages.kotlin);
                loadData();
                break;
            case R.id.all:
                Constants.languageOptions = String.valueOf(Languages.all);
                loadData();
                break;
            case R.id.database:
                Intent intent = new Intent(RepoListActivity.this, SavedReposActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    //region Helper Methods

    private void attachUI() {
        recyclerView = findViewById(R.id.repo_recycler_view);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        Log.i(TAG,"RecyclerView loaded successfully");
    }

    //endregion

    //region Network and Data Process specific to RepoListActivity

    private void loadData() {
        ProgressDialog progressDialog = DialogsUtils
                .showProgressDialog(RepoListActivity.this, "Waiting for Receiving Data");

        repoListPresenter.loadData(items -> {
            this.itemList = items;
            initializeListView();
        }, this::notifyDataReceived);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);
    }

    private void addData() {
        repoListPresenter.loadData(this::loadMore, this::notifyDataReceived);
    }
    //endregion

    //region List View Adapter
    private void initializeListView() {

        adapter = new RepoViewAdapter(itemList, new CustomItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                rowTapped(position);
            }

            @Override
            public void onButtonClick(View v, int position) {
                String message = String.format("Data saved to %s", Source.local);
                Toast.makeText(RepoListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    //endregion

    //region User Interaction

    private void rowTapped(int position) {
        Items item = itemList.get(position);
        gotoRepoDetails(item.getHtmlUrl());
    }

    private void notifyDataReceived(Source source) {
        String message = String.format("Data from %s", source.name());
        Toast.makeText(RepoListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void onReachLastItem() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    if (manager != null && manager.findLastCompletelyVisibleItemPosition() == itemList.size() - 1) {
                        //bottom of list!
                        Constants.currentPage++;
                        addData();
                        isLoading = true;
                        Log.v(TAG,"Scrolling, current state :"+Constants.currentPage);
                    }
                }
            }
        });
    }

    private void loadMore(List<Items> items) {

        ProgressDialog progressDialog = DialogsUtils
                .showProgressDialog(RepoListActivity.this, "Fetching Next Page");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                int scrollPosition = itemList.size();
                adapter.notifyItemRemoved(scrollPosition);
                itemList.addAll(items);
                adapter.notifyDataSetChanged();
                isLoading = false;
                progressDialog.dismiss();
                Log.i(TAG,"Data added to list via endless scrolling");
            }
        }, 2000);
    }
    //endregion

    //region Navigation
    private void gotoRepoDetails(String htmlUrl) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.repoUrl, htmlUrl);
        Log.v(TAG,"Repository url to access :"+htmlUrl);
        Intent intent = new Intent(RepoListActivity.this, WebViewActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
    //endregion
}
