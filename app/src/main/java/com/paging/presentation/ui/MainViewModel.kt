
package com.paging.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.paging.data.mappers.RepoEntityUIMapper
import com.paging.domain.usecases.GetAllRepos
import com.paging.presentation.common.BaseViewModel
import com.paging.presentation.common.SingleLiveEvent
import javax.inject.Inject

/**
 * ViewModel for the [MainActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
class MainViewModel @Inject constructor(private val getRepos: GetAllRepos)  : BaseViewModel() {

    private val repoEntityUIMapper = RepoEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<RepoViewState> = MutableLiveData()

    init {
        viewState.value = RepoViewState()
    }
    fun getRepos() {
        viewState.value?.repos?.let {
            onReposReceived(it)
        } ?: addDisposable(getRepos.observable()
                .flatMap {
                    it.let {
                        repoEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { var temp = it as PagedList<RepoUI>
                            onReposReceived(temp) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
//    private val queryLiveData = MutableLiveData<String>()
//    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData) {
//        repository.loadRepos()
//    }
//
//    val repos: LiveData<PagedList<Repo>> = Transformations.switchMap(repoResult) {
//        it -> it.data
//    }
//    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) { it ->
//        it.networkErrors
//    }
//
//    fun getRepos() {
//        queryLiveData.postValue("JakeWharton")
//    }


    }
        private fun onReposReceived(repos: PagedList<RepoUI>) {
            val newViewState = viewState.value?.copy(
                    isLoading = false,
                    repos = repos)
            viewState.value = newViewState
        }
}
