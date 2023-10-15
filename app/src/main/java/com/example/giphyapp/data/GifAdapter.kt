package com.example.giphyapp.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphyapp.R
import com.example.giphyapp.databinding.ListItemBinding

class GifAdapter(private var gifList: List<GiphyModel>?, val listener: Listener?) :
    RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return GifViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return gifList?.size ?: 0
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gifMogel = gifList?.get(position)

        if (gifMogel != null) {
            holder.loadGif(gifMogel)
            holder.itemView.setOnClickListener {
                listener?.onClick(gifMogel)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newGifList: List<GiphyModel>) {
        gifList = newGifList
        notifyDataSetChanged()
    }

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gifImageView: ImageView = itemView.findViewById(R.id.imGif)
        val binding = ListItemBinding.bind(itemView)

        fun loadGif(item: GiphyModel) = with(binding) {
            tvTitle.text = item.title
            Glide.with(itemView)
                .asGif()
                .load(item.url)
                .into(gifImageView)
        }
    }

    interface Listener {
        fun onClick(item: GiphyModel)
    }
}