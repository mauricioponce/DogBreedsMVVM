package com.example.doggiesmvvm.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class BreedEntity(@ColumnInfo(name = "breed_name") val breedName: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

