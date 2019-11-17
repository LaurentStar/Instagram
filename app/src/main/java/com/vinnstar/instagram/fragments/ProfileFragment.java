package com.vinnstar.instagram.fragments;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.vinnstar.instagram.Post;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    private final static String TAG = "ProfileFragment";

    @Override
    protected void queryPost() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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
                for (int i = 0; i < posts.size(); i++) {
                    Log.i(TAG, "Post: " + posts.get(i).getDescription() + " User: " + posts.get(i).getUser().getUsername());
                }
            }
        });
    }
}
