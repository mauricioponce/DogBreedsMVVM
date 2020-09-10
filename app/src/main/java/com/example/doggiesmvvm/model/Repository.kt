package com.example.doggiesmvvm.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doggiesmvvm.model.db.BreedEntity
import com.example.doggiesmvvm.model.db.BreedRoomDatabase
import com.example.doggiesmvvm.model.remote.RetrofitClient
import com.example.doggiesmvvm.model.remote.pojo.BreedImagesWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class Repository (context: Context) {

    private val breedDao = BreedRoomDatabase.getDatabase(context).breedDao()

    val list = breedDao.getBreeds()

    private val images = MutableLiveData<List<String>>()

    suspend fun getDoggies(): LiveData<List<BreedEntity>> {
        val response = RetrofitClient.retrofitInstance().listaRazas()

        if(response.isSuccessful) {
            response.body()?.let {
                val processBreeds = breedFromWrapper2Entity(it)
                if(processBreeds != null) {
                    breedDao.insertBreeds(processBreeds)
                }
            }
        } else {
            Timber.d("${response.code()} - ${response.errorBody()?.string()}")
        }

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

