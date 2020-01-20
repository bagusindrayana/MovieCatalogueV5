package com.bagus.moviecataloguev4.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null,
    var type: String? = null
) : Parcelable