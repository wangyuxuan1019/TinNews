package com.laioffer.tinnews.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.laioffer.tinnews.TinNewsApplication;
import com.laioffer.tinnews.database.TinNewsDatabase;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final NewsApi newsApi;
    private final TinNewsDatabase database;

    public NewsRepository() {
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = TinNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {
        //mutable vs immutable <-> arrayList vs linkedList
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        //callback<NewsResponse> callback = new callback<NewsResponse>
        //Call<NewsResponse> call = newsApi.getTopHeadlines(country)
        //callback.onresponse(call, newsresponse)
        Log.d("navvv", newsApi.toString());
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                            Log.d("getTopHeadlines", response.body().toString());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        return everyThingLiveData;
    }

    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        Log.d("AsyncTest", Thread.currentThread().getName() + "1");//order 126345
        new FavoriteAsyncTask(database, resultLiveData).execute(article);
        Log.d("AsyncTest", Thread.currentThread().getName() + "6");
        return resultLiveData;
    }

    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> implements com.laioffer.tinnews.repository.FavoriteAsyncTask {

        private final TinNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinNewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("AsyncTest", Thread.currentThread().getName() + "2");
        }

        @Override
        protected Boolean doInBackground(Article... articles) {
            Log.d("AsyncTest", Thread.currentThread().getName() + "3");
            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
                publishProgress();
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.d("AsyncTest", Thread.currentThread().getName() + "4");
        }

        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
            Log.d("AsyncTest", Thread.currentThread().getName() + "5");
        }
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles();
    }

    public void deleteSavedArticle(Article article) {
        //simpler version to implement AsyncTask
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

}

