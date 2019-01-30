package neel.com.searchgithubusers.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList {

    @SerializedName("items")
    @Expose
    private List<User> mUsers;

    public UserList(List<User> users) {
        mUsers = users;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }
}
