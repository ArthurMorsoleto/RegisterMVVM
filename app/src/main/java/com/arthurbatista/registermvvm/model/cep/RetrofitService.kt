package com.arthurbatista.registermvp.model.cep

import com.arthurbatista.registermvvm.model.cep.CEP
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("{cep}/json")
    fun findCEP(@Path("cep") cep: String): Call<CEP>

}