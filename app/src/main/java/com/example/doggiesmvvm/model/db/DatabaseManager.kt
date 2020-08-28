package com.example.doggiesmvvm.model.db

import android.content.Context
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DatabaseManager(context: Context, private val scope: CoroutineScope) {
    private val db = BreedRoomDatabase.getDatabase(context)

    val list = db.breedDao().getBreeds()

    fun saveBreeds(wrapper: BreedWrapper?) {
        val processBreeds = processBreeds(wrapper)
        addBreeds(processBreeds)
    }

    private fun processBreeds(wrapper: BreedWrapper?): List<BreedEntity>? {
        return wrapper?.message?.keys?.map { BreedEntity(it) }
    }

    private fun addBreeds(values: List<BreedEntity>?) = scope.launch{
        if(values != null) {
            db.breedDao().insertBreeds(values)
        }
    }
}