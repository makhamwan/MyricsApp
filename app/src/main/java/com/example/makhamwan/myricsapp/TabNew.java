package com.example.makhamwan.myricsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.Firebase;
import java.util.HashMap;
import java.util.Map;
import android.app.SearchManager;

/**
 * Created by makhamwan on 5/17/2017 AD.
 */

public class TabNew extends Fragment {

    private Button add_button, search_button;
    private EditText search, url, title, artist, album, lyric;
    private Firebase mRootRef = new Firebase("https://myricsapp-bf045.firebaseio.com/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_tab, container, false);
        search = (EditText) v.findViewById(R.id.search);
        url = (EditText) v.findViewById(R.id.url);
        title = (EditText) v.findViewById(R.id.title);
        artist = (EditText) v.findViewById(R.id.artist);
        album = (EditText) v.findViewById(R.id.album);
        lyric = (EditText) v.findViewById(R.id.lyric);

        search_button = (Button) v.findViewById(R.id.search_button);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "search ?");
                onSearchClick(v);
            }
        });

        add_button = (Button) v.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url.getText().length()==0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Url", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title.getText().length()==0) {
                    Toast.makeText( getActivity().getApplicationContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (artist.getText().length()==0) artist.setText("Unknown");
                if (album.getText().length()==0) album.setText("Unknown");
                if (lyric.getText().length()==0) lyric.setText("Unknown");

                final String mUrl = url.getText().toString().trim();
                final String mTitle = title.getText().toString().trim();
                final String mArtist = artist.getText().toString().trim();
                final String mAlbum = album.getText().toString().trim();
                final String mLyric = lyric.getText().toString();

                Map<String,String> list_songs = new HashMap<String,String>();
                list_songs.put("url",mUrl);
                list_songs.put("name",mTitle);
                list_songs.put("artist",mArtist);
                list_songs.put("album",mAlbum);
                list_songs.put("lyric",mLyric);
                mRootRef.child("song").push().setValue(list_songs);

                Toast.makeText(getActivity().getApplicationContext(), "ADDED NEW SONG", Toast.LENGTH_SHORT).show();
                add_button.setText("added");
            }
        });
        return v;
    }

    public void onSearchClick(View v) {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            String term = search.getText().toString();
            intent.putExtra(SearchManager.QUERY, term);
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}