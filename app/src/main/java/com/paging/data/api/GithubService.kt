
package com.paging.data.api

import com.paging.domain.entities.RepoEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "GithubService"
/**
 * Trigger a request to the Github searchRepo API with the following params:
 * @param page request page index
 * @param itemsPerPage number of repositories to be returned by the Github API per page
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the list of repos received
 * @param onError function that defines how to handle request failure
 */
//fun loadRepos(
//        service: GithubService,
//        page: Int,
//        itemsPerPage: Int,
//        onSuccess: (repos: List<Repo>) -> Unit,
//        onError: (error: String) -> Unit
//) {
//    Log.d(TAG, "page: $page, itemsPerPage: $itemsPerPage")
//
//
//    service.loadRepos(page, itemsPerPage).enqueue(
//            object : Callback<List<Repo>> {
//                override fun onFailure(call: Call<List<Repo>>?, t: Throwable) {
//                    Log.d(TAG, "fail to get data")
//                    onError(t.message ?: "unknown error")
//                }
//
//                override fun onResponse(
//                        call: Call<List<Repo>>?,
//                        response: Response<List<Repo>>
//                ) {
//                    Log.d(TAG, "got a response $response")
//                    if (response.isSuccessful) {
//                        val repos = response.body() ?: emptyList()
//
//                        if (repos.isEmpty())
//                            onError(response.errorBody()?.string() ?: "Unknown error")
//                        onSuccess(repos)
//                    } else {
//                        onError(response.errorBody()?.string() ?: "Unknown error")
//                    }
//                }
//            }
//    )
//}

/**
 * Github API communication setup via Retrofit.
 */
interface GithubService {
    /**
     * Get repos ordered by stars.
     */
    @GET("users/JakeWharton/repos")
    fun loadRepos(
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int
    ): Observable<List<RepoEntity>>

    companion object {
        const val BASE_URL = "https://api.github.com/"

//        fun create(): GithubService {
//            val logger = HttpLoggingInterceptor()
//            logger.level = Level.BASIC
//
//            val client = OkHttpClient.Builder()
//                    .addInterceptor(logger)
//                    .build()
//            return Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(client)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(GithubService::class.java)
//        }
    }
}