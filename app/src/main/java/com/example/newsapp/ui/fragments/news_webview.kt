package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsWebviewBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class news_webview : Fragment(R.layout.fragment_news_webview) {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding: FragmentNewsWebviewBinding

    private val args: news_webviewArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_news_webview , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as NewsActivity).viewModel
        val article =  args.Article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }


        binding.fab.setOnClickListener{
            newsViewModel.saved_news(article)

            Snackbar.make(view , "Article saved Successfully" , Snackbar.LENGTH_SHORT).show()
        }


    }


}