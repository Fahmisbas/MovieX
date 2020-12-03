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
import com.acsl.moviex.util.gone
import com.acsl.moviex.util.visible
import com.acsl.moviex.vo.Status
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
        observerDataChanges()

    }

    private fun initRecyclerView() {
        rv_tabs.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
        onItemClickListener()
    }

    private fun onItemClickListener() {
        listAdapter.onItemClickCallback = object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataEntity) {
                Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, data)
                    startActivity(this)
                }
            }
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
    }

    private fun observerDataChanges() {
        viewModel.tvShowPagedList.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })
        observeNetworkState()
    }

    private fun observeNetworkState() {
        viewModel.tvShowNetworkState.observe(viewLifecycleOwner, { networkState ->
            when (networkState.status) {
                Status.RUNNING -> {
                    if (viewModel.tvListIsEmpty()) {
                        runningView()
                    }
                }
                Status.SUCCESS -> {
                    successView()
                }
                Status.FAIL -> {
                    if (viewModel.tvListIsEmpty()) {
                        failedView()
                    }
                }
            }
        })
    }


    private fun runningView() {
        load_viewpager.visible()
        img_error.gone()
        tv_error.gone()
    }

    private fun successView() {
        load_viewpager.gone()
        img_error.gone()
        tv_error.gone()
    }

    private fun failedView() {
        load_viewpager.gone()
        img_error.visible()
        tv_error.visible()
    }

    companion object {
        fun newInstance(): TvShowFragment {
            return TvShowFragment()
        }
    }
}