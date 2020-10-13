package com.platdmit.forasofttest.app.screens.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.platdmit.forasofttest.R
import com.platdmit.forasofttest.app.base.extentions.picassoRender
import com.platdmit.forasofttest.app.utilities.enums.BundleTypes
import com.platdmit.forasofttest.databinding.FragmentAlbumsItemBinding
import com.platdmit.forasofttest.domain.models.Album

class AlbumsPagingDataAdapter :
    PagingDataAdapter<Album, AlbumsPagingDataAdapter.AlbumsViewHolder>(AlbumsComparator) {

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        return holder.bindData(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = FragmentAlbumsItemBinding.inflate(layoutInflater, parent, false)
        return AlbumsViewHolder(dataBinding)
    }

    inner class AlbumsViewHolder(
        private val viewBinding: FragmentAlbumsItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(album: Album) {
            viewBinding.run {
                albumCover.picassoRender(album.artUrl60)
                albumName.text = album.name
                albumAuthor.text = album.artistName
                root.setOnClickListener {
                    it.findNavController().navigate(
                        R.id.action_albumsFragment_to_albumFragment, bundleOf(
                            BundleTypes.ALBUM_DETAIL.name to album
                        )
                    )
                }
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