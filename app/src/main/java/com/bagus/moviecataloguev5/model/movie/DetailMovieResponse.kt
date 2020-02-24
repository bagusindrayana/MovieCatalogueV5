package com.bagus.moviecataloguev5.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailMovieResponse (
    var id : Int,
    var title : String,
    var backdrop_path : String,
    var overview : String,
    var vote_average : Float
): Parcelable