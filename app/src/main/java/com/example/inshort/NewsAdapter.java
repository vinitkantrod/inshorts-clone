package com.example.inshort;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inshort.dtos.NewsDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    List<NewsDto> newsDtoList;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "NewsPrefs" ;

    public NewsAdapter(Context c, List<NewsDto> n) {
        this.context = c;
        this.newsDtoList = n;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsDto n = newsDtoList.get(position);
        if (n != null) {
            new DownloadImageTask(holder.mImageView)
                    .execute(n.getUrlToImage());
            holder.mTitleTxtView.setText(n.getTitle());
            holder.mBodyTxtView.setText(n.getDescription());
            holder.mAuthorTxtView.setText(n.getAuthor());
            holder.mDateTxtView.setText(n.getPublishedAt());
        }
        updateSharedPref(n.getUrl());
    }

    @Override
    public int getItemCount() {
        if (newsDtoList != null) {
            return newsDtoList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTitleTxtView;
        TextView mBodyTxtView;
        TextView mAuthorTxtView;
        TextView mDateTxtView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.news_image_view);
            mTitleTxtView = itemView.findViewById(R.id.news_title);
            mBodyTxtView = itemView.findViewById(R.id.news_body);
            mAuthorTxtView = itemView.findViewById(R.id.news_author);
            mDateTxtView = itemView.findViewById(R.id.news_date);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.i("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void updateSharedPref(String url) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("url", url);
        editor.commit();
    }

    public void setNewsList(List<NewsDto> newsList) {
        this.newsDtoList = newsList;
        notifyDataSetChanged();
    }
}
