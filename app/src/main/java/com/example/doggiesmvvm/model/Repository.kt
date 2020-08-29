package com.example.doggiesmvvm.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doggiesmvvm.model.db.BreedEntity
import com.example.doggiesmvvm.model.db.DatabaseManager
import com.example.doggiesmvvm.model.remote.RetrofitClient
import com.example.doggiesmvvm.model.remote.pojo.BreedImagesWrapper
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class Repository (context: Context, scope: CoroutineScope) {

    private val tag = "Repository"
    private val dbManager = DatabaseManager(context, scope)

    private val list = dbManager.list

    private val images = MutableLiveData<List<String>>()

    /**
     * Alternativa 1: Desde la base de datos
     *
     * Desde la base de datos obtenemos la lista de las razas.
     * Se expone un livedata
     */
    fun getDoggies() : LiveData<List<BreedEntity>>{
        RetrofitClient.retrofitInstance().listaRazas().enqueue(object : Callback<BreedWrapper>  {
            override fun onFailure(call: Call<BreedWrapper>, t: Throwable) {
                Timber.d(tag, "paff y nació chocapic $t")
            }

            override fun onResponse(
                call: Call<BreedWrapper>,
                response: Response<BreedWrapper>
            ) {
                Timber.d("${response.body()}")
                dbManager.saveBreeds(response.body())
            }
        })
        return list
    }

    /**
     * Alternativa 2: Se carga la lista de imagenes desde la API todas las veces
     */
    fun getImages(breed: String): LiveData<List<String>> {
        RetrofitClient.retrofitInstance().listaImagenes(breed).enqueue(object: Callback<BreedImagesWrapper> {
            override fun onResponse(call: Call<BreedImagesWrapper>, response: Response<BreedImagesWrapper>) {
                images.postValue(response.body()?.message)
            }

            override fun onFailure(call: Call<BreedImagesWrapper>, t: Throwable) {
                Timber.d("error con las imagenes $t")
            }

        })
        return images
    }
}

