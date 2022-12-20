package com.example.musicapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * Author  : Mohit
 * Project : MusicApp
 * Date    : 10:20 PM
 **/

public class HomeFragment extends Fragment {
    private MusicDb musicDb;
    private static final String TAG = "HomeFragment";
    private Song lastSong;

    public HomeFragment() {
        super(R.layout.home_fragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        musicDb = new MusicDb(getContext());
        setHasOptionsMenu(true);
        List<Song> allSong = musicDb.getAllSong();
        try {
            if (allSong.isEmpty()) {

                Song s1 = new Song("Song 1", "song1", 3, "https://www.shortmusicclips.com/previews/epic-drums-15sec.mp3");
                musicDb.addSong(s1);
                Thread.sleep(1000);

                Song s2 = new Song("Song 2", "song2", 2, "https://www.shortmusicclips.com/previews/resistance-30sec-b.mp3");
                musicDb.addSong(s2);
                Thread.sleep(1000);

                Song s3 = new Song("Song 3", "song3", 5, "https://www.shortmusicclips.com/previews/the-mission-30sec.mp3");
                musicDb.addSong(s3);


                Thread.sleep(1000);
            }

            Song s1 = allSong.get(0);
            MaterialCardView card1 = view.findViewById(R.id.card1);
            card1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoPlayer(s1);
                }
            });

            lastSong = s1;

            ImageView iv1 = view.findViewById(R.id.iv_album_art1);
            TextView song1 = view.findViewById(R.id.tv_name1);
            RatingBar ratingBar1 = view.findViewById(R.id.rating1);
            iv1.setImageDrawable(getImage(s1.icon));
            song1.setText(s1.name);
            ratingBar1.setRating(s1.rate);
            ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    musicDb.updateRate((int) v, s1.id);
                }
            });

            Song s2 = allSong.get(1);
            MaterialCardView card2 = view.findViewById(R.id.card2);
            card2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoPlayer(s2);
                }
            });

            ImageView iv2 = view.findViewById(R.id.iv_album_art2);
            TextView song2 = view.findViewById(R.id.tv_name2);
            RatingBar ratingBar2 = view.findViewById(R.id.rating2);
            iv2.setImageDrawable(getImage(s2.icon));
            song2.setText(s2.name);
            ratingBar2.setRating(s2.rate);
            ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    musicDb.updateRate((int) v, s2.id);
                }
            });

            Song s3 = allSong.get(2);
            MaterialCardView card3 = view.findViewById(R.id.card3);
            card3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoPlayer(s3);
                }
            });
            ImageView iv3 = view.findViewById(R.id.iv_album_art3);
            TextView song3 = view.findViewById(R.id.tv_name3);
            RatingBar ratingBar3 = view.findViewById(R.id.rating3);
            iv3.setImageDrawable(getImage(s3.icon));
            song3.setText(s3.name);
            ratingBar3.setRating(s3.rate);
            ratingBar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    musicDb.updateRate((int) v, s3.id);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void gotoPlayer(Song s) {
        lastSong = s;
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", s);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_main);
        navController.navigate(R.id.action_homeFragment_to_musicPlayerFragment, bundle);
    }

    public Drawable getImage(String nm) {
        if (nm == null) return null;
        return getResources().getDrawable(getResources().getIdentifier(nm, "drawable", getContext().getPackageName()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.media_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_open_media) {
            gotoPlayer(lastSong);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
