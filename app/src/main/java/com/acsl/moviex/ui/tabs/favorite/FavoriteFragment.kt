package com.acsl.moviex.ui.tabs.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.ui.adapter.OrdinaryAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

    private var adapter = OrdinaryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_tabs.adapter = adapter
        if (arguments != null) {
            val index = arguments?.getParcelableArrayList<DataEntity>(EXTRA_TV_SHOWS)
            if (index != null) {
                adapter.updateList(index)
            }
        }
    }


    companion object {
        private const val EXTRA_TV_SHOWS = "section_number"

        fun newInstance(
            tvShows: List<DataEntity>,
        ): FavoriteFragment {
            val fragment = FavoriteFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(EXTRA_TV_SHOWS, tvShows as ArrayList<DataEntity>)
            fragment.arguments = bundle
            return fragment
        }
    }
}