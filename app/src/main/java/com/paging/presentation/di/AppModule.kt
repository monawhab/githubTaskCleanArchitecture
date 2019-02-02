package com.paging.presentation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.paging.MyApp
import com.paging.data.api.GithubService
import com.paging.data.db.RepoDao
import com.paging.data.db.RepoDatabase
import com.paging.data.repositories.CachedPagingDataStore
import com.paging.data.repositories.PagingRepositoryImpl
import com.paging.data.repositories.RemotePagingDataStore
import com.paging.domain.PagingRepository
import com.paging.domain.usecases.GetAllRepos
import com.paging.presentation.common.ASyncTransformer
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    fun provideContext(application: MyApp): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        return Retrofit.Builder()
                .baseUrl(GithubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//LiveDataCallAdapterFactory()
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): RepoDatabase {
        return Room
                .databaseBuilder(app, RepoDatabase::class.java, RepoDatabase.DB_NAME)
                .fallbackToDestructiveMigration().allowMainThreadQueries()
                .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: RepoDatabase): RepoDao {
        return db.reposDao()
    }

    @Provides
    @Singleton
    fun providePagingRepository(api: GithubService,repoDao: RepoDao): PagingRepository {

        val cachedWeatherDataStore = CachedPagingDataStore(repoDao)
        val remoteWeatherDataStore = RemotePagingDataStore(api)
        return PagingRepositoryImpl(cachedWeatherDataStore, remoteWeatherDataStore)
    }

    @Singleton
    @Provides
    fun provideGetAllRepos(pagingRepository: PagingRepository): GetAllRepos {
        return GetAllRepos(ASyncTransformer(), pagingRepository)
    }
}
