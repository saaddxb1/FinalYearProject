package com.example.finalyearproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class User(var name: String? = null,
                var email: String? = null,
                var UniqueID: String? = null,
                var token: String? = null
//                var img: Int? = null,

                )
//          : Parcelable {
//    constructor() : this("", "", "",)




