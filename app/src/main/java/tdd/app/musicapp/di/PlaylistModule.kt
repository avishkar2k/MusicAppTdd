package tdd.app.musicapp.di

import androidx.test.espresso.IdlingResource
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tdd.app.musicapp.apiservices.PlaylistApi


val interceptor: HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val okhttpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build();
val idlingResource:IdlingResource = OkHttp3IdlingResource.create("okhttp", okhttpClient)

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playlistApi(retrofit: Retrofit): PlaylistApi = retrofit.create(PlaylistApi::class.java)

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:2999/")
        .client(okhttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}