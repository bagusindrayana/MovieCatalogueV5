package com.bagus.moviecataloguev4.model.tv

import android.os.Parcelable
import com.bagus.moviecataloguev4.model.tv.TvGenres
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvGenresResponse(var genres : ArrayList<TvGenres>): Parcelable