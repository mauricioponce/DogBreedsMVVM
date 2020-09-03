package com.example.doggiesmvvm.model.db

import com.example.doggiesmvvm.model.breedFromWrapper2Entity
import com.example.doggiesmvvm.model.remote.pojo.BreedWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DatabaseManager(context: Context, private val scope: CoroutineScope) {
    private val db = BreedRoomDatabase.getDatabase(context)

    val list = db.breedDao().getBreeds()

    fun saveBreeds(wrapper: BreedWrapper?) {
        val processBreeds = breedFromWrapper2Entity(wrapper)
        addBreeds(processBreeds)
    }

    private fun addBreeds(values: List<BreedEntity>?) = scope.launch {
        if (values != null) {
            db.breedDao().insertBreeds(values)
        }
    }
}