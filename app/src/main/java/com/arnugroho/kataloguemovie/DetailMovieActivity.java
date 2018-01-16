package com.arnugroho.kataloguemovie;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnugroho.kataloguemovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MOVIE = "extra_movie";
    private ImageView imgDetail;
    private TextView tvReleaseDate, tvOverview, tvTitle;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Button w154, w185, w342, w500, w780;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        imgDetail = (ImageView)findViewById(R.id.img_dtl);
        tvReleaseDate = (TextView)findViewById(R.id.tv_dtl_releasedate);
        tvOverview = (TextView)findViewById(R.id.tv_dtl_overview);
        tvTitle = (TextView)findViewById(R.id.tv_dtl_title);

        Picasso.with(this).load(Uri.parse("http://image.tmdb.org/t/p/w342" + movie.getPoster_path())).into(imgDetail);

        String sReleaseDate = movie.getRelease_date();
        try {
            Date releaseDate = sdf.parse(movie.getRelease_date());
            sdf.applyPattern("E, dd MMM yyyy");
            sReleaseDate = sdf.format(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        w154 = (Button)findViewById(R.id.btn_w154);
        w154.setOnClickListener(this);
        w185 = (Button)findViewById(R.id.btn_w185);
        w185.setOnClickListener(this);
        w342 = (Button)findViewById(R.id.btn_w342);
        w342.setOnClickListener(this);
        w500 = (Button)findViewById(R.id.btn_w500);

        tvReleaseDate.setText(sReleaseDate);
        tvOverview.setText(movie.getOverview());
        tvTitle.setText(movie.getTitle());
    }

    @Override
    public void onClick(View view) {
        PosterFragment posterFragment = new PosterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (view.getId()){
            case R.id.btn_w154 :
                Log.d("w15","w15");
                posterFragment.setImgPath("http://image.tmdb.org/t/p/w154" + movie.getPoster_path());
                posterFragment.show(fragmentManager, PosterFragment.class.getSimpleName());
                break;
            case R.id.btn_w185 :
                posterFragment.setImgPath("http://image.tmdb.org/t/p/w185" + movie.getPoster_path());
                posterFragment.show(fragmentManager, PosterFragment.class.getSimpleName());
                break;
            case R.id.btn_w342 :
                posterFragment.setImgPath("http://image.tmdb.org/t/p/w342" + movie.getPoster_path());
                posterFragment.show(fragmentManager, PosterFragment.class.getSimpleName());
                break;
            case R.id.btn_w500 :
                posterFragment.setImgPath("http://image.tmdb.org/t/p/w500" + movie.getPoster_path());
                posterFragment.show(fragmentManager, PosterFragment.class.getSimpleName());
                break;

        }
    }
}
