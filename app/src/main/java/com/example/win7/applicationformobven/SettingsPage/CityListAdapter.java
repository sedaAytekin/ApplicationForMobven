package com.example.win7.applicationformobven.SettingsPage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.win7.applicationformobven.Response.CityListModel;
import com.example.win7.applicationformobven.ResponseViewModel;
import com.example.win7.applicationformobven.databinding.ListitemCityListBinding;

import java.util.List;

    /**
     * Created by dev on 30.03.2018.
     */

    public class CityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<CityListModel> items;
        private ResponseViewModel viewModel;

        public CityListAdapter(ResponseViewModel viewModel) {
            this.viewModel = viewModel;
        }

        public void setItems(List<CityListModel> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ListingCity(ListitemCityListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ListingCity mHolder = (ListingCity) holder;
            mHolder.cityListBinding.setData(items.get(position));
            mHolder.cityListBinding.cityItemRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        viewModel.selectedCity.setValue(buttonView.getText().toString());
                    }
                }
            });

        }

        @Override
        public int getItemViewType(int position) {

            return 1;
        }

        @Override
        public int getItemCount() {
            if (items != null) {
                return items.size();
            }
            return 0;
        }

        private class ListingCity extends RecyclerView.ViewHolder {
            private ListitemCityListBinding cityListBinding;

            ListingCity(ListitemCityListBinding cityListBinding) {
                super(cityListBinding.getRoot());
                this.cityListBinding = cityListBinding;
            }
        }

}

