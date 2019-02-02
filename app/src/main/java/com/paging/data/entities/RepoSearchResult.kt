
package com.paging.data.entities

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.paging.domain.entities.RepoEntity

/**
 * RepoSearchResult from a search, which contains LiveData<List<Repo>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class RepoSearchResult(
        val data: LiveData<PagedList<RepoEntity>>,
        val networkErrors: LiveData<String>
)
