package com.example.inshort.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inshort.NewsAdapter;
import com.example.inshort.NewsInterface;
import com.example.inshort.NewsRetrofit;
import com.example.inshort.R;
import com.example.inshort.dtos.NewsApiRespDto;
import com.example.inshort.dtos.NewsDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {


    private static final String TAG = "NewsFragment";
    RecyclerView rc;
    NewsAdapter newsAdapter;
    NewsInterface newsInterface;
    NewsRetrofit newsRetrofit;
    NewsApiRespDto newsDtoList;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        Log.i(TAG, "onCreateView: News Fragment");
        newsRetrofit = new NewsRetrofit();
        newsInterface = newsRetrofit.getNewsInterface();
        newsInterface.getNewsList().enqueue(newsCallback);
        List<NewsDto> data = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(), data);
        rc = rootView.findViewById(R.id.news_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        SnapHelper snapHelper = new PagerSnapHelper();
        rc.setLayoutManager(linearLayoutManager);
        snapHelper.attachToRecyclerView(rc);

        rc.setAdapter(newsAdapter);

        return rootView;
    }

    Callback<NewsApiRespDto> newsCallback = new Callback<NewsApiRespDto>() {
        @Override
        public void onResponse(Call<NewsApiRespDto> call, Response<NewsApiRespDto> response) {
            if (response.isSuccessful()) {
                newsDtoList = response.body();
                Log.i(TAG, "onResponse: " + newsDtoList.toString());
                newsAdapter.setNewsList(newsDtoList.getArticles());
            } else {
                Log.i(TAG, "onResponse: 111111");
            }
        }

        @Override
        public void onFailure(Call<NewsApiRespDto> call, Throwable t) {
            t.printStackTrace();
        }
    };
}