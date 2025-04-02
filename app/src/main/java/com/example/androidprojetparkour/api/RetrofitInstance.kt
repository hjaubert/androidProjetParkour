package com.example.androidprojetparkour.api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val baseUrl = "http://92.222.217.100/api/"

    private val token = "Mj1MPHiuLCJd0mq4VMKz1vA2y8cPtUtS0aPzMgS49d4bd5QVSJW9pOd5fjEr7L2a"

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(token))
        .build()

    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val parkourApi : ParkourApi = getInstance().create(ParkourApi::class.java)

}