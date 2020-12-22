package com.example.inshort;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inshort.dtos.NewsDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String TAG = "NewsAdapter";

    Context context;
    List<NewsDto> newsDtoList;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "NewsPrefs" ;
    String imagePath = "";
    
    public ItemClickListener itemClickListener;
    
    public NewsAdapter(Context c, List<NewsDto> n, ItemClickListener l) {
        this.context = c;
        this.newsDtoList = n;
        this.itemClickListener = l;
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
        imagePath = n.getUrlToImage();
        if (n != null) {
            new DownloadImageTask(holder.mImageView)
                    .execute(imagePath);
            holder.mTitleTxtView.setText(n.getTitle());
            holder.mBodyTxtView.setText(n.getDescription());
            holder.mAuthorTxtView.setText(n.getAuthor());
            holder.mDateTxtView.setText(n.getPublishedAt());
        }
        updateSharedPref(n.getUrl());
//        holder.newsBottomLayout.setVisibility(displayBottomLayout == true ? View.VISIBLE : View.GONE );
    }

    @Override
    public int getItemCount() {
        if (newsDtoList != null) {
            return newsDtoList.size();
        }
        return 0;
    }

    public interface ItemClickListener {
        void onClicked();
        void openUrl(String url);
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Boolean displayBottomLayout = true;
        public boolean displayImage =  false;
        ImageView mImageView;
        TextView mTitleTxtView;
        TextView mBodyTxtView;
        TextView mAuthorTxtView;
        TextView mDateTxtView;
        LinearLayout newsBottomLayout;
        LinearLayout bottomLayout;
        ImageView shareView;
        ImageView bookmarkView;
        TextView readMoreTxtView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.news_image_view);
            mTitleTxtView = itemView.findViewById(R.id.news_title);
            mBodyTxtView = itemView.findViewById(R.id.news_body);
            mAuthorTxtView = itemView.findViewById(R.id.news_author);
            mDateTxtView = itemView.findViewById(R.id.news_date);
            newsBottomLayout = itemView.findViewById(R.id.news_bottom_layout);
            bottomLayout = itemView.findViewById(R.id.news_bottom_layout);
            bottomLayout.setVisibility(View.VISIBLE);
            shareView = itemView.findViewById(R.id.share_news);
            shareView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, mTitleTxtView != null ? mTitleTxtView.getText() : "Inshorts - 60 word news summary");
                    context.startActivity(Intent.createChooser(intent, "share Quote via"));
                }
            });
            readMoreTxtView = itemView.findViewById(R.id.read_more);
            readMoreTxtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsDto n = newsDtoList.get(getAdapterPosition());
                    itemClickListener.openUrl(n.getUrlToImage());
                }
            });
            bookmarkView = itemView.findViewById(R.id.bookmark_news);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            displayBottomLayout = !displayBottomLayout;
            bottomLayout.setVisibility(displayBottomLayout == true ? View.VISIBLE : View.GONE);
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
                Log.i("TAG", "doInBackground: " + urldisplay);
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
            bmImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    private void updateSharedPref(String url) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("url", url);
        Log.i("TAG", "updateSharedPref: " + url);
        editor.commit();
    }

    public void setNewsList(List<NewsDto> newsList) {
        this.newsDtoList = newsList;
        notifyDataSetChanged();
    }

}
