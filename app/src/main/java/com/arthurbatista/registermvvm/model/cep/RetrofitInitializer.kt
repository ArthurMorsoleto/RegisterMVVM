package com.arthurbatista.registermvp.model.cep

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer{

    val BASE_URL: String = "https://viacep.com.br/ws/"

    val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun getCEPService(): RetrofitService {
        return this.retrofit.create(RetrofitService::class.java)
    }

}