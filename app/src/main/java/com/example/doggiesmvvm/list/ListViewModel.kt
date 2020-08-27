package com.example.doggiesmvvm.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.doggiesmvvm.model.Repository
import com.example.doggiesmvvm.model.db.BreedEntity

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    val doggiesList: LiveData<List<DoggyUI>> = Transformations.map(repository.getDoggies()) {
        it.map { convert(it) }
    }

    private fun convert(d: BreedEntity) : DoggyUI {
        return DoggyUI(d.breedName)
    }
}