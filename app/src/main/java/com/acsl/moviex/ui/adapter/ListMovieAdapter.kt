package com.acsl.moviex.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.request.ApiRequest.Companion.BASE_IMAGE_URL
import com.acsl.moviex.util.EspressoIdlingResource
import com.acsl.moviex.vo.NetworkState
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

class ListMovieAdapter :
    PagedListAdapter<DataEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val itemMovie = 1
    private val itemNetwork = 2

    var onItemClickCallback: OnItemClickCallback? = null

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        return if (viewType == itemMovie) {
            view = layoutInflater.inflate(R.layout.item_movie, parent, false)
            MovieViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            NetworkStateItemViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == itemMovie) {
            getItem(position)?.let { data ->
                (holder as MovieViewHolder).bind(data) {
                    onItemClickCallback?.onItemClicked(data)
                }
            }
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.running()
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            itemNetwork
        } else {
            itemMovie
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataEntity>() {
            override fun areItemsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: DataEntity, itemClicked: () -> Unit) {
            with(itemView) {
                EspressoIdlingResource.increment()
                Glide.with(this.context).load(BASE_IMAGE_URL + data.posterPath).into(poster)
                setOnClickListener { itemClicked.invoke() }
                tv_movie_name.text = data.originalTitle
                tv_movie_desc.text = data.overview
                EspressoIdlingResource.decrement()
            }
        }
    }

    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.running()) {
                itemView.progress_bar_item.visibility = View.VISIBLE
            } else {
                itemView.progress_bar_item.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.error()) {
                itemView.msg_error.visibility = View.VISIBLE
                itemView.msg_error.text = networkState.message
            } else if (networkState != null && networkState == NetworkState.reachedBottomList()) {
                itemView.msg_error.visibility = View.VISIBLE
                itemView.msg_error.text = networkState.message
            } else {
                itemView.msg_error.visibility = View.GONE
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {

        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataEntity)
    }


}