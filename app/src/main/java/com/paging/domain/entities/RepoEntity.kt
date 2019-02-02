package com.paging.domain.entities

data class RepoEntity(
        var id: Long = 0,
        var name: String,
        var fullName: String,
        var description: String?
)