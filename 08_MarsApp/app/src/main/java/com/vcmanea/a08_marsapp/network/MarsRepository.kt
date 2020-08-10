package com.vcmanea.a08_marsapp.network

object MarsRepository {
    var client: WebService= RetrofitClient.retrofit
    suspend fun getMars()=client.getProperties()

}