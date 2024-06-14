package com.android.trashub.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    private val newsRepository = NewsRepository()
    private val newList = MutableLiveData<List<NewsItem>>()
    val newsList: LiveData<List<NewsItem>> = newList

    fun fetchNews() {
        newsRepository.getHealthNews(
            onSuccess = { newsList -> newList.postValue(newsList) },
            onFailure = { errorMessage -> }
        )
    }
}