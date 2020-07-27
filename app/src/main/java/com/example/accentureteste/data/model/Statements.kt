package com.example.accentureteste.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ResponseStatements {

    @SerializedName("statementList")
    lateinit var data: List<Statements>

    data class Statements (
        var title: String,
        var desc : String,
        var date: String,
        var value:Double
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readDouble()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(desc)
            parcel.writeString(date)
            parcel.writeDouble(value)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Statements> {
            override fun createFromParcel(parcel: Parcel): Statements {
                return Statements(parcel)
            }

            override fun newArray(size: Int): Array<Statements?> {
                return arrayOfNulls(size)
            }
        }
    }
}