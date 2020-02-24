package com.bagus.moviecataloguev5.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieResponse(
    var page : String,
    var total_result : String,
    var total_pages:String,
    var results : ArrayList<Movie>
) : Parcelable