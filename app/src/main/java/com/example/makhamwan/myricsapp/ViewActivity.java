package com.example.makhamwan.myricsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference mDatabaseRef;
    private FirebaseRecyclerAdapter<Song, ShowDataViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_tab);

        mDatabaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myricsapp-bf045.firebaseio.com/song");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("test", ""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewActivity.this));

        Toast.makeText(this, "Please wait, it is loading ..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("test", "Hello");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Song,ShowDataViewHolder>(Song.class, R.layout.list_item, ShowDataViewHolder.class, mDatabaseRef){
            @Override
            protected void populateViewHolder(ShowDataViewHolder viewHolder, Song model, final int position) {
                viewHolder.setSong(model.getName(), model.getArtist());
                Log.d( "test" ,model.getName() + " , " + model.getArtist() );
            }
        };

        recyclerView.setAdapter(mFirebaseAdapter);
    }

    public static class ShowDataViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView artist;
        public ShowDataViewHolder( final View view){
            super(view);
            name = (TextView) view.findViewById(R.id.song_title);
            artist = (TextView) view.findViewById(R.id.song_artist);
        }
        public void setSong(String name, String artist){
            this.name.setText(name);
            this.artist.setText(artist);
        }
    }
}
