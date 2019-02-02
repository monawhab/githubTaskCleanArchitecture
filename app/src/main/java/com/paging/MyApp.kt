package com.paging

import android.app.Activity
import android.app.Application
import com.paging.presentation.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class MyApp : Application(), HasActivityInjector {
    @Inject//AndroidInjection.inject() gets a DispatchingAndroidInjector<Activity> from the Application and passes your activity to inject(Activity).
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
