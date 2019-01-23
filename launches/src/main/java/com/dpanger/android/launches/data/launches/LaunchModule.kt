package com.dpanger.android.launches.data.launches

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module

class LaunchModule : Module() {

    init {
        val retrofit = Retrofit.Builder()
                        .baseUrl("https://launchlibrary.net/1.3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()

        val apiClass = LaunchApi::class.java
        bind(apiClass).toInstance(retrofit.create(apiClass))
        bind(ILaunchRepository::class.java).to(LaunchRepository::class.java)
    }

}
