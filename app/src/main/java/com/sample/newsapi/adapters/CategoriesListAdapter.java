package com.sample.newsapi.adapters;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.newsapi.R;
import com.sample.newsapi.databinding.LayoutCategoryItemBinding;
import com.sample.newsapi.viewmodels.NewsViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * fills headlines categories filters at top of the headlines list
 */
public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ImageItemHolder> {

    private ArrayList<String> categoriesList;
    private NewsViewModel newsViewModel;

    @Inject
    public CategoriesListAdapter(NewsViewModel viewModel) {
        this.categoriesList = viewModel.getCategoriesList();
        this.newsViewModel = viewModel;
    }

    public ArrayList<String> getCategoriesList() {
        return categoriesList;
    }

    @NonNull
    @Override
    public ImageItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ImageItemHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.layout_category_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemHolder imageItemHolder, int position) {
        imageItemHolder.layoutCategoryItemBinding.setItem(categoriesList.get(position));
        imageItemHolder.layoutCategoryItemBinding.setPosition(position);
        imageItemHolder.layoutCategoryItemBinding.setViewmodel(newsViewModel);
        imageItemHolder.layoutCategoryItemBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


    public class ImageItemHolder extends RecyclerView.ViewHolder {

        LayoutCategoryItemBinding layoutCategoryItemBinding;

        public ImageItemHolder(LayoutCategoryItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.layoutCategoryItemBinding = itemBinding;
            this.layoutCategoryItemBinding.getRoot().setOnClickListener(view->{
                newsViewModel.setCategorySelected(getAdapterPosition());
                notifyDataSetChanged();
                newsViewModel.fetchCloudHeadLines();
                newsViewModel.getIsResetOnNewSync().postValue(true);
            });
        }
    }
}
