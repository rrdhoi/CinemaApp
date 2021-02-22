package com.rrdhoi.projectone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
        var desc : String? = "",
        var director : String? = "",
        var gendre : String? = "",
        var judul : String? = "",
        var image : String? = "",
        var rating : String? = ""
) : Parcelable