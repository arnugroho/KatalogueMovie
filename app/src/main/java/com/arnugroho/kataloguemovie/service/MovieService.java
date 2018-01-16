package com.arnugroho.kataloguemovie.service;

import com.arnugroho.kataloguemovie.model.Movie;

import org.json.JSONObject;

/**
 * Created by arnugroho on 12/3/2017.
 */

public class MovieService {
    public Movie getDataFromJson (JSONObject object) throws Exception {
        Movie m = new Movie();
        m.setVote_count(object.getString("vote_count"));
        m.setId(object.getString("id"));
        m.setVote_average(object.getString("vote_average"));
        m.setTitle(object.getString("title"));
        m.setPopularity(object.getString("popularity"));
        m.setPoster_path(object.getString("poster_path"));
        m.setOriginal_language(object.getString("original_language"));
        m.setOriginal_title(object.getString("original_title"));
        m.setBackdrop_path(object.getString("backdrop_path"));
        m.setAdult(object.getString("adult"));
        m.setOverview(object.getString("overview"));
        m.setRelease_date(object.getString("release_date"));

        return m;

    }
}
