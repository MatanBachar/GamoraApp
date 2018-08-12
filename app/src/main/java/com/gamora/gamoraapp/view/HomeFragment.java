package com.gamora.gamoraapp.view;

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

import java.util.Date;

public class HomeFragment extends PostContainerFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        baseMain = inflater.inflate(R.layout.fragment_home, container, false);
//        listView = (RecyclerView) baseMain.findViewById(R.id.listView);
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView =(ListView) view.findViewById(R.id.listView);
        populateView();
        adapterFeed = new AdapterFeed(view.getContext(),R.layout.row_feed,postList);
        listView.setAdapter(adapterFeed);
    }

    public void populateView() {
        postList.add(new Post("123sd689", "12456", 1, "Horizon", "Shalom", new Date(), "456789", 0));
        postList.add(new Post("ssdawdawd", "12456", 1, "Horizon", "Shalom", new Date(), "456789", 0));
        postList.add(new Post("12wdawd689", "12456", 1, "Horizon", "Shalom", new Date(), "456789", 0));
        postList.add(new Post("123wdaw89", "12456", 1, "Horizon", "Shalom", new Date(), "456789", 0));
    }


}
