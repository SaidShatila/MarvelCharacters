package said.shatila.marvelcharacters.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import said.shatila.marvelcharacters.data.remote.AppApi
import said.shatila.marvelcharacters.data.remote.Constants.APIKEY
import said.shatila.marvelcharacters.data.remote.Constants.BASE_URL
import said.shatila.marvelcharacters.data.remote.Constants.HASH
import said.shatila.marvelcharacters.data.remote.Constants.PRIVATE_KEY
import said.shatila.marvelcharacters.data.remote.Constants.PUBLIC_KEY
import said.shatila.marvelcharacters.data.remote.Constants.TS
import said.shatila.marvelcharacters.util.md5
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder().addInterceptor { chain ->
            val currentTime = System.currentTimeMillis().toString()
            val newUrl =
                chain.request().url.newBuilder().addQueryParameter(TS, currentTime)
                    .addQueryParameter(APIKEY, PUBLIC_KEY).addQueryParameter(
                        HASH, (currentTime + PRIVATE_KEY + PUBLIC_KEY).md5()
                    ).build()
            val newRequest = chain.request().newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }.addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): AppApi =
        retrofit.create(AppApi::class.java)

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}