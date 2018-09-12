package com.example.win7.applicationformobven.HomePage;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win7.applicationformobven.R;
import com.example.win7.applicationformobven.ResponseViewModel;
import com.example.win7.applicationformobven.databinding.HomePageFragmentBinding;
import com.squareup.picasso.Picasso;

public class HomePageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomePageFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_page_fragment, null, false);
        ResponseViewModel viewModel = ViewModelProviders.of(getActivity()).get(ResponseViewModel.class);

        viewModel.getMinimumTemp().observe(this, aDouble -> {
            if(aDouble!= null){
                //burada değeri yazıcaz.
                binding.minTempDegreeTV.setText(aDouble.toString() + " \u2103");
                binding.minDegreeTV.setText(aDouble.toString() + " \u2103");
            }
        });
        viewModel.getMaximumTemp().observe(this, aDouble -> {
            if(aDouble!= null){
                //burada değeri yazıcaz.
                binding.maxTempDegreeTV.setText(" / " + aDouble.toString() + " \u2103");
                binding.maxDegreeTV.setText(aDouble.toString() + " \u2103");
            }
        });
        viewModel.getCity().observe(this, s -> {
            if(s!= null){
                //burada değeri yazıcaz.
                binding.cityTV.setText(s);
            }
        });
        viewModel.getDescription().observe(this, s -> {
            if(s!= null){
                //burada değeri yazıcaz.
                binding.descriptionTV.setText(" / " + s);
            }
        });
        viewModel.getWeatherIcon().observe(this, s -> {
            if(s!= null){
                //burada değeri yazıcaz.
                Picasso.with(getActivity())
                        .load(s)
                        .into(binding.iconIV);
            }
        });
        return binding.getRoot();
    }
}
