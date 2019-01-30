package neel.com.searchgithubusers.api;

import neel.com.searchgithubusers.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Github {

    String GITHUB_BASE_URL ="https://api.github.com";


    interface Auth{

        @GET("/search/users")
        Call<UserList>users(@Query("q")String query);


    }

}
