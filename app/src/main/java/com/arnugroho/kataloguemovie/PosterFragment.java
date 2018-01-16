package com.arnugroho.kataloguemovie;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class PosterFragment extends DialogFragment {
    public ImageView imgPoster;
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public PosterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_poster, container, false);
        imgPoster = (ImageView)view.findViewById(R.id.img_poster);
        Picasso.with(getContext()).load(Uri.parse(imgPath)).into(imgPoster);
        // Inflate the layout for this fragment
        return view;
    }

}
