package com.msr.msgallery.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.msr.msgallery.data.model.Album
import com.msr.msgallery.databinding.ListItemAlbumBinding
import com.msr.msgallery.extentions.showToast

class AlbumsRecyclerViewAdapter(val context: Context, var albumsList: MutableList<Album>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var selectedCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumViewHolder(
            ListItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = albumsList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AlbumViewHolder).setModel(albumsList[position])
        holder.binding.checkbox.setOnCheckedChangeListener { _, _ ->
            val album = albumsList[position]
            album.selected = !album.selected
            if (album.selected) {
                selectedCount++
            } else {
                selectedCount--
            }
            context.showToast("Selected Count:$selectedCount")
            notifyDataSetChanged()
        }
    }

    class AlbumViewHolder(var binding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setModel(item: Album) {
            binding.model = item
        }
    }
}