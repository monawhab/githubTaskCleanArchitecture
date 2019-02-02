package com.paging.data.mappers

import com.paging.domain.common.Mapper
import com.paging.domain.entities.RepoEntity
import com.paging.presentation.ui.RepoUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoEntityUIMapper @Inject constructor() : Mapper<RepoEntity, RepoUI>() {

    override fun mapFrom(from: RepoEntity): RepoUI {
        return RepoUI(
                id = from.id,
                name = from.name,
                description = from.description,
                fullName = from.fullName
        )
    }
}
