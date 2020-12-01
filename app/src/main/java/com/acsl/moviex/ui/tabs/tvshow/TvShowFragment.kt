package com.acsl.moviex.ui.tabs.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acsl.moviex.R
import com.acsl.moviex.factory.ViewModelFactory
import com.acsl.moviex.ui.adapter.ListAdapter
import kotlinx.android.synthetic.main.fragment_tv_shows.*


class TvShowFragment : Fragment() {

    private var listAdapter = ListAdapter()
    private lateinit var viewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        observerChanges()

    }


    private fun initRecyclerView() {
        rv_tabs.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
    }

    private fun observerChanges() {
        viewModel.tvShowPagedList.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })
    }


    companion object {

        fun newInstance(
        ): TvShowFragment {
            return TvShowFragment()
        }
    }
}