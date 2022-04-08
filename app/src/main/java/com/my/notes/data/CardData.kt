package com.my.notes.data

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CardData (var header: String? = null, var description: String? = null, var picture: Int = 0,  var date: Date? = null):Parcelable{

    @IgnoredOnParcel
    var id: String? = null
}