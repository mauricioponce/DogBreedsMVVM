package com.example.doggiesmvvm.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.doggiesmvvm.model.db.BreedEntity
import com.example.doggiesmvvm.model.db.BreedRoomDatabase
import com.example.doggiesmvvm.model.remote.RetrofitClient
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class Repository (context: Context) {

    private val tag = "Repository"
    private val db =  BreedRoomDatabase.getDatabase(context)

    val list = db.breedDao().getBreeds()

    fun getDoggies() : LiveData<List<BreedEntity>>{

        CoroutineScope(Dispatchers.IO).launch {
            db.breedDao().deleteAll()
        }

        RetrofitClient.retrofitInstance().listaRazas().enqueue(object : Callback<BreedWrapper>  {
            override fun onFailure(call: Call<BreedWrapper>, t: Throwable) {
                Timber.d(tag, "paff y nació chocapic $t")
            }

            override fun onResponse(
                call: Call<BreedWrapper>,
                response: Response<BreedWrapper>
            ) {
                Timber.d("${response.body()}")

                val body = response.body()
                val map = body?.message?.keys?.map { BreedEntity(it) }

                CoroutineScope(Dispatchers.IO).launch {
                    db.breedDao().insertBreeds(map!!)
                }
            }
        })

        return list
    }

}