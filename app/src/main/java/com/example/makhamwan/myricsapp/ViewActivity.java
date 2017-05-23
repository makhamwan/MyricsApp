package com.example.makhamwan.myricsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private String key;

    private TextView name, artist, album, lyric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        key = getIntent().getStringExtra("key");

        name = (TextView) findViewById(R.id.title);
        artist = (TextView) findViewById(R.id.artist);
        album = (TextView) findViewById(R.id.album);
        lyric = (TextView) findViewById(R.id.lyric);
        mDatabaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myricsapp-bf045.firebaseio.com/song/" + key);
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("name")){
                    String mName = dataSnapshot.getValue().toString();
                    name.setText( "Title: " + mName);
                }
                if(dataSnapshot.getKey().equals("artist")){
                    String mArtist = dataSnapshot.getValue().toString();
                    artist.setText( "Artist: " + mArtist);
                }
                if(dataSnapshot.getKey().equals("album")){
                    String mAlbum = dataSnapshot.getValue().toString();
                    album.setText( "Album: " + mAlbum);
                }
                if(dataSnapshot.getKey().equals("lyric")){
                    String mLyric = dataSnapshot.getValue().toString();
                    lyric.setText( "Lyric: \n" + mLyric);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}