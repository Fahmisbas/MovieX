package com.acsl.moviex.ui.tabs.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.factory.ViewModelFactory
import com.acsl.moviex.ui.adapter.ListAdapter
import com.acsl.moviex.ui.detail.DetailActivity
import com.acsl.moviex.ui.navigation.HomeViewModel
import com.acsl.moviex.util.Status
import com.acsl.moviex.util.gone
import com.acsl.moviex.util.visible
import kotlinx.android.synthetic.main.fragment_tv_shows.*


class TvShowFragment : Fragment() {

    private var listAdapter = ListAdapter()
    private lateinit var viewModel: HomeViewModel

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

        observeDataChanges()
    }

    private fun initRecyclerView() {
        rv_tabs_tv_shows.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
        onItemClickListener()
    }

    private fun onItemClickListener() {
        listAdapter.onItemClickCallback = object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataEntity) {
                Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_DATA_DETAIL, data)
                    startActivity(this)
                }
            }
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    private fun observeDataChanges() {
        viewModel.getAllTvShows().observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })
        observeNetworkState()
    }

    private fun observeNetworkState() {
        viewModel.getTvShowNetworkState().observe(viewLifecycleOwner, { networkState ->
            when (networkState.status) {
                Status.RUNNING -> {
                    if (viewModel.tvShowListIsEmpty()) {
                        runningView()
                    }
                }
                Status.SUCCESS -> {
                    successView()
                }
                Status.FAIL -> {
                    if (viewModel.tvShowListIsEmpty()) {
                        failedView()
                    }
                }
            }
        })
    }


    private fun runningView() {
        load_viewpager.visible()
        img_error_tv_show.gone()
        tv_error_tv_show.gone()
    }

    private fun successView() {
        load_viewpager.gone()
        img_error_tv_show.gone()
        tv_error_tv_show.gone()
    }

    private fun failedView() {
        load_viewpager.gone()
        img_error_tv_show.visible()
        tv_error_tv_show.visible()
    }

    companion object {
        fun newInstance(): TvShowFragment {
            return TvShowFragment()
        }
    }
}