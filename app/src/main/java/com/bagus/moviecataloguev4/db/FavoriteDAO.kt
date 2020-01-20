package com.bagus.moviecataloguev4.db

import androidx.room.*
import com.bagus.moviecataloguev4.db.table.Favorite

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE title LIKE :title")
    fun findByTitle(title: String): Favorite

    @Insert
    fun insertAll(vararg favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Update
    fun updateFavorite(vararg favorites: Favorite)
}