package com.example.newsapp.ui

import RetrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.util.Article

class NewsRepository(
    val db:ArticleDatabase
){
    suspend fun getBreakingNews(countryCode:String , pageNumber:Int) =
        RetrofitInstance.api.getBreakingNews(countryCode , pageNumber)

    suspend fun searchNews(searchQuery:String , pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery,pageNumber)

    suspend fun saveNews(article: Article) = db.ArticleDao().insert(article)

    suspend fun deleteNews(article: Article) = db.ArticleDao().deleteArticle(article)

    fun getAllNewsArticles()=db.ArticleDao().getAllArticle()

}
