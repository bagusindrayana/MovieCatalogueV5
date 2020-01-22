package com.bagus.moviecataloguev4.model.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailTv (
    var id : Int,
    var name : String,
    var backdrop_path : String,
    var overview : String,
    var vote_average : Float,
    var first_air_date : String,
    var status : String
) : Parcelable