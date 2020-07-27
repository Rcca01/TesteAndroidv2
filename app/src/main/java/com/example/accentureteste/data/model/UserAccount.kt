package com.example.accentureteste.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ResponseLogin {

    @SerializedName("userAccount")
    var data: UserAccount = UserAccount()

    data class UserAccount(
        val userId: Int = 0,
        val name: String = "",
        val bankAccount: String = "",
        val agency: String = "",
        val balance: Double = 0.0
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readDouble()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(userId)
            parcel.writeString(name)
            parcel.writeString(bankAccount)
            parcel.writeString(agency)
            parcel.writeDouble(balance)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<UserAccount> {
            override fun createFromParcel(parcel: Parcel): UserAccount {
                return UserAccount(parcel)
            }

            override fun newArray(size: Int): Array<UserAccount?> {
                return arrayOfNulls(size)
            }
        }
    }
}