package com.laioffer.tinnews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.laioffer.tinnews.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert
    void saveArticle(Article article);

    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAllArticles();
    //benefit:
    //  1.LiveData is a Async task already do in background thread
    //  2.LiveData can automatic update itself when "delete" a article

    @Delete
    void deleteArticle(Article article);

}
