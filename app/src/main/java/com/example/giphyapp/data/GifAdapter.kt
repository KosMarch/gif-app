package com.example.giphyapp.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphyapp.Listener
import com.example.giphyapp.R
import com.example.giphyapp.databinding.ListItemBinding

class GifAdapter(
    private var gifList: List<GiphyResponse.GiphyModel>?,
    private val listener: Listener
) :
    ListAdapter<GiphyResponse, GifAdapter.GifViewHolder>(Comparator()) {

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gifImageView: ImageView = itemView.findViewById(R.id.imGif)
        private val binding = ListItemBinding.bind(itemView)

        fun loadGif(item: GiphyResponse.GiphyModel) {
            binding.tvTitle.text = item.title

            Glide.with(itemView)
                .asGif()
                .load(item.images.original.url)
                .into(gifImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return GifViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gifList?.size ?: 0
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gifModel = gifList?.get(position)

        if (gifModel != null) {
            holder.loadGif(gifModel)
            holder.itemView.setOnClickListener {
                listener.onClick(gifModel.images.original.url)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<GiphyResponse>() {
        override fun areItemsTheSame(oldItem: GiphyResponse, newItem: GiphyResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GiphyResponse, newItem: GiphyResponse): Boolean {
            return oldItem == newItem
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newGifList: List<GiphyResponse.GiphyModel>) {
        gifList = newGifList
        notifyDataSetChanged()
    }
}