package com.bagus.moviecataloguev5.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    var id: Int,
    var title: String,
    var poster_path: String,
    var vote_average : String? = null,
    var release_date : String? = null) : Parcelable