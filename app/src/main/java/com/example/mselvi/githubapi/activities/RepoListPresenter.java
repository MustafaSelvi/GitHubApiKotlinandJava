package com.example.mselvi.githubapi.activities;

import android.os.Build;
import android.util.Log;
import com.example.mselvi.githubapi.helpers.Constants;
import com.example.mselvi.githubapi.helpers.RestManager;
import com.example.mselvi.githubapi.modelLayer.api.RepoRestClient;
import com.example.mselvi.githubapi.modelLayer.enums.Source;
import com.example.mselvi.githubapi.modelLayer.Items;
import com.example.mselvi.githubapi.modelLayer.RepoResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoListPresenter {
    private static final String TAG = "RepoListPresenter";
    List<Items> items;

    public void loadData(Consumer<List<Items>> onNewResults, Consumer<Source> notifyDataRecieved) {
        RepoRestClient repoRestClient = RestManager.getClient().create(RepoRestClient.class);

        Call<RepoResponse> callGroup = repoRestClient.getMappedLanguage(Constants.languageOptions + "+pushed:>" +formatCurrentDate(), String.valueOf(Constants.currentPage));

        callGroup.enqueue(new Callback<RepoResponse>() {
            @Override
            public void onResponse(Call<RepoResponse> call, Response<RepoResponse> response) {
                if (response.isSuccessful()) {
                    items = response.body().getItems();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        onNewResults.accept(items);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        notifyDataRecieved.accept(Source.network);
                    }
                    Log.v(TAG,"Rest Response :"+response.message());
                }
            }
            @Override
            public void onFailure(Call<RepoResponse> call, Throwable t) {
               Log.e(TAG,"Network Error");
            }
        });
    }

    public String formatCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss ");
        df.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String formattedDate = df.format(c);
        Log.i("Date", String.valueOf(formattedDate));

        return formattedDate;
    }
}
