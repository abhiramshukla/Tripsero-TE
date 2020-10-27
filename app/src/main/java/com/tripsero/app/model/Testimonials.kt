package com.tripsero.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Testimonials : Serializable {

    @SerializedName("img")
    @Expose
    var img : String? = null

    @SerializedName("text")
    @Expose
    var text : String? = null

    @SerializedName("name")
    @Expose
    var name : String? = null

}
