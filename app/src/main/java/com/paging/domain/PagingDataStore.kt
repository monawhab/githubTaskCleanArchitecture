package com.paging.domain

import androidx.paging.DataSource
import com.paging.domain.entities.RepoEntity

interface PagingDataStore {
    fun getAllRepos(): DataSource.Factory<Int, RepoEntity>
}