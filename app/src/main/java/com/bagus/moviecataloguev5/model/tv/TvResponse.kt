package com.bagus.moviecataloguev5.model.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvResponse(var page : String,var total_result : String,var total_pages:String,var results : ArrayList<Tv>) :
    Parcelable