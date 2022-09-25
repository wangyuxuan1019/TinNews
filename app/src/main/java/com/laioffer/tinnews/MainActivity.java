package com.laioffer.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laioffer.tinnews.R;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("navvv", "abc");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        // click on tab on BottomNavView can navigate
        NavigationUI.setupWithNavController(navView, navController);
        // can display label on action bar
        NavigationUI.setupActionBarWithNavController(this, navController);

        // new a task, make the call<NewsResponse>
        // add task to queue
        // while(true) { retrofit keep check the queue }
        // if queue has `task`, retrofit do task: call endpoint, parse json , etc
        // once retrofit finish the task
        // callback.onResponse(response)
        // if (task success) newsResponseCallback.onResponse()
        // else newsResponseCallback.onFailure()

//        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);
//        // Call<NewsResponse>
//          // multi thread add task in loop -> call back return to enqueue
//        api.getTopHeadlines("us").enqueue(new Callback<NewsResponse>() {
//            //run in background
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.d("getTopHeadlines", response.body().toString());
//                } else {
//                    Log.d("getTopHeadlines", response.toString());
//                }
//            }
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                Log.d("getTopHeadlines", t.toString());
//            }
//        });

    }

    @Override
    //for ActionBar
    //for 左下角 use onBackPressed()
    public boolean onSupportNavigateUp() {
        // can click back
        return navController.navigateUp();
    }
}