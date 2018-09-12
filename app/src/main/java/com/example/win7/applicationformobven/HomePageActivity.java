package com.example.win7.applicationformobven;

import android.arch.lifecycle.ViewModel;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.win7.applicationformobven.HomePage.HomePageFragment;
import com.example.win7.applicationformobven.SettingsPage.SettingsPageFragment;
import com.example.win7.applicationformobven.databinding.ActivityHomePageBinding;

public class HomePageActivity extends AppCompatActivity {
    private ResponseViewModel activityViewModel;
    ActivityHomePageBinding binding;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_page);

        fragmentManager = getSupportFragmentManager();

        activityViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                //noinspection unchecked
                return (T) new ResponseViewModel(getApplicationContext(), fragmentManager);
            }
        }).get(ResponseViewModel.class);

        HomePageFragment homePageFragment = new HomePageFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, homePageFragment);
        transaction.commit();

        binding.setHandler(activityViewModel);
    }

}
