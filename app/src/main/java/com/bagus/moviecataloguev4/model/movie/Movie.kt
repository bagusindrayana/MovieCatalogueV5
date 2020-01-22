package com.bagus.moviecataloguev4.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    var id: Int,
    var title: String,
    var poster_path: String) : Parcelable