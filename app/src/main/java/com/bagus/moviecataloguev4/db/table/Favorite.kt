package com.bagus.moviecataloguev4.db.table

import androidx.room.*
import java.util.*


@Entity(tableName = "favorites")
class Favorite (

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "poster")
    var poster: String? = null,
    @ColumnInfo(name = "item_id")
    var item_id: Int? = null,
    @ColumnInfo(name = "type")
    var type: String? = null



)