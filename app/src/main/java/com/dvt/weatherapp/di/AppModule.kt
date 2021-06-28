package com.dvt.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.dvt.weatherapp.BuildConfig
import com.dvt.weatherapp.data.api.helper.ApiHelper
import com.dvt.weatherapp.data.api.helper.IApiHelper
import com.dvt.weatherapp.data.api.service.ApiService
import com.dvt.weatherapp.data.db.AppDatabase
import com.dvt.weatherapp.data.db.dao.ForecastDao
import com.dvt.weatherapp.data.db.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    private const val TIMEOUT = 30L
    private const val KEEP_ALIVE_DURATION = 5L
    private const val DB_NAME = "WeatherAppDb"

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
            .build()
    }else{
        OkHttpClient
            .Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelper): IApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao = appDatabase.weatherDao()

    @Provides
    fun provideForecastDao(appDatabase: AppDatabase): ForecastDao = appDatabase.forecastDao()
}