package com.platdmit.forasofttest.app.screens.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.platdmit.forasofttest.app.base.extentions.picassoRender
import com.platdmit.forasofttest.databinding.FragmentAlbumsItemBinding
import com.platdmit.forasofttest.domain.models.Album

class AlbumsPagingDataAdapter(
    private val clickListener: (Album) -> Unit
) :
    PagingDataAdapter<Album, AlbumsPagingDataAdapter.AlbumsViewHolder>(AlbumsComparator) {

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        return holder.bindData(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = FragmentAlbumsItemBinding.inflate(layoutInflater, parent, false)
        return AlbumsViewHolder(dataBinding){
            getItem(it)?.let(clickListener)
        }
    }

    inner class AlbumsViewHolder(
        private val viewBinding: FragmentAlbumsItemBinding,
        private val onClickPosition: (Int) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root){
        fun bindData(album: Album) {
            viewBinding.run {
                albumCover.picassoRender(album.artUrl60)
                albumName.text = album.name
                albumAuthor.text = album.artistName
                itemView.setOnClickListener { onClickPosition(bindingAdapterPosition) }
            }
        }
    }

    private object AlbumsComparator : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}