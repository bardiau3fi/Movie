package com.example.topmovies.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.topmovies.databinding.ItemHomeGenreListBinding
import com.example.topmovies.models.home.ResponseGenresList
import javax.inject.Inject

class GenresAdapter @Inject constructor() : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeGenreListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresAdapter.ViewHolder {
        binding =
            ItemHomeGenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: GenresAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(item: ResponseGenresList.ResponseGenresListItem) {
            binding.apply {
                nameTxt.text = item.name
            }
        }
    }

    private val differCallBack =
        object : DiffUtil.ItemCallback<ResponseGenresList.ResponseGenresListItem>() {
            override fun areItemsTheSame(
                oldItem: ResponseGenresList.ResponseGenresListItem,
                newItem: ResponseGenresList.ResponseGenresListItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponseGenresList.ResponseGenresListItem,
                newItem: ResponseGenresList.ResponseGenresListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    val differ = AsyncListDiffer(this, differCallBack)
}