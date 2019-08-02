package com.sample.newsapi.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * creating a unique ViewModel Factory that provides all kind of ViewModel we need based from the modules inside the component.
 * @param <V>
 */
public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {
    private V viewModel;

    public ViewModelProviderFactory(V viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.getClass())) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
