package com.gamora.gamoraapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.model.data.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class PostContainerFragment extends Fragment {

    protected ListView listView;
    protected ArrayList<Post> postList;
    protected AdapterFeed adapterFeed;
    protected BaseMainActivity activityRef = ((BaseMainActivity)getActivity());
    protected View baseMain;

    //Firebase objects
    protected DatabaseReference postsDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postsDatabase = FirebaseDatabase.getInstance().getReference("posts");
        postList = new ArrayList<>();
//        adapterFeed = new AdapterFeed(postList);
//        listView.setAdapter(adapterFeed);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            postList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    postList.add(post);
                }
                adapterFeed.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public abstract void populateView();
}
