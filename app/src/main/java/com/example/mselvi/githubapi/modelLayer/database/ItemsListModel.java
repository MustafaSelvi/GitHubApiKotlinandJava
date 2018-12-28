package com.example.mselvi.githubapi.modelLayer.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ItemsListModel extends AndroidViewModel {
    private final LiveData<List<ItemsDto>> repoList;

    private AppDatabase appDatabase;

    public ItemsListModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        repoList = appDatabase.itemsDao().getAll();
    }
    public void addItem(final ItemsDto itemsDto) {
        new addAsyncTask(appDatabase).execute(itemsDto);
    }

    public void deleteItem(ItemsDto itemsDto) {
        new deleteAsyncTask(appDatabase).execute(itemsDto);
    }

    public void getTitle(String title) {
        new getTitleAsyncTask(appDatabase).execute(title);
    }

    public int getCount(Integer repoId) {
       return appDatabase.itemsDao().getCount(repoId);
    }

    public int deleteRow( Integer repoId) {
        return appDatabase.itemsDao().deleteRow(repoId);
    }



    public LiveData<List<ItemsDto>> getRepoList() {
        return repoList;
    }

    private static class addAsyncTask extends AsyncTask<ItemsDto, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ItemsDto... params) {
            db.itemsDao().insert(params[0]);
            return null;
        }

    }
    private static class deleteAsyncTask extends AsyncTask<ItemsDto, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ItemsDto... params) {
            db.itemsDao().delete(params[0]);
            return null;
        }
    }

    private static class getTitleAsyncTask extends AsyncTask<String, Void, Void> {

        private AppDatabase db;

        getTitleAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }


        @Override
        protected Void doInBackground(String... title) {
            db.itemsDao().getTitle(String.valueOf(title));

            return null;
        }

    }

}
