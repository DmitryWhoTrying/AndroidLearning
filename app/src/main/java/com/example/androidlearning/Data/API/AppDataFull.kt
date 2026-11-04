package com.example.androidlearning.Data.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


//{"id":"fa2e31b8-1234-4cf7-9914-108a170a1b01",
// "name":"QuickNote",
// "developer":"QuickSoft Inc.",
// "category":"Производительность",
// "ageRating":3,
// "size":8.2,
// "iconUrl":"https://fastly.picsum.photos/id/237/200/200.jpg?hmac=zHUGikXUDyLCCmvyww1izLK3R3k8oRYBRiTizZEdyfI",
// "screenshotUrlList":["https://fastly.picsum.photos/id/2/200/200.jpg?hmac=isSWZUpv7D1D156XcADPOCZlfCG9mmvb8OlXFdvLdK0","https://fastly.picsum.photos/id/3/200/200.jpg?hmac=N5yYUNYl5gOUcaMmTtnNNtx839TN2qaNM4SaXhQl65U","https://fastly.picsum.photos/id/4/200/200.jpg?hmac=ozRrjh7SMobdmKcOU0f5sWYszFW9Or1c90qeq7uqtuw","https://fastly.picsum.photos/id/5/200/200.jpg?hmac=oN9VtXdJYLSFssji8vCr48JaI-e5Zi4eH9GAiYBB_Ig","https://fastly.picsum.photos/id/6/200/200.jpg?hmac=g4Q9Vcu5Ohm8Rwap3b6HSIxUfIALZ6BasESHhw7VjLE"],
// "description":"QuickNote — практичное приложение для хранения"}


class AppDataFull {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name : String? = null;

    @SerializedName("developer")
    @Expose
    var developer: String? = null

    @SerializedName("category")
    @Expose
    var category: String? = null

    @SerializedName("ageRating")
    @Expose
    var ageRating: Int? = null

    @SerializedName("size")
    @Expose
    var size: Double? = null

    @SerializedName("iconUrl")
    @Expose
    var iconUrl: String? = null

    @SerializedName("screenshotUrlList")
    @Expose
    var screenshotUrlList: List<String>? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
}