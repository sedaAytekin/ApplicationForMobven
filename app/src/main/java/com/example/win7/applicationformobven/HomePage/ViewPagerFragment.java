package com.example.win7.applicationformobven.HomePage;

import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win7.applicationformobven.R;
import com.example.win7.applicationformobven.ViewPagerAdapter;
import com.example.win7.applicationformobven.databinding.SettingsPageFragmentBinding;
import com.example.win7.applicationformobven.databinding.ViewpagerFragmentBinding;

public class ViewPagerFragment extends Fragment {
    ViewpagerFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater, R.layout.viewpager_fragment, null, false);

      //  binding.circleIndicatorNewsFeed.setViewPager(binding.feedPhotos);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager(binding.pager);


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

            HomePageFragment favoritesFragment = new HomePageFragment();
            adapter.addFragment(favoritesFragment,".");

            HomePageFragment getFavoredByFragment = new HomePageFragment();
            adapter.addFragment(getFavoredByFragment,".");


        viewPager.setAdapter(adapter);
        binding.indicator.setViewPager(viewPager);
    }
}
