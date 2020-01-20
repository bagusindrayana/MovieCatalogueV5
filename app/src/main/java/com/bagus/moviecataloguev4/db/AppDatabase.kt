package com.bagus.moviecataloguev4.db

import android.content.Context
import androidx.room.*
import com.bagus.moviecataloguev4.db.table.Favorite

@Database(entities = arrayOf(Favorite::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDAO


    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "todo-list.db")
            .build()
    }
}