package com.project.sws.album.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.sws.album.Album
import com.project.sws.album.databinding.ItemAlbumBinding
import com.project.sws.album.formatToString
import com.project.sws.album.toFormatDecimal

class AlbumAdapter(private val fragment: Fragment) :
    ListAdapter<Album, AlbumAdapter.ViewHolder>(Differ()) {
    var onBookmarkCheckedChange: ((id: Int, isCheck: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvAlbumName.text = item.collectionName
            tvArtistName.text = item.artistName
            tvCurrency.text = item.currency
            tvPrice.text = "$${item.price.toFormatDecimal(2)}"
            tvReleaseDate.text = item.releaseDate?.formatToString("yyyy-MM-dd")
            ivCover.let {
                Glide.with(fragment).load(item.artWorkUrl).into(it)
            }
            cbBookmark.setOnCheckedChangeListener { _, b ->
                onBookmarkCheckedChange?.invoke(item.collectionId, b)
            }
            updateIsBookmark(this, item.isBookmarked)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val payload = payloads.firstOrNull() as? Differ.Payload ?: return
            updateItemView(holder.binding, payload)
        }
    }

    private fun updateItemView(binding: ItemAlbumBinding, payload: Differ.Payload) {
        payload.changes.forEach {
            when (it) {
                is Differ.FieldChange.Bookmark -> updateIsBookmark(binding, it.newValue)
            }
        }
    }

    private fun updateIsBookmark(binding: ItemAlbumBinding, newValue: Boolean) {
        binding.cbBookmark.isChecked = newValue
    }

    private class Differ : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.collectionId == newItem.collectionId
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return getChanges(oldItem, newItem).isEmpty()
        }

        override fun getChangePayload(oldItem: Album, newItem: Album): Any? {
            return Payload(getChanges(oldItem, newItem))
        }

        private fun getChanges(oldItem: Album, newItem: Album): List<FieldChange> {
            val changes = mutableListOf<FieldChange>()
            if (oldItem.isBookmarked != newItem.isBookmarked) changes.add(FieldChange.Bookmark(newItem.isBookmarked))
            return changes
        }

        data class Payload(val changes: List<FieldChange>)

        sealed class FieldChange {
            data class Bookmark(val newValue: Boolean) : FieldChange()
        }
    }

    class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)
}