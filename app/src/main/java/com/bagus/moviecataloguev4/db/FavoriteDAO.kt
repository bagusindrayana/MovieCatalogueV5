package com.bagus.moviecataloguev4.db

import androidx.room.*
import com.bagus.moviecataloguev4.db.table.Favorite

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorites where type = 'MOVIE'")
    fun getMovie(): List<Favorite>

    @Query("SELECT * FROM favorites where type = 'TV'")
    fun getTv(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE title LIKE :title")
    fun findByTitle(title: String): Favorite

    @Query("SELECT * FROM favorites WHERE item_id = :id and type = :type")
    fun findByItemId(id: Int,type : String? = null): Favorite

    @Insert
    fun insertAll(vararg favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Update
    fun updateFavorite(vararg favorites: Favorite)
}