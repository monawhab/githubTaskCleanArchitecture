package com.paging.domain.usecases

import androidx.paging.PagedList
import com.paging.domain.PagingRepository
import com.paging.domain.common.Transformer
import com.paging.domain.entities.RepoEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllRepos @Inject constructor(transformer: Transformer<PagedList<RepoEntity>>,
                                      private val pagingRepository: PagingRepository) : UseCase<PagedList<RepoEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<PagedList<RepoEntity>> {
        return pagingRepository.getAllRepos()
    }

}