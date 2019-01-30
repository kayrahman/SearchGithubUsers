package neel.com.searchgithubusers.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static neel.com.searchgithubusers.api.Github.GITHUB_BASE_URL;

public class Service {

    public static Github.Auth getApi(){

        return new Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Github.Auth.class);

    }
}
