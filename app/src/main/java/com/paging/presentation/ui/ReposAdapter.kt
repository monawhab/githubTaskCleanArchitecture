
package com.paging.presentation.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the list of repositories.
 */
class ReposAdapter : PagedListAdapter<RepoUI, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){//progress
            return ProgressViewHolder.create(parent)
        }
        else{
            return RepoViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            if (getItemViewType(position) == 1)
                (holder as ProgressViewHolder).bind(isLoading)
            else
                (holder as RepoViewHolder).bind(repoItem)
        }
    }

    companion object {
        private var isLoading = true

        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RepoUI>() {
            override fun areItemsTheSame(oldItem: RepoUI, newItem: RepoUI): Boolean =
                    oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: RepoUI, newItem: RepoUI): Boolean =
                    oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            1;
        } else {
            0;
        }
    }


    fun setIsLoading(loading : Boolean) {
        isLoading = loading
        notifyItemChanged(super.getItemCount())
    }
}
