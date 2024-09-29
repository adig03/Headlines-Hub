package com.example.newsapp.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Resource


class breaking_news : Fragment(R.layout.fragment_breaking_news) {

private lateinit var newsViewModel: NewsViewModel
private lateinit var binding: FragmentBreakingNewsBinding
private lateinit var newsAdapter: NewsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            binding = DataBindingUtil.inflate(inflater , R.layout.fragment_breaking_news , container , false)
            return binding.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel = (activity as NewsActivity).viewModel

        newsAdapter = NewsAdapter()

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("Article" , it)
            }

            findNavController().navigate(R.id.action_breaking_news_to_news_webview , bundle)
        }

        newsViewModel.breakingNews.observe(viewLifecycleOwner,Observer  {response->
            if (response is Resource.success) {
                hideProgressBar()

                response.data?.let{newsResponse->
                    newsAdapter.differ.submitList(newsResponse.articles)
                }
            }
            else if (response is Resource.Error) {
                hideProgressBar()
                response.message?.let{message->
                    Log.d(TAG , "An error Occured : $message")
                }
            }
            else if (response is Resource.Loading) {
                showProgressBar()
            }
            else if (response == null) TODO()
        })
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.GONE
    }

    private fun setupRecyclerView() {

        binding.rvBreakingNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}
