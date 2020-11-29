package com.siclo.app.core.di

import com.siclo.app.BuildConfig
import com.siclo.app.core.data.api.SicloServices
import com.siclo.app.core.data.api.SicloStagingServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideInterceptors(): Array<Interceptor> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        return arrayOf(loggingInterceptor)
    }

    @Provides
    fun provideOkHttpClient(httpInterceptors: Array<Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                httpInterceptors.forEach { addInterceptor(it) }
            }
            .build()
    }

    @Provides
    fun provideSicloStagingService(httpClient: OkHttpClient): SicloStagingServices {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.STAGING_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create(SicloStagingServices::class.java)
    }

    @Provides
    fun provideSicloService(httpClient: OkHttpClient): SicloServices {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create(SicloServices::class.java)
    }
}