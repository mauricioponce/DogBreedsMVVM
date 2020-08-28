package com.example.doggiesmvvm.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [BreedEntity::class], version = 1, exportSchema = false)
abstract class BreedRoomDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BreedRoomDatabase? = null

        fun getDatabase(context: Context): BreedRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BreedRoomDatabase::class.java,
                    "breed_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
