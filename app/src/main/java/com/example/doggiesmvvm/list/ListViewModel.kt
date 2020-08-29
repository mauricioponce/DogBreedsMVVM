package com.example.doggiesmvvm.list

import android.app.Application
import androidx.lifecycle.*
import com.example.doggiesmvvm.model.Repository
import com.example.doggiesmvvm.model.db.BreedEntity

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application, viewModelScope)

    var selected = MutableLiveData<DoggyUI>()

    var detail: LiveData<List<String>> = Transformations.switchMap(selected) {
        repository.getImages(it.breedName)
    }
    val doggiesList: LiveData<List<DoggyUI>> = Transformations.map(repository.getDoggies()) {
        it.map { breed -> convert(breed) }
    }

    /**
     * Se convierte al tipo DoggyUI que tiene la info del elemento
     */
    private fun convert(d: BreedEntity): DoggyUI {
        return DoggyUI(d.breedName)
    }

    fun select(it: DoggyUI) {
        selected.postValue(it)

    }
}