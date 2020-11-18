package com.acsl.moviex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.DataEntity
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.BASE_IMAGE_URL
import com.acsl.moviex.util.EspressoIdlingResource
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class ListMovieAdapter(private val dataList: ArrayList<DataEntity>, private val context: Context) :
    RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {

    var onItemClickCallback: OnItemClickCallback? = null

    fun updateList(data: ArrayList<DataEntity>, isNotEmpty: (Boolean) -> Unit) {
        dataList.clear()
        dataList.addAll(data)
        isNotEmpty.invoke(dataList.isNotEmpty())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = dataList[position]
        holder.bind(movie, context) {
            onItemClickCallback?.onItemClicked(movie)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: DataEntity, context: Context, itemClicked: () -> Unit) {
            with(itemView) {
                EspressoIdlingResource.increment()

                Glide.with(this.context)
                    .load(BASE_IMAGE_URL + data.posterPath)
                    .into(iv_poster)

                setOnClickListener {
                    itemClicked.invoke()
                }

                tv_movie_name.text = data.originalTitle
                tv_movie_desc.text = data.overview

                EspressoIdlingResource.decrement()
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataEntity)
    }
}