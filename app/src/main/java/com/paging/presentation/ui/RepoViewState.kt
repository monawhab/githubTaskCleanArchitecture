package com.paging.presentation.ui

import androidx.paging.PagedList

data class RepoViewState(
        var isLoading: Boolean = true,
        var repos : PagedList<RepoUI>? = null
)
