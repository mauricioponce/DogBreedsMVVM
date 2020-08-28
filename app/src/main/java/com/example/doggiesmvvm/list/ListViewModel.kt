package com.example.doggiesmvvm.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.doggiesmvvm.model.Repository
import com.example.doggiesmvvm.model.db.BreedEntity

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application, viewModelScope)

    val doggiesList: LiveData<List<DoggyUI>> = Transformations.map(repository.getDoggies()) {
        it.map { breed -> convert(breed) }
    }

    /**
     * Se convierte al tipo DoggyUI que tiene la info del elemento
     */
    private fun convert(d: BreedEntity) : DoggyUI {
        return DoggyUI(d.breedName)
    }
}