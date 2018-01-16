package com.arnugroho.kataloguemovie;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.arnugroho.kataloguemovie.loader.MovieAsyncLoader;
import com.arnugroho.kataloguemovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.listView)
    ListView listView;
    KatalogAdapter adapter;
    @BindView(R.id.edit_movie)
    EditText findMovie;
    @BindView(R.id.btn_src)
    Button btnFind;

    static final String EXTRA_FIND_MOVIE = "EXTRA_FIND_MOVIE";


    public SearchMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);

        ButterKnife.bind(this, view);

        adapter = new KatalogAdapter(getActivity());
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(listViewListener);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FIND_MOVIE, findMovie.getText().toString());

        getLoaderManager().initLoader(0, bundle, this );
        return view;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String extraFindMovie = "";
        if (args != null){
            extraFindMovie = args.getString(EXTRA_FIND_MOVIE);
        }

        return new MovieAsyncLoader(getActivity(),extraFindMovie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        adapter.setData(movies);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }

    @OnClick(R.id.btn_src)
    public void searchMovie() {
        String movie = findMovie.getText().toString();

        if (TextUtils.isEmpty(movie))return;

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FIND_MOVIE,movie);
        getLoaderManager().restartLoader(0,bundle,  this);
    }

    AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("listViewListener", "listViewListener");
            Intent intentDetailMovie = new Intent(getActivity(), DetailMovieActivity.class);
            Movie movie = (Movie) adapterView.getItemAtPosition(i);
            intentDetailMovie.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
            startActivity(intentDetailMovie);

        }
    };

}
