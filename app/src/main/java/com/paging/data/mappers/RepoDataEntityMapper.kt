package com.paging.data.mappers

import com.paging.data.entities.Repo
import com.paging.domain.common.Mapper
import com.paging.domain.entities.RepoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoDataEntityMapper @Inject constructor() : Mapper<Repo, RepoEntity>() {

    override fun mapFrom(from: Repo): RepoEntity {
        return RepoEntity(
                id = from.id,
                name = from.name,
                description = from.description,
                fullName = from.fullName
        )
    }
}
