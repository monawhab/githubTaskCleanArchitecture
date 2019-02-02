package com.paging.data.repositories

import com.paging.data.api.GithubService
import com.paging.data.mappers.RepoEntityUIMapper
import com.paging.domain.entities.RepoEntity
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemotePagingDataStore @Inject constructor(private val api: GithubService) {

    private val repoDataEntityMapper = RepoEntityUIMapper()
    fun getAllRepos(itemsNumber: Int, pageNumber: Int): Observable<List<RepoEntity>> {
        return api.loadRepos(pageNumber, itemsNumber)
    }
}