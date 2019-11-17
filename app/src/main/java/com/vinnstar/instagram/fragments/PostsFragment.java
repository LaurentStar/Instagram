package com.vinnstar.instagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.vinnstar.instagram.Post;
import com.vinnstar.instagram.PostsAdapter;
import com.vinnstar.instagram.R;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    private final static String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> mPosts;
    private SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Refreshing");
                queryPost();
            }
        });


        rvPosts = view.findViewById(R.id.rvPosts);

        //Create adapter
        mPosts = new ArrayList<>();
        //create datascource
        adapter = new PostsAdapter(getContext(), mPosts);
        //set adapter on recycler view
        rvPosts.setAdapter(adapter);
        //set layout managers on recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPost();
    }


    protected void queryPost() {

        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Null data", e);
                    return;
                }
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);
                for (int i = 0; i < posts.size(); i++) {
                    Log.i(TAG, "Post: " + posts.get(i).getDescription() + " User: " + posts.get(i).getUser().getUsername());
                }

            }
        });
    }
}
