package neel.com.searchgithubusers;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import neel.com.searchgithubusers.adapter.UserListAdapter;
import neel.com.searchgithubusers.api.Service;
import neel.com.searchgithubusers.model.User;
import neel.com.searchgithubusers.model.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MAIN_ACTIVITY" ;
    private RecyclerView mUserListRv;
    private TextView mEmptyListTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

    }

  private void initViews(){

        mEmptyListTv = findViewById(R.id.tv_ac_main_empty_list);
        mEmptyListTv.setVisibility(View.GONE);

      mUserListRv = findViewById(R.id.rv_ac_main_user_list);
      mUserListRv.setHasFixedSize(true);
      mUserListRv.setLayoutManager(new LinearLayoutManager(this));



    }

    private void fetchUsersFromGithub(String query){

        Service.getApi().users(query)
                .enqueue(new Callback<UserList>() {
                    @Override
                    public void onResponse(Call<UserList> call, Response<UserList> response) {

                        List<User> userList;
                        UserListAdapter adapter;

                        try {

                            if(response.code() == HttpURLConnection.HTTP_OK){

                                userList = response.body().getUsers();
                                adapter = new UserListAdapter(userList, MainActivity.this);
                                mUserListRv.setAdapter(adapter);

                                /*get the user list*/

                                for(int i=0;i<userList.size();i++){

                                    Log.i(TAG,"ON_RESPONSE"+ userList.get(i).getLogin());

                                }

                                if(userList.size()>0) {

                                    mEmptyListTv.setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();

                                }else{
                                    adapter.swap(userList);
                                    mEmptyListTv.setVisibility(View.VISIBLE);
                                }


                                Log.i(TAG,"ON_RESPONSE"+ String.valueOf(response.body()));

                            }else{

                                Log.i(TAG,"ON_RESPONSE: Connection Error");

                                mEmptyListTv.setVisibility(View.VISIBLE);

                            }




                        }catch (NullPointerException e){

                            Log.i(TAG,"ON_RESPONSE: NullpointerException"+ e.getMessage());

                        }catch (IndexOutOfBoundsException e){

                            Log.i(TAG,"ON_RESPONSE: IndexOutOfBoundException"+ e.getMessage());

                        }


                    }

                    @Override
                    public void onFailure(Call<UserList> call, Throwable t) {

                        Log.i("RESPONSE","Failed");

                    }
                });


    }

    /*menu*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        /*styling searchview*/

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHint(R.string.hint);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.BLACK);
        View searchplate = (View)searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchplate.setBackgroundResource(R.drawable.bg_searchview);
        ImageView searchIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.ic_action_search_light);


        /*ending styling searchview*/


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                fetchUsersFromGithub(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return true;

    }
}
