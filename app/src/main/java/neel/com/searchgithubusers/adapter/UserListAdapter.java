package neel.com.searchgithubusers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import neel.com.searchgithubusers.R;
import neel.com.searchgithubusers.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private List<User> mUserList;
    private Context mContext;

    public UserListAdapter(List<User> userList, Context context) {
        mUserList = userList;
        mContext = context;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user_info,parent,false);
        UserListViewHolder viewHolder = new UserListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {

        holder.bindUserList(position);

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    public void swap(List list){

        if (mUserList != null) {
            mUserList.clear();
            mUserList.addAll(list);
        }
        else {
            mUserList = list;
        }
        notifyDataSetChanged();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder{

        private ImageView user_image;
        private TextView user_name;

        public UserListViewHolder(View itemView) {
            super(itemView);

            user_image = itemView.findViewById(R.id.iv_list_item_user_info);
            user_name = itemView.findViewById(R.id.tv_list_item_user_info);

        }

        public void bindUserList(int position){

            user_name.setText(mUserList.get(position).getLogin());

            Picasso.with(mContext).load(mUserList.get(position).getAvatarUrl())
                    .placeholder(R.drawable.male)
                    .into(user_image);


        }

    }
}
