package com.sample.newsapi.views;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.sample.newsapi.R;
import com.sample.newsapi.databinding.ActivityNewsDetailBinding;
import com.sample.newsapi.models.HeadLine;
import com.sample.newsapi.viewmodels.NewsViewModel;

import javax.inject.Inject;

public class NewsDetailActivity extends BaseActivity<NewsViewModel> {

    ActivityNewsDetailBinding activityNewsDetailBinding;
    @Inject
    NewsViewModel newsViewModel;
    @Override
    public NewsViewModel getViewModel() {
        return newsViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNewsDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);

        if (getIntent()!=null){
            HeadLine headLine = (HeadLine) getIntent().getSerializableExtra("headLine");
            activityNewsDetailBinding.setHeadLine(headLine);
        }
        activityNewsDetailBinding.setViewmodel(newsViewModel);
        activityNewsDetailBinding.executePendingBindings();
        setSupportActionBar(activityNewsDetailBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    void onNetworkConnected() {
        onRestart();
    }

    @Override
    void onNetworkDisconnected() {
        Snackbar.make(activityNewsDetailBinding.cordinatorLayoutParent, R.string.internet_error, Snackbar.LENGTH_LONG)
                .show();
    }

}
