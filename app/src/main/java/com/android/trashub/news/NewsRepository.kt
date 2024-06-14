package com.android.trashub.news

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {
    fun getHealthNews(
        onSuccess: (List<NewsItem>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        ApiClient.apiService.healthNews("skin", "health", "en", ApiConfig.API_KEY)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (response.isSuccessful) {
                        val newsResponse = response.body()
                        if (newsResponse != null) {
                            val articlesItem = newsResponse.articles ?: emptyList()
                            val newsList = articlesItem.mapNotNull { articleItem ->
                                articleItem?.let {
                                    if (!it.title.isNullOrEmpty() && !it.urlToImage.isNullOrEmpty() && !it.url.isNullOrEmpty()) {
                                        NewsItem(it.title!!, it.urlToImage.toString(), it.url!!)
                                    } else {
                                        null
                                    }
                                }
                            }
                            onSuccess(newsList)
                        } else {
                            onFailure("Response body is null")
                        }
                    } else {
                        onFailure("Not Found")
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    onFailure(t.message ?: "Error")
                }
            })
    }
}