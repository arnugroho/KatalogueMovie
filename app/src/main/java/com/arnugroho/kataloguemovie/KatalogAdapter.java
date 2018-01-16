package com.arnugroho.kataloguemovie;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnugroho.kataloguemovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by arnugroho on 12/3/2017.
 */

public class KatalogAdapter extends BaseAdapter {

    private ArrayList<Movie> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public KatalogAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Movie> items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final Movie item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public Movie getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_row_movie, null);
            holder.textViewTitle= (TextView)convertView.findViewById(R.id.tv_item_title);
            holder.overview = (TextView)convertView.findViewById(R.id.tv_item_overview);
            holder.releaseDate = (TextView)convertView.findViewById(R.id.tv_item_release_date);
            holder.imagePoster = (ImageView)convertView.findViewById(R.id.img_item_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewTitle.setText(mData.get(position).getTitle());
        String overviewText = mData.get(position).getOverview();
        if (overviewText.length() >= 50) {
            holder.overview.setText(overviewText.substring(0, 50) + " ...");
        } else {
            holder.overview.setText(overviewText);
        }
        try {
            String sReleaseDate = mData.get(position).getRelease_date();
            if ( sReleaseDate != null) {
                Date releaseDate = sdf.parse(mData.get(position).getRelease_date());
                sdf.applyPattern("E, dd MMM yyyy");
                sReleaseDate = sdf.format(releaseDate);

            }
            holder.releaseDate.setText(sReleaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.with(context).load(Uri.parse("http://image.tmdb.org/t/p/w154" + mData.get(position).getPoster_path())).into(holder.imagePoster);
        return convertView;
    }
    private static class ViewHolder {
        TextView textViewTitle;
        TextView overview;
        TextView releaseDate;
        ImageView imagePoster;
    }

}
