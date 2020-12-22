package com.example.inshort;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.inshort.fragments.CategoryFragment;
import com.example.inshort.fragments.NewsFragment;
import com.example.inshort.fragments.WebViewFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return CategoryFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return NewsFragment.newInstance();
            case 2: // Fragment # 1 - This will show SecondFragment
                return WebViewFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
