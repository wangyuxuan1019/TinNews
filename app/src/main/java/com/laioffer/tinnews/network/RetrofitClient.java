package com.laioffer.tinnews.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//chrome client -> url -> http -> networking call -> html -> render -> website
//retrofit client -> url -> okhttp -> networking call -> json -> parse -> java class
public class RetrofitClient {

    // TODO: Assign your API_KEY here
    private static final String API_KEY = "d9f4911437a94e40a6ac01f5f538f82d";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit newInstance() {
        //同一个http/key use Interceptor to pass key
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
        Log.d("navvv", okHttpClient.toString());
        // 可以有多个 call多个客户端 <-> chrome miti tabs
        return new Retrofit.Builder()
                .baseUrl(BASE_URL) // url
                .client(okHttpClient) //http
                .addConverterFactory(GsonConverterFactory.create()) //json -> jave class
                .build();
    }

    private static class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("X-Api-Key", API_KEY)
                    .build();
            Log.d("builtURL", request.url().toString());
            return chain.proceed(request);
        }
    }
}
