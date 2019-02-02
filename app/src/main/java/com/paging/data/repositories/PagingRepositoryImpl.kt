package com.paging.data.repositories

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.paging.data.RepoBoundaryCallback
import com.paging.domain.PagingRepository
import com.paging.domain.entities.RepoEntity
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagingRepositoryImpl @Inject constructor(private val cachedDataStore: CachedPagingDataStore,
                                               private val remoteDataStore: RemotePagingDataStore) : PagingRepository {
    override fun getAllRepos(): Observable<PagedList<RepoEntity>> {
//        return cachedDataStore.isEmpty().flatMap { empty ->
//            if (!empty) {
//                return@flatMap cachedDataStore.getAllRepos()
//            } else {
//                return@flatMap remoteDataStore.getAllRepos()
//                        .doOnNext { repos ->
//                            saveRepos(repos)
//                        }
//            }
//        }

        // Get data source factory from the local cache
        val dataSourceFactory = cachedDataStore.getAllRepos()

        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback = RepoBoundaryCallback(cachedDataStore,remoteDataStore)
        val networkErrors = boundaryCallback.networkErrors

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
                .setPageSize(DATABASE_PAGE_SIZE)//.setPrefetchDistance(DATABASE_PAGE_SIZE-2)
                .build()
        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
                .setBoundaryCallback(boundaryCallback)
                .build()

        // Get the network errors exposed by the boundary callback
        return Observable.just(data.value)
 }

    fun saveRepos(repos: List<RepoEntity>){
        return cachedDataStore.saveRepos(repos)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 15
        private const val INITIAL_LOAD_SIZE_HINT = DATABASE_PAGE_SIZE*2
    }
}