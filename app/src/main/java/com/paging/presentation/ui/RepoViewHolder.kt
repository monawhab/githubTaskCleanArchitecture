
package com.paging.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paging.R
import com.paging.data.entities.Repo

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.tvRepoName)
    private val description: TextView = view.findViewById(R.id.tvRepoDesc)

    private var repo: RepoUI? = null

    fun bind(repo: RepoUI?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: RepoUI) {
        this.repo = repo
        name.text = repo.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.repo_view_item, parent, false)
                return RepoViewHolder(view)
        }
    }
}
