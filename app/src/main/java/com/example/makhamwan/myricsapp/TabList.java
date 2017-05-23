package com.example.makhamwan.myricsapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by makhamwan on 5/17/2017 AD.
 */

public class TabList extends Fragment {

    private Button search_button, delete_button;
    RecyclerView recyclerView;
    DatabaseReference mDatabaseRef;
    private FirebaseRecyclerAdapter<Song, ShowDataViewHolder> mFirebaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_tab, container, false);

        mDatabaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myricsapp-bf045.firebaseio.com/song");

        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Toast.makeText(getActivity(), "Please wait, it is loading ..", Toast.LENGTH_SHORT).show();

        search_button = (Button) v.findViewById(R.id.search_button);
        delete_button = (Button) v.findViewById(R.id.delete_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                startActivity(intent);
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
                viewHolder.setSong(model.getName(), model.getArtist());

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
                        startActivity(intent);
                    }
                });
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