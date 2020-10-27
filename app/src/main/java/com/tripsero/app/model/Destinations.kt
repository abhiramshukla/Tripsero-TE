package com.tripsero.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Destinations {

    @Expose
    @SerializedName("Locations")
    var locations : Array<Locations>? = null
}