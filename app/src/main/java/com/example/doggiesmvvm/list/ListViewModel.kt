package com.example.doggiesmvvm.list

import android.app.Application
import androidx.lifecycle.*
import com.example.doggiesmvvm.model.Repository
import com.example.doggiesmvvm.model.db.BreedEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class ViewStatus
data class DetailView(val breedName: String, val images: List<String>) : ViewStatus()
object LoadingDetail: ViewStatus()


class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    init {
        viewModelScope.launch {
            repository.getDoggies()
        }
    }

    val doggiesList: LiveData<List<DoggyUI>>  = repository.list.map {
        it.map { breed -> convert(breed) }
    }

    var selected = MutableLiveData<DoggyUI>()

    var detail: LiveData<List<String>> = Transformations.switchMap(selected) {
        repository.getImages(it.breedName)
    }

    /**
     * Se convierte al tipo DoggyUI que tiene la info del elemento
     */
    private fun convert(d: BreedEntity): DoggyUI {
        return DoggyUI(d.breedName)
    }

    fun select(it: DoggyUI) {
        selected.value = it
    }
}