package com.platdmit.forasofttest.app.screens.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.platdmit.forasofttest.databinding.FragmentAlbumsStateItemsBinding

class AlbumsLoadStateAdapter
    : LoadStateAdapter<AlbumsLoadStateAdapter.AlbumsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: AlbumsLoadStateViewHolder, loadState: LoadState) {
        holder.bindData(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): AlbumsLoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding = FragmentAlbumsStateItemsBinding.inflate(layoutInflater, parent, false)

        return AlbumsLoadStateViewHolder(dataBinding)
    }

    class AlbumsLoadStateViewHolder(
        private val viewBinding: FragmentAlbumsStateItemsBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(loadState: LoadState) {
            viewBinding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}