package com.example.ghibli.di

import com.example.ghibli.BuildConfig
import com.example.ghibli.data.network.ApiService
import com.example.ghibli.data.repository.FilmRepository
import com.example.ghibli.ui.navigation.DefaultNavigator
import com.example.ghibli.ui.navigation.Destination
import com.example.ghibli.ui.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: ApiService): FilmRepository =
        FilmRepository(api)

    @Provides
    @Singleton
    fun provideNavigator(): Navigator =
        DefaultNavigator(startDestination = Destination.AppGraph)
}
