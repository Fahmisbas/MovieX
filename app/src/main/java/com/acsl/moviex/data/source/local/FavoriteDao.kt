package com.acsl.moviex.data.source.local

import androidx.paging.DataSource
import androidx.room.*
import com.acsl.moviex.data.entities.DataEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DataEntity)

    @Delete
    fun delete(data: DataEntity)

    @Query("SELECT * FROM favorite_table WHERE is_favorite = 1")
    fun getAllFavoriteMovies(): DataSource.Factory<Int, DataEntity>

    @Query("SELECT * FROM favorite_table WHERE is_favorite = 2")
    fun getAllFavoriteTvShows(): DataSource.Factory<Int, DataEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_table WHERE originalTitle = :title)")
    fun isExist(title: String): Boolean

}