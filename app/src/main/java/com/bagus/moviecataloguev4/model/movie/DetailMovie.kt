package com.bagus.moviecataloguev4.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailMovie (
    var id : Int,
    var title : String,
    var backdrop_path : String,
    var overview : String,
    var vote_average : Float,
    var release_date : String,
    var status : String
) : Parcelable