package com.paging.domain

import androidx.paging.PagedList
import com.paging.domain.entities.RepoEntity
import io.reactivex.Observable

interface PagingRepository {
    fun getAllRepos(): Observable<PagedList<RepoEntity>>
}
