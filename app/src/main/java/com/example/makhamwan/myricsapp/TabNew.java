package com.example.makhamwan.myricsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by makhamwan on 5/17/2017 AD.
 */

public class TabNew extends Fragment {

    private Button add_button;
    private EditText url, title, artist, album, lyric;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_tab, container, false);
        url = (EditText) v.findViewById(R.id.url);
        title = (EditText) v.findViewById(R.id.title);
        artist = (EditText) v.findViewById(R.id.artist);
        album = (EditText) v.findViewById(R.id.album);
        lyric = (EditText) v.findViewById(R.id.lyric);

        add_button = (Button) v.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_button.setText("added");
            }
        });
        return v;
    }
}