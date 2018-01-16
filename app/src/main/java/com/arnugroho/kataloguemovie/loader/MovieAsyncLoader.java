package com.arnugroho.kataloguemovie.loader;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.arnugroho.kataloguemovie.Constants;
import com.arnugroho.kataloguemovie.model.Movie;
import com.arnugroho.kataloguemovie.service.MovieService;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by arnugroho on 12/3/2017.
 */

public class MovieAsyncLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mData;
    private boolean mHasResult = false;
    private static final String API_KEY = Constants.API_KEY_3;

    private String queryMovie;
    private MovieService movieService = new MovieService();


    public MovieAsyncLoader(Context context, String queryMovie) {
        super(context);

        onContentChanged();
        this.queryMovie = queryMovie;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> movieItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +
               API_KEY + "&language=en-US&query=" + queryMovie;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject object = list.getJSONObject(i);
                        Movie movie = movieService.getDataFromJson(object);
                        movieItems.add(movie);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }


        });

        return movieItems;
    }

    protected void onReleaseResources(ArrayList<Movie> data) {
        //nothing to do.
    }
}
