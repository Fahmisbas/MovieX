package com.acsl.moviex.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acsl.moviex.data.entities.DataEntity

@Database(entities = [DataEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract val favoriteDao: FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FavoriteDatabase::class.java, "favorite_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE as FavoriteDatabase

        }
    }

}