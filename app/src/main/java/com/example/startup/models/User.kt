package com.example.startup.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class User(var uid: String, var username: String, var profileImageUrl: String): Parcelable {
    constructor() : this("", "", "")
}