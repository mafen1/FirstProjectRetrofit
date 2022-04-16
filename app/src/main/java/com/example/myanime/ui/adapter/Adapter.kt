package com.example.myanime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myanime.data.models.DataItem
import com.example.myanime.databinding.AnimeItemBinding

class Adapter :  ListAdapter<DataItem, Adapter.ViewHolder>(ItemComparator()) {

    var currentPosition: ((position: Int) -> Unit)? = null

    inner class ViewHolder(
        private val binding: AnimeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataItem: MutableList<DataItem>){
            val dataItem = dataItem[absoluteAdapterPosition]
            binding.tvAnimeTitle.text = dataItem.animeName

            Glide.with(binding.root)
                .load("${dataItem.animeImg}")
                .into(binding.cardImage)

            binding.btnDelete.setOnClickListener {
                currentPosition?.invoke(absoluteAdapterPosition)
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AnimeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList)
    }
}