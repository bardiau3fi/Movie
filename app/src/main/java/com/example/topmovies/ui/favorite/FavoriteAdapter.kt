package com.example.topmovies.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.topmovies.databinding.ItemHomeMoviesLastBinding
import com.example.topmovies.db.MovieEntity
import com.example.topmovies.models.home.ResponseMoviesList
import javax.inject.Inject

class FavoriteAdapter @Inject constructor() :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeMoviesLastBinding
    private var moviesList = emptyList<MovieEntity>()

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int ): FavoriteAdapter.ViewHolder {

        binding =ItemHomeMoviesLastBinding.inflate(LayoutInflater.from(parent.context), parent, false)



        return ViewHolder()
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.bindItems(moviesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = moviesList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItems(item: MovieEntity) {
            binding.apply {
                movieNameTxt.text = item.title
                movieRateTxt.text = item.rate
                movieCountryTxt.text = item.country
                movieYearTxt.text = item.year
                moviePosterImg.load(item.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                //Click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }
    private var onItemClickListener: ((MovieEntity) -> Unit)? = null
    fun setOnItemClickListener(listener: (MovieEntity) -> Unit) {
        onItemClickListener = listener
    }
    fun setData(data: List<MovieEntity>) {
        val moviesDiffUtil = MoviesDiffUtils(moviesList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(
        private val oldItem: List<MovieEntity>,
        private val newItem: List<MovieEntity>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}