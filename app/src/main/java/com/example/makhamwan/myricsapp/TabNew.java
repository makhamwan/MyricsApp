package com.example.makhamwan.myricsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.Firebase;

/**
 * Created by makhamwan on 5/17/2017 AD.
 */

public class TabNew extends Fragment {

    private Button add_button;
    private EditText url, title, artist, album, lyric;
    private Firebase mRootRef = new Firebase("https://myricsapp-bf045.firebaseio.com/").child("Songs").push();

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
                final String mUrl = url.getText().toString().trim();
                final String mTitle = title.getText().toString().trim();
                final String mArtist = artist.getText().toString().trim();
                final String mAlbum = album.getText().toString().trim();
                final String mLyric = lyric.getText().toString();
                if (mUrl.isEmpty()) {
                    Toast.makeText( getActivity().getApplicationContext(), "Please Enter Url", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mTitle.isEmpty()) {
                    Toast.makeText( getActivity().getApplicationContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] arrays = new String[5];
                arrays[0] = mUrl;
                arrays[1] = mTitle;
                arrays[2] = mArtist;
                arrays[3] = mAlbum;
                arrays[4] = mLyric;

                Firebase childRef_Song = mRootRef.child("Song");
                childRef_Song.setValue(arrays);

                Toast.makeText(getActivity().getApplicationContext(), "ADDED NEW SONG", Toast.LENGTH_SHORT).show();
                add_button.setText("added");
            }
        });
        return v;
    }
}