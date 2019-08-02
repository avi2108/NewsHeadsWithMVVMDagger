package com.sample.newsapi.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sample.newsapi.R;
import com.sample.newsapi.databinding.LayoutHeadlineItemBinding;
import com.sample.newsapi.models.HeadLine;
import com.sample.newsapi.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * fills headlines list items
 */
public class HeadLinesListAdapter extends RecyclerView.Adapter<HeadLinesListAdapter.ImageItemHolder> {

    private ArrayList<HeadLine> headLineItems;
    private NewsViewModel newsViewModel;

    @Inject
    public HeadLinesListAdapter(NewsViewModel viewModel) {
        this.headLineItems = new ArrayList<>();
        this.newsViewModel = viewModel;
    }

    public void setHeadLineItems(List<HeadLine> headLineItems) {
        this.headLineItems = (ArrayList<HeadLine>) headLineItems;
    }

    public ArrayList<HeadLine> getHeadLineItems() {
        return headLineItems;
    }

    @NonNull
    @Override
    public ImageItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ImageItemHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.layout_headline_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemHolder imageItemHolder, int position) {
        imageItemHolder.layoutHeadlineItemBinding.setItem(headLineItems.get(position));
        imageItemHolder.layoutHeadlineItemBinding.setPosition(position);
        imageItemHolder.layoutHeadlineItemBinding.setViewModel(newsViewModel);
        imageItemHolder.layoutHeadlineItemBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return headLineItems.size();
    }


    public class ImageItemHolder extends RecyclerView.ViewHolder {

        LayoutHeadlineItemBinding layoutHeadlineItemBinding;

        public ImageItemHolder(LayoutHeadlineItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.layoutHeadlineItemBinding = itemBinding;
        }
    }
}
