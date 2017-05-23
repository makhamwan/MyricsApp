package com.example.makhamwan.myricsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private String key;

    private EditText name, artist, album, lyric;
    private Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        key = getIntent().getStringExtra("key");

        name = (EditText) findViewById(R.id.title);
        artist = (EditText) findViewById(R.id.artist);
        album = (EditText) findViewById(R.id.album);
        lyric = (EditText) findViewById(R.id.lyric);
        save_button = (Button) findViewById(R.id.save_button);
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myricsapp-bf045.firebaseio.com/song/" + key);
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("name")){
                    String mName = dataSnapshot.getValue().toString();
                    name.setText(mName);
                }
                if(dataSnapshot.getKey().equals("artist")){
                    String mArtist = dataSnapshot.getValue().toString();
                    artist.setText(mArtist);
                }
                if(dataSnapshot.getKey().equals("album")){
                    String mAlbum = dataSnapshot.getValue().toString();
                    artist.setText(mAlbum);
                }
                if(dataSnapshot.getKey().equals("lyric")){
                    String mLyric = dataSnapshot.getValue().toString();
                    lyric.setText( mLyric);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("name")){
                    String mName = dataSnapshot.getValue().toString();
                    name.setText(mName);
                }
                if(dataSnapshot.getKey().equals("artist")){
                    String mArtist = dataSnapshot.getValue().toString();
                    artist.setText(mArtist);
                }
                if(dataSnapshot.getKey().equals("album")){
                    String mAlbum = dataSnapshot.getValue().toString();
                    album.setText(mAlbum);
                }
                if(dataSnapshot.getKey().equals("lyric")){
                    String mLyric = dataSnapshot.getValue().toString();
                    lyric.setText( mLyric);
                }
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

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_name = name.getText().toString();
                String new_artist = artist.getText().toString();
                String new_album = album.getText().toString();
                String new_lyric = lyric.getText().toString();
                mDatabaseRef.child("name").setValue(new_name);
                mDatabaseRef.child("artist").setValue(new_artist);
                mDatabaseRef.child("album").setValue(new_album);
                mDatabaseRef.child("lyric").setValue(new_lyric);
                Toast.makeText(EditActivity.this, "UPDATED SONG", Toast.LENGTH_SHORT).show();
            }
        });
    }
}