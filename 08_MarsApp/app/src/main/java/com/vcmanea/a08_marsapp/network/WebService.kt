package com.vcmanea.a08_marsapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL="https://mars.udacity.com"

//Use Moshi builder to build a retrofit object using a Moshi converter  with our Moshi object pointing to the desired URL
private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Create a Retrofit interface. in this example, it will be
interface WebService{
    @GET("/realestate")
    suspend fun getProperties(): List<MarsProperty>
}

class RetrofitClient{
    companion object {
        val retrofit=Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build().create(WebService::class.java)
    }
}







