package com.sample.newsapi.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.View;

import com.sample.newsapi.R;
import com.sample.newsapi.adapters.CategoriesListAdapter;
import com.sample.newsapi.adapters.HeadLinesListAdapter;
import com.sample.newsapi.databinding.ActivityMainBinding;
import com.sample.newsapi.viewmodels.NewsViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends BaseActivity<NewsViewModel> {

    ActivityMainBinding activityMainBinding;
    @Inject
    HeadLinesListAdapter headLinesListAdapter;
    @Inject
    CategoriesListAdapter categoriesListAdapter;
    @Inject
    @Named("vertical")
    LinearLayoutManager linearLayoutManagerVertical;


    @Inject
    @Named("horizontal")
    LinearLayoutManager linearLayoutManagerHorizontal;
    @Inject
    NewsViewModel newsViewModel;


    @Override
    public NewsViewModel getViewModel() {
        return newsViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewmodel(newsViewModel);
        activityMainBinding.executePendingBindings();
        setSupportActionBar(activityMainBinding.toolbar);
        initRecyclers();
        subscribeObservers();
    }


    void initRecyclers() {
        activityMainBinding.contentMain.recyclerforHeadlinesList.setHasFixedSize(true);
        activityMainBinding.contentMain.recyclerforHeadlinesList.setLayoutManager(linearLayoutManagerVertical);
        activityMainBinding.contentMain.recyclerforHeadlinesList.setAdapter(headLinesListAdapter);

        activityMainBinding.contentMain.recyclerViewForCategories.setHasFixedSize(true);
        activityMainBinding.contentMain.recyclerViewForCategories.setLayoutManager(linearLayoutManagerHorizontal);
        activityMainBinding.contentMain.recyclerViewForCategories.setAdapter(categoriesListAdapter);
    }

    void subscribeObservers() {

        //fetches data from local db in default
        newsViewModel.fetchLocalHeadLines().observe(this, placeList -> {
            if (!placeList.isEmpty()) {
                headLinesListAdapter.setHeadLineItems(placeList);
                headLinesListAdapter.notifyDataSetChanged();
            }
            newsViewModel.getIsHeadLinesLoadedObservable().set(true);

            activityMainBinding.contentMain.tvForEmptyListMsg.setVisibility(placeList.isEmpty() ? View.VISIBLE : View.GONE);
        });
        newsViewModel.getIsResetOnNewSync().observe(this, aBoolean -> {
            if (aBoolean) {
                activityMainBinding.contentMain.recyclerforHeadlinesList.scrollToPosition(0);
                newsViewModel.getIsResetOnNewSync().setValue(false);//setting to default
            }
        });

        newsViewModel.getHeadLineClickMutableLiveData().observe(this, clickedItemPosition ->
        {
            if (clickedItemPosition != null && clickedItemPosition!=-1) {
                Intent intent = new Intent(this, NewsDetailActivity.class);
                intent.putExtra("headLine", headLinesListAdapter.getHeadLineItems().get(clickedItemPosition));
                startActivity(intent);
                newsViewModel.getHeadLineClickMutableLiveData().setValue(-1);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    void onNetworkConnected() { // on network connected we will sync data from cloud repo
        newsViewModel.fetchCloudHeadLines().observe(this, headLineList -> {
            if (!headLineList.isEmpty()) {
                newsViewModel.insertIntoDB(headLineList);
            }

        });
    }

    @Override
    void onNetworkDisconnected() {
        Snackbar.make(activityMainBinding.cordinatorLayoutParent, R.string.internet_error, Snackbar.LENGTH_LONG)
                .show();
        activityMainBinding.contentMain.tvForEmptyListMsg.setVisibility(headLinesListAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
