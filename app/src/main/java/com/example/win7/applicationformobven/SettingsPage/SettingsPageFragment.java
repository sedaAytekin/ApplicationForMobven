package com.example.win7.applicationformobven.SettingsPage;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.win7.applicationformobven.R;
import com.example.win7.applicationformobven.ResponseViewModel;
import com.example.win7.applicationformobven.databinding.SettingsPageFragmentBinding;


public class SettingsPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SettingsPageFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.settings_page_fragment, null, false);
        ResponseViewModel viewModel = ViewModelProviders.of(getActivity()).get(ResponseViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerview'ı hızlandırmak için cache açıyoruz.
        binding.cityListRV.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        CityListAdapter adapter = new CityListAdapter(viewModel);
        binding.cityListRV.setAdapter(adapter);
        binding.cityListRV.setLayoutManager(layoutManager);
        binding.cityListRV.setItemAnimator(null);

        viewModel.getCityList().observe(this,adapter::setItems);

        viewModel.getBatteryState().observe(this, aFloat -> {
            if(aFloat!= null){
                //burada değeri yazıcaz.
                binding.progressbar.setProgress(aFloat.intValue());
                binding.batteryPercentageTV.setText(String.valueOf(aFloat) + " %");

            }
        });
        viewModel.getPositionDefault().observe(this, i -> {
            if(i!= null){
                ((LinearLayoutManager) binding.cityListRV.getLayoutManager()).scrollToPositionWithOffset(i-1,0);

            }
        });
        //wifi in olup olmama durumuna göre switch on-off modu ayarlanıyor.
        viewModel.getIsWifi().observe(this, aBoolean -> {
            if(aBoolean!= null){
              //wifi on-off ayarlanıyor ve kullanıcı değişmemesi için swipe'ı kapatıyoruz.
                binding.wifiSW.setChecked(aBoolean);
                binding.wifiSW.setClickable(false);

            }
        });


        binding.setHandler(viewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

}
