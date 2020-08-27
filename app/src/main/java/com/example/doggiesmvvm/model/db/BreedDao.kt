package com.example.doggiesmvvm.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BreedDao {

    @Query("SELECT * from breed ORDER BY breed_name ASC")
    fun getBreeds(): LiveData<List<BreedEntity>>

    @Insert
    suspend fun insertBreeds(breed: List<BreedEntity>)

    @Query("DELETE FROM breed")
    suspend fun deleteAll()
}