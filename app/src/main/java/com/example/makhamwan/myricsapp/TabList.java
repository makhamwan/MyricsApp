package com.example.makhamwan.myricsapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by makhamwan on 5/17/2017 AD.
 */

public class TabList extends Fragment {

    private Button search_button, fav_button, delete_button;
    RecyclerView recyclerView;
    DatabaseReference mDatabaseRef;
    private FirebaseRecyclerAdapter<Song, ShowDataViewHolder> mFirebaseAdapter;
    private Firebase mRootRef = new Firebase("https://myricsapp-bf045.firebaseio.com/");
    private String search_message;
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_tab, container, false);
        mDatabaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myricsapp-bf045.firebaseio.com/song");
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Toast.makeText(getActivity(), "Please wait, it is loading ..", Toast.LENGTH_SHORT).show();
        editText = (EditText) v.findViewById(R.id.search);
        search_button = (Button) v.findViewById(R.id.search_button);
        delete_button = (Button) v.findViewById(R.id.delete_button);
        fav_button = (Button) v.findViewById(R.id.fav_button);
        search_message = "";
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_message = editText.getText().toString();
                onStart();
            }
        });

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.child("favorite").getChildren()){
                    String favSong = data.getKey();
                    String keySong = data.child("key").getValue().toString();

                    mRootRef.child("favorite")
                            .child(favSong)
                            .child("name")
                            .setValue(
                                dataSnapshot
                                        .child("song")
                                        .child(keySong)
                                        .child("name").getValue().toString()
                            );
                    mRootRef.child("favorite")
                            .child(favSong)
                            .child("artist")
                            .setValue(
                                    dataSnapshot
                                            .child("song")
                                            .child(keySong)
                                            .child("artist").getValue().toString()
                            );
                    mRootRef.child("favorite")
                            .child(favSong)
                            .child("album")
                            .setValue(
                                    dataSnapshot
                                            .child("song")
                                            .child(keySong)
                                            .child("album").getValue().toString()
                            );
                    mRootRef.child("favorite")
                            .child(favSong)
                            .child("lyric")
                            .setValue(
                                    dataSnapshot
                                            .child("song")
                                            .child(keySong)
                                            .child("lyric").getValue().toString()
                            );

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Song,ShowDataViewHolder>(Song.class, R.layout.list_item, ShowDataViewHolder.class, mDatabaseRef){
            @Override
            protected void populateViewHolder(final ShowDataViewHolder viewHolder, Song model, final int position) {
                if (search_message.length()==0 || model.getName().equalsIgnoreCase(search_message)){

                    if (model.getFavorite().equalsIgnoreCase("true")){
                        viewHolder.setFav();
                    }

                    viewHolder.setSong(model.getName(), model.getArtist());
                    viewHolder.itemView.findViewById(R.id.fav_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        final String mKey = mFirebaseAdapter.getRef(position).getKey();
                        mRootRef.child("favorite").push().child("key").setValue(mKey);
                        Toast.makeText(getActivity().getApplicationContext(), "ADDED FAVORITE SONG", Toast.LENGTH_SHORT).show();
                        mRootRef.child("song").child(mKey).child("favorite").setValue("true");
                        }
                    });

                    viewHolder.itemView.findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), EditActivity.class);
                            intent.putExtra("key", mFirebaseAdapter.getRef(position).getKey());
                            startActivity(intent);
                        }
                    });

                    viewHolder.itemView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Delete?").setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int selectedItems = position;
                                            mFirebaseAdapter.getRef(selectedItems).removeValue();
                                            mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                            recyclerView.invalidate();
                                            onStart();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.setTitle("Are you sure?");
                            dialog.show();
                        }
                    });

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ViewActivity.class);
                            intent.putExtra("key", mFirebaseAdapter.getRef(position).getKey());
                            startActivity(intent);
                        }
                    });
                }
                else {
                    viewHolder.hideCard();
                    return;
                }
            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    public static class ShowDataViewHolder extends RecyclerView.ViewHolder{
        private final LinearLayout card;
        private final TextView name;
        private final TextView artist;
        private final Button fav_button;
        public ShowDataViewHolder( final View view){
            super(view);
            name = (TextView) view.findViewById(R.id.song_title);
            artist = (TextView) view.findViewById(R.id.song_artist);
            card = (LinearLayout) view.findViewById(R.id.card);
            fav_button = (Button) view.findViewById(R.id.fav_button);

        }
        public void setSong(String name, String artist){
            this.name.setText(name);
            this.artist.setText(artist);

        }

        public void hideCard(){
            this.card.setVisibility(View.GONE);
        }

        public void setFav(){
            this.fav_button.setBackgroundResource(R.drawable.fav);
            this.fav_button.setEnabled(false);
        }
    }
}