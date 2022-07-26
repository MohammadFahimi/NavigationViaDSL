package com.mfahimi.demo.navigation

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

@Serializable
data class SearchParameters(
    val searchQuery: String,
    val filters: List<String>
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(searchQuery)
        parcel.writeStringList(filters)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchParameters> {
        override fun createFromParcel(parcel: Parcel): SearchParameters {
            return SearchParameters(parcel)
        }

        override fun newArray(size: Int): Array<SearchParameters?> {
            return arrayOfNulls(size)
        }
    }
}

class SearchParametersType : NavType<SearchParameters>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: SearchParameters) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): SearchParameters {
        return bundle.getParcelable<SearchParameters>(key)!!
    }

    override fun parseValue(value: String): SearchParameters {
        return Json.decodeFromString<SearchParameters>(value)
    }
}