package com.example.doggiesmvvm.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.doggiesmvvm.model.db.BreedEntity
import com.example.doggiesmvvm.model.db.DatabaseManager
import com.example.doggiesmvvm.model.remote.RetrofitClient
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class Repository (context: Context, scope: CoroutineScope) {

    private val tag = "Repository"
    private val dbManager = DatabaseManager(context, scope)

    val list = dbManager.list

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
}

