package com.tripsero.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Places : Serializable{

    @SerializedName("icon")
    @Expose
    var icon : String? = null

    @SerializedName("name")
    @Expose
    var name : String? = null

}
