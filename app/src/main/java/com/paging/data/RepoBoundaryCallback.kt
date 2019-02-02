package com.paging.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.paging.data.repositories.CachedPagingDataStore
import com.paging.data.repositories.RemotePagingDataStore
import com.paging.domain.entities.RepoEntity

/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class RepoBoundaryCallback(
        private val cachedDataStore: CachedPagingDataStore,
        private val remoteDataStore: RemotePagingDataStore
) : PagedList.BoundaryCallback<RepoEntity>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("RepoBoundaryCallback", "onZeroItemsLoaded")
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: RepoEntity) {
        Log.d("RepoBoundaryCallback", "onItemAtEndLoaded")
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        remoteDataStore.getAllRepos(NETWORK_PAGE_SIZE, lastRequestedPage).doOnNext { repos ->
            cachedDataStore.saveRepos(repos)
            lastRequestedPage++
            isRequestInProgress = false

        }
//        loadRepos(service, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
//            cache.insert(repos) {
//                lastRequestedPage++
//                isRequestInProgress = false
//            }
//        }, { error ->
//            _networkErrors.postValue(error)
//            isRequestInProgress = false
//        })
    }
}