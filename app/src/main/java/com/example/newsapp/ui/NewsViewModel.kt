package com.example.newsapp.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Constants.Companion.API_KEY
import com.example.newsapp.util.Article
import com.example.newsapp.util.Resource
import com.example.newsapp.util.newsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository : NewsRepository): ViewModel() {


    val breakingNews :MutableLiveData<Resource<newsResponse>?> = MutableLiveData()
    // a MutableLiveData which will be used for storing the varying response
    val pageNumber = 1


    val searchedNews:MutableLiveData<Resource<newsResponse>?> = MutableLiveData()
    val Search_pageNumber =1

    init{
        getBreakingNews("us")
    }


    fun getBreakingNews(CountryCode :String) = viewModelScope.launch{
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(CountryCode , pageNumber)
        breakingNews.postValue(handleBreakingNews(response))

    }

    private fun handleBreakingNews(response: Response<newsResponse>):Resource<newsResponse>{
        if(response.isSuccessful){
            response.body()?.let{
                result->
                return Resource.success(result)
            }

        }
        Log.d(TAG, "Error: ${response.code()} - ${response.message()}")
            return Resource.Error(response.message())


    }


    // lets do for the search news fragment
    fun getSearchedNews(SearchQuery:String) = viewModelScope.launch{
        searchedNews.postValue(Resource.Loading())
        val searched_response = newsRepository.searchNews(SearchQuery, pageNumber)
            searchedNews.postValue(handleSearchedNews(searched_response))

    }

    private fun handleSearchedNews(response: Response<newsResponse>): Resource<newsResponse>? {
        if(response.isSuccessful){
            response.body()?.let{
                    result->
                return Resource.success(result)
            }

        }
        Log.d(TAG, "Error: ${response.code()} - ${response.message()}")
        return Resource.Error(response.message())

    }


    // for the fab in newsWebView
    fun saved_news(article: Article) =viewModelScope.launch{
        newsRepository.saveNews(article)
    }

    fun deleteed_news(article: Article)  = viewModelScope.launch{
        newsRepository.deleteNews(article)
    }

    fun getSavedNews() = newsRepository.getAllNewsArticles()

}