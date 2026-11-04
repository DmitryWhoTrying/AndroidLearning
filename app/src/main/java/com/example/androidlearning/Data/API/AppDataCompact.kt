package com.example.androidlearning.Data.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//{"id":"fa2e31b8-1234-4cf7-9914-108a170a1b01",
// "name":"QuickNote",
// "description":"Лёгкое приложение для заметок и напоминаний",
// "category":"Производительность",
// "iconUrl":"https://fastly.picsum.photos/id/237/200/200.jpg?hmac=zHUGikXUDyLCCmvyww1izLK3R3k8oRYBRiTizZEdyfI"}


class AppDataCompact : java.io.Serializable
{
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name : String? = null;

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("category")
    @Expose
    var category: String? = null

    @SerializedName("iconUrl")
    @Expose
    var iconUrl: String? = null
}
