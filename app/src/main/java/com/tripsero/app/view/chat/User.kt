package com.tripsero.app.view.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

class User(val uid: String, val username: String,val status:String ,val profileImageUrl: String): Parcelable {
    constructor() : this("", "", "","")
}