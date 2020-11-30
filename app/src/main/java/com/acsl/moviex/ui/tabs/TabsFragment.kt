package com.acsl.moviex.ui.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.ui.adapter.ListMovieAdapter
import com.acsl.moviex.ui.detail.DetailActivity
import com.acsl.moviex.ui.detail.DetailActivity.Companion.EXTRA_MOVIE_DETAIL
import com.acsl.moviex.vo.NetworkState
import kotlinx.android.synthetic.main.fragment_tabs.*

class TabsFragment(
    private var listData: PagedList<DataEntity>,
) : Fragment() {

    private var adapter = ListMovieAdapter()
    private lateinit var netState: NetworkState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_tabs.adapter = adapter

        var index = 0
        if (arguments != null) {
            index = arguments?.getInt(SECTION_NUMBER, 0) as Int
            netState = arguments?.getParcelable<NetworkState>(NETWORK_STATE) as NetworkState
        }
        setList(index)

    }

    private fun setList(index: Int) {
        if (index == 0) {
            updateList(listData)
        } else {
            updateList(listData)

        }
    }

    private fun updateList(list: PagedList<DataEntity>) {
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
        adapter.setNetworkState(netState)
        onItemClicked()

    }

    private fun onItemClicked() {
        adapter.onItemClickCallback = object : ListMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataEntity) {
                Intent(context, DetailActivity::class.java).apply {
                    putExtra(EXTRA_MOVIE_DETAIL, data)
                    startActivity(this)
                }
            }
        }
    }

    companion object {
        private const val SECTION_NUMBER = "section_number"
        private const val NETWORK_STATE = "network_state"


        fun newInstance(
            index: Int,
            listData: PagedList<DataEntity>,
            networkState: NetworkState
        ): TabsFragment {
            val fragment = TabsFragment(listData)
            val bundle = Bundle()
            bundle.putParcelable(NETWORK_STATE, networkState)
            bundle.putInt(SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}