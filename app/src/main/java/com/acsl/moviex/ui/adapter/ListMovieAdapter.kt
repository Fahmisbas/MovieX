package com.acsl.moviex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acsl.moviex.R
import com.acsl.moviex.data.entities.MovieEntity
import com.acsl.moviex.data.source.remote.response.MovieResponse.Companion.BASE_IMAGE_URL
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class ListMovieAdapter(private val movieList: ArrayList<MovieEntity>, private val context: Context) : RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {

    var onItemClickCallback: OnItemClickCallback? = null

    fun updateList(movies: ArrayList<MovieEntity>, isNotEmpty: (Boolean) -> Unit) {
        movieList.clear()
        movieList.addAll(movies)
        isNotEmpty.invoke(movieList.isNotEmpty())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie, context) {
            onItemClickCallback?.onItemClicked(movie)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieEntity, context: Context, itemClicked: () -> Unit) {
            with(itemView) {
                Glide.with(this.context)
                    .load(BASE_IMAGE_URL+movie.posterPath)
                    .into(iv_poster)

                setOnClickListener {
                    itemClicked.invoke()
                }

                tv_movie_name.text = movie.originalTitle
                tv_movie_desc.text = movie.overview
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(movie: MovieEntity)
    }
}