package com.acsl.moviex.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.request.ApiRequest
import com.acsl.moviex.util.EspressoIdlingResource
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class OrdinaryAdapter(private val dataList: ArrayList<DataEntity>) :
    RecyclerView.Adapter<OrdinaryAdapter.ViewHolder>() {

    var onItemClickCallback: OnItemClickCallback? = null

    fun updateList(data: ArrayList<DataEntity>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShows = dataList[position]
        holder.bind(tvShows) {

        }
    }

    override fun getItemCount() = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: DataEntity, itemClicked: () -> Unit) {
            with(itemView) {
                EspressoIdlingResource.increment()
                Glide.with(this.context).load(ApiRequest.BASE_IMAGE_URL + data.posterPath)
                    .into(poster)
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

}