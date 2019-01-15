package io.mercury.android.movies.data.movies

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.config.Module

class MovieModule : Module() {

    init {
        val retrofit = Retrofit.Builder()
                        .baseUrl("https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/6374d9bc5e30774d00ba961ed49d7c0f2ad50092/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()

        val apiClass = GithubApi::class.java
        bind(apiClass).toInstance(retrofit.create(apiClass))
        bind(IMovieRepository::class.java).to(MovieRepository::class.java)
    }

}
