package com.gmail.bergrin.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.gmail.bergrin.retrofitdemo.adapters.CountryAdapter;
import com.gmail.bergrin.retrofitdemo.model.CountryInfo;
import com.gmail.bergrin.retrofitdemo.model.Result;
import com.gmail.bergrin.retrofitdemo.service.CountryService;
import com.gmail.bergrin.retrofitdemo.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Result> resultArrayList;
    private CountryAdapter countryAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCountries();
    }

    private Object getCountries() {
        CountryService countryService = RetrofitInstance.getService();
        Call<CountryInfo> call = countryService.getResults();
        call.enqueue(new Callback<CountryInfo>() {
            @Override
            public void onResponse(Call<CountryInfo> call, Response<CountryInfo> response) {
                CountryInfo countryInfo = response.body();
                if (countryInfo != null && countryInfo.getRestResponse() != null) {
                    resultArrayList = (ArrayList<Result>) countryInfo.getRestResponse().getResult();
                    fillRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<CountryInfo> call, Throwable t) {

            }
        });

        return resultArrayList;
    }

    private void fillRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        countryAdapter = new CountryAdapter(resultArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(countryAdapter);
    }
}