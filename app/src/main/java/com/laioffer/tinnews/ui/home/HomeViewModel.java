package com.laioffer.tinnews.ui.home;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.repository.NewsRepository;


public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>(); // state

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    // state and set state in React // event
    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    // transfer between two LiveData
    public LiveData<NewsResponse> getTopHeadlines() {
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
        //== return Transformations.switchMap(countryInput, repository.getTopHeadlines(countryInput.getValue());
    }

    public void setFavoriteArticleInput(Article article) {
        repository.favoriteArticle(article);
    }

}

