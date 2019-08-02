package com.sample.newsapi.helpers;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.newsapi.R;
import com.sample.newsapi.viewmodels.NewsViewModel;

public class BindingAdapterFactory {


    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        CachedImageViewLoader.with(view.getContext()).loadImage(imageUrl, view);
        Log.e("urls", imageUrl + "");
    }

    @BindingAdapter({"onClickHeadLineOpen", "viewModel"})
    public static void onClickHeadLineCardItem(CardView cardView, int position, NewsViewModel newsViewModel) {
        cardView.setOnClickListener(view -> {
            newsViewModel.postClickEventFor(position);
        });
    }

    @BindingAdapter({"textForItem", "context"})
    public static void setSkinToCategoryitem(TextView textView, boolean isSelected, Context context) {
        textView.setTextColor(isSelected ? ContextCompat.getColor(context, R.color.white) : ContextCompat.getColor(context, R.color.colorPrimaryDark));
        textView.setBackgroundDrawable(isSelected ? ContextCompat.getDrawable(context, R.drawable.bg_primarycolor_category_option) : ContextCompat.getDrawable(context, R.drawable.bg_white_category_option));
    }

    @BindingAdapter("loadWebUrl")
    public static void loadWeburl(WebView webView, String url) {
        webView.loadUrl(url);

    }

    @BindingAdapter("webviewClient")
    public static void setWebviewClient(WebView webView, WebViewClient webViewClient) {
        webView.setWebViewClient(webViewClient);

    }
}
