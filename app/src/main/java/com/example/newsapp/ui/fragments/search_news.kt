package com.example.newsapp.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter

import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class search_news : Fragment(R.layout.fragment_search_news) {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var binding :FragmentSearchNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = DataBindingUtil.inflate(inflater , R.layout.fragment_search_news , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).viewModel

        newsAdapter = NewsAdapter()

        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("Article" , it)
            }

            findNavController().navigate(R.id.action_search_news_to_news_webview, bundle)
        }


        var job: Job? = null

        binding.etSearch.addTextChangedListener { editable->

            job?.cancel()
            job = MainScope().launch{
                delay(500)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        newsViewModel.getSearchedNews(editable.toString())
                    }
                }
            }

        }

        newsViewModel.searchedNews.observe(viewLifecycleOwner , Observer { response->

            if(response is Resource.success){
                hideProgressBar()
                response.data?.let{newsResponse->
                    newsAdapter.differ.submitList(newsResponse.articles)
                }

            }
            else if(response is Resource.Error){
                hideProgressBar()
                response.message?.let{message->
                    Log.d(TAG , "An error Occured: ${message}")

                }

            }
            else{
                showProgressBar()
            }


        })


    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setUpRecyclerView() {
        binding.rvSearchNews.apply {
            newsAdapter = NewsAdapter()
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}
