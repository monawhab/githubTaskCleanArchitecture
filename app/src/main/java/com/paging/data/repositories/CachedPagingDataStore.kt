package com.paging.data.repositories

import androidx.arch.core.util.Function
import androidx.paging.DataSource
import com.paging.data.db.RepoDao
import com.paging.data.mappers.RepoDataEntityMapper
import com.paging.data.mappers.RepoEntityToDataMapper
import com.paging.domain.entities.RepoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachedPagingDataStore @Inject constructor(private val repoDao: RepoDao) {
    private val repoDataEntityMapper  = RepoDataEntityMapper()
    private val repoEntityToDataMapper  = RepoEntityToDataMapper()

    fun getAllRepos(): DataSource.Factory<Int, RepoEntity> {
        return repoDao.getRepos().map(Function { repoDataEntityMapper.mapFrom(it) })
    }
    fun saveRepos(repos: List<RepoEntity>) {
        return repoDao.insert(repos.map { repoEntityToDataMapper.mapFrom(it) })
    }
}