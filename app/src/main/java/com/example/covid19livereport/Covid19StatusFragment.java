package com.example.covid19livereport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid19livereport.databinding.FragmentCovid19StatusBinding;
import com.example.covid19livereport.viewmodels.Covid19ViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Covid19StatusFragment extends Fragment {
    private FragmentCovid19StatusBinding binding;
    private Covid19ViewModel viewModel;

    public Covid19StatusFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.countrysearch_menu, menu);
        final androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menu
                .findItem(R.id.item_search).getActionView();
        searchView.setQueryHint("Search a city");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query!=null){
                    viewModel.setCity(query);
                    viewModel.loadData();
                    searchView.setQuery(null,false);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCovid19StatusBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(Covid19ViewModel.class);
        viewModel.loadData();
        viewModel.getResponseInfoLiveData().observe(getViewLifecycleOwner(), covid19ResponseModel -> {
            binding.countryTV.setText(covid19ResponseModel.getCountry());
            Picasso.get().load(covid19ResponseModel.getCountryInfo().getFlag())
                    .fit()
                    .into(binding.flagTV);
            binding.updateTimeTV.setText(new SimpleDateFormat("MMM dd, yyyy").
                    format(new Date(covid19ResponseModel.getUpdated())));
            binding.caseTodayTV.setText(String.valueOf(covid19ResponseModel.getTodayCases()));
            binding.deathTodayTV.setText(String.valueOf(covid19ResponseModel.getTodayDeaths()));
            binding.recoverTodayTV.setText(String.valueOf(covid19ResponseModel.getTodayRecovered()));
            binding.totalcaseTV.setText(String.valueOf(covid19ResponseModel.getCases()));
            binding.totaldeathTV.setText(String.valueOf(covid19ResponseModel.getDeaths()));
            binding.totalrecoverTodayTV.setText(String.valueOf(covid19ResponseModel.getRecovered()));
        });


        return binding.getRoot();
    }
}