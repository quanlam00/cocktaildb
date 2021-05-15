package com.quanlam.thecocktaildb.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quanlam.thecocktaildb.R
import com.quanlam.thecocktaildb.databinding.MainFragmentBinding
import com.quanlam.thecocktaildb.ui.detail.CocktailDetailFragment
import com.quanlam.thecocktaildb.ui.extension.setUpProgressBar

class MainFragment : Fragment(), OnCocktailItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
        const val TAG = "MainFragment"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var alphabetListAdapter: CocktailListAdapter //Full list
    private lateinit var filteredListAdapter: CocktailListAdapter //Filtered list
    private var binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        //Set listener to handle change in search text box
        binding?.searchView?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    //On search bar empty, switch from filtered list to full list
                    binding?.alphabetList?.visibility = View.VISIBLE
                    binding?.filteredList?.visibility = View.INVISIBLE
                    Log.d(TAG, "Switch to full list")
                    viewModel.cancelSearch()
                } else {
                    //Filter list by search entry
                    viewModel.searchCocktailByName(s.toString())
                    Log.d(TAG, "Filter for $s")
                }
            }
        })
        return binding?.root!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Init view model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //Get some cocktails
        viewModel.getMoreCocktails()

        setupRecyclerViews()
        setupInfiniteScrolling()
        setUpProgressBar(viewModel.liveLoadingState)
    }

    private fun setupRecyclerViews() {
        alphabetListAdapter = CocktailListAdapter(viewModel.liveDrinkList.value!!, this)
        filteredListAdapter = CocktailListAdapter(viewModel.liveFilteredList.value!!, this)
        with(binding?.alphabetList) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.adapter = alphabetListAdapter
        }
        with(binding?.filteredList) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.adapter = filteredListAdapter
        }
        val alphabetListObserver = Observer<List<CocktailListItem>> {
            Log.d(TAG, "New changes to full list")
            alphabetListAdapter.notifyItemRangeChanged(0,
                viewModel.liveDrinkList.value?.size ?: 0)
            binding?.alphabetList?.visibility = View.VISIBLE
            binding?.filteredList?.visibility = View.INVISIBLE
        }
        val filteredListObserver = Observer<List<CocktailListItem>> {
            if (!binding?.searchView?.text.isNullOrEmpty()) {
                Log.d(TAG, "New change to filtered list")
                filteredListAdapter.notifyDataSetChanged()
                binding?.alphabetList?.visibility = View.INVISIBLE
                binding?.filteredList?.visibility = View.VISIBLE
            }
        }
        viewModel.liveDrinkList.observe(viewLifecycleOwner, alphabetListObserver)
        viewModel.liveFilteredList.observe(viewLifecycleOwner, filteredListObserver)
    }

    private fun setupInfiniteScrolling() {
        binding?.alphabetList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getMoreCocktails()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    //On list item clicked
    override fun onClick(item: CocktailListItem?) {
        if (!item?.id.isNullOrEmpty()) {
            val detailFragment = CocktailDetailFragment.newInstance(item?.id!!)
            if (activity != null) {
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, detailFragment, "cocktailDetail")
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}