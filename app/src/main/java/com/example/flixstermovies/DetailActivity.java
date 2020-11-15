package com.example.flixstermovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixstermovies.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    TextView tvTitle, tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    static final String YOUTUBE_API_KEY = "AIzaSyCpQpm4vYiJnDk0NKkZsAZ-wsxqKDpnLGY";
    static final String YOUTUBE_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie m = Parcels.unwrap(getIntent().getParcelableExtra("movieObject"));

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(YOUTUBE_URL, m.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                // parse the response to get the YT video key
                try {
                    JSONArray resutls = json.jsonObject.getJSONArray("results");
                    if(resutls.length() == 0) return;

                    key = resutls.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", "get the key " + key );
                    initializePlayer();
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to get jsonArray", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });



        tvTitle.setText(m.getTitle());
        tvOverview.setText(m.getOverview());
        ratingBar.setRating(m.getRating());
    }

    private void initializePlayer(){
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(key);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("DetailActivity", "YT Player initialization failure");
            }
        });
    }
}