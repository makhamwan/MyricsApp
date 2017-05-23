package com.example.makhamwan.myricsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

/**
 * Created by makhamwan on 5/17/2017 AD.
 */

public class TabList extends Fragment {

    private Button search_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_tab, container, false);
        search_button = (Button) v.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}