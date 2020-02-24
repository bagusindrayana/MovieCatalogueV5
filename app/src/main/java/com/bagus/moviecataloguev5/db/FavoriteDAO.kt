package com.bagus.moviecataloguev5.db

import android.database.Cursor
import androidx.room.*
import com.bagus.moviecataloguev5.db.table.Favorite


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




    @Query("DELETE FROM favorites WHERE item_id = :id")
    fun deleteById(id: Int): Int

    /**
     * Select all menus.
     *
     * @return A [Cursor] of all the menus in the table.
     */
    @Query("SELECT * FROM favorites")
    fun selectAll(): Cursor?

    /**
     * Select a menu by the ID.
     *
     * @param id The row ID.
     * @return A [Cursor] of the selected menu.
     */
    @Query("SELECT * FROM favorites WHERE id = :id")
    fun selectById(id: Int): Cursor?
}