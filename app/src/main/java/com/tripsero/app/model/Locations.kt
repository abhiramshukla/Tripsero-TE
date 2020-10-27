package com.tripsero.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Locations : Serializable {

    @SerializedName("Name")
    @Expose
    var name : String? = null

    @SerializedName("icon")
    @Expose
    var icon : String? = null

    @SerializedName("About")
    @Expose
    var about : String? = null

    @SerializedName("Testimonials")
    @Expose
    var testimonials : Array<Testimonials>? = null

    @SerializedName("Tags")
    @Expose
    var tags : String? = null

    @SerializedName("Activities")
    @Expose
    var activities : Array<Activities>? = null

    @SerializedName("Places")
    @Expose
    var places : Array<Places>? = null

}
