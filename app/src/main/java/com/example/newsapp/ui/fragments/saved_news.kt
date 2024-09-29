package com.example.newsapp.ui.fragments

import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter

import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class saved_news() : Fragment(R.layout.fragment_saved_news) {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSavedNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel = (activity as NewsActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_saved_news , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUprecylderView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("Article" , it)
            }

            findNavController().navigate(R.id.action_saved_news_to_news_webview , bundle)
        }

        val itemTouchHelperCallback = object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
             return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                newsViewModel.deleteed_news(article)
                Snackbar.make(view , "Successfully deleted Article", Snackbar.LENGTH_LONG).apply{
                    setAction("Undo"){
                        newsViewModel.saved_news(article)
                    }
                    show()
                }
            }

        }
ItemTouchHelper(itemTouchHelperCallback).apply {
    attachToRecyclerView(binding.rvSavedNews)
}



        newsViewModel.getSavedNews().observe(viewLifecycleOwner  , Observer {
            if(it.isNotEmpty()){
                binding.CardView.visibility = View.INVISIBLE
                binding.rvSavedNews.visibility = View.VISIBLE
                newsAdapter.differ.submitList(it)
            }
            else{
                binding.CardView.visibility  = View.VISIBLE
                binding.rvSavedNews.visibility =View.INVISIBLE


            }
        })

    }



    private fun setUprecylderView() {
        newsAdapter = NewsAdapter()

        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }


}