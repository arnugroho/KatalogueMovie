package com.arnugroho.kataloguemovie;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.arnugroho.kataloguemovie.loader.MovieAsyncLoader;
import com.arnugroho.kataloguemovie.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{
    ListView listView;
    KatalogAdapter adapter;
    EditText findMovie;
    Button btnFind;

    static final String EXTRA_FIND_MOVIE = "EXTRA_FIND_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new KatalogAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(adapter);

        findMovie = (EditText)findViewById(R.id.edit_movie);
        btnFind = (Button)findViewById(R.id.btn_src);

        btnFind.setOnClickListener(myListener);

        listView.setOnItemClickListener(listViewListener);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FIND_MOVIE, findMovie.getText().toString());

        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String extraFindMovie = "";
        if (args != null){
            extraFindMovie = args.getString(EXTRA_FIND_MOVIE);
        }

        return new MovieAsyncLoader(this,extraFindMovie);
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        adapter.setData(movies);
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movie = findMovie.getText().toString();

            if (TextUtils.isEmpty(movie))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_FIND_MOVIE,movie);
            getLoaderManager().restartLoader(0,bundle, MainActivity.this);
        }
    };

    AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("listViewListener", "listViewListener");
            Intent intentDetailMovie = new Intent(MainActivity.this, DetailMovieActivity.class);
            Movie movie = (Movie) adapterView.getItemAtPosition(i);
            intentDetailMovie.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
            startActivity(intentDetailMovie);

        }
    };
}
