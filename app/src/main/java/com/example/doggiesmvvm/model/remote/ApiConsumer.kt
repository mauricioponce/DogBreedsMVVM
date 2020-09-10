package com.example.doggiesmvvm.model.remote

import com.example.doggiesmvvm.model.remote.pojo.BreedImagesWrapper
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DoggiesAPI {

    @GET("breeds/list/all")
    suspend fun listaRazas(): Response<BreedWrapper>

    @GET("breed/{breed}/images")
    fun listaImagenes(@Path("breed") breed: String?): Call<BreedImagesWrapper>

    @GET("breed/{breed}/{subBreed}/images")
    fun listaImagenesSubraza(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String?
    ): Call<BreedWrapper?>?
}

class RetrofitClient {
    companion object {

        private const val BASE_URL = "https://dog.ceo/api/"

        fun retrofitInstance(): DoggiesAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DoggiesAPI::class.java)
        }
    }
}