package com.sample.newsapi.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.sample.newsapi.datarepository.offlinerepo.database.DBRepo;
import com.sample.newsapi.datarepository.onlinerepo.CloudRepo;
import com.sample.newsapi.models.HeadLine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    CloudRepo cloudRepo;
    @Mock
    DBRepo dbRepo;
    @InjectMocks
    NewsViewModel newsViewModel;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void fetchCloudHeadLines() {
        //Arrange
        List<HeadLine> headLineList = new ArrayList<>();
        headLineList.add(new HeadLine("name1", "title1", "description1","contentUrl1","imageUrl1"));
        MutableLiveData<List<HeadLine>> listLiveData = new MutableLiveData<List<HeadLine>>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listLiveData.postValue(headLineList);
            }
        }).start();

        Mockito.when(cloudRepo.fetchHeadLines("dummyCategory")).thenReturn(listLiveData);

        //Act
        LiveData<List<HeadLine>> liveData = newsViewModel.fetchCloudHeadLines();

        //Assert
        Assert.assertTrue("list data is empty on cloud call",liveData.getValue().size()==listLiveData.getValue().size());

    }

}