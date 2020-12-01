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
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class ListAdapter :
    PagedListAdapter<DataEntity, ListAdapter.ViewHolder>(DIFF_CALLBACK) {

    var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(data) {
                onItemClickCallback?.onItemClicked(data)
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    interface OnItemClickCallback {
        fun onItemClicked(data: DataEntity)
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
}