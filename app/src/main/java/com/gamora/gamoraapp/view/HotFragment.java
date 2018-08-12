package com.gamora.gamoraapp.view;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.model.data.Post;
import com.google.firebase.database.Query;

import java.util.Date;

public class HotFragment extends PostContainerFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_hot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView);
        populateView();
        adapterFeed = new AdapterFeed(view.getContext(), R.layout.row_feed, postList);
        listView.setAdapter(adapterFeed);
    }

    public void populateView() {
        postsDatabase.addListenerForSingleValueEvent(valueEventListener);
    }
}
