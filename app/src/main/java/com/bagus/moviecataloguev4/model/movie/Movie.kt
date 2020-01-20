package com.bagus.moviecataloguev4.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    var popularity: Float? = null,
    var id: Int,
    var video: Boolean? = null,
    var vote_account: Int? = null,
    var vote_average: Float,
    var title: String,
    var release_date: String? = null,
    var original_language: String? = null,
    var original_title: String? = null,

    var backdrop_path: String? = null,
    var adult: Boolean? = null,
    var overview: String? = null,
    var poster_path: String) : Parcelable