package com.bagus.moviecataloguev5.model.search

import android.os.Parcelable
import com.bagus.moviecataloguev5.model.tv.Tv
import kotlinx.android.parcel.Parcelize

@Parcelize
class SearchTvResponse(
    var page : String,
    var total_result : String,
    var total_pages:String,
    var results : ArrayList<Tv>
) : Parcelable