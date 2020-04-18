package com.uni.pis.Data.UserData

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
class UserDataGoogle(var first_name: String ="test", var last_name: String ="test", var email: String ="", var phoneNumber: String ="", var gender: String =""
               , var city: String ="test", var birthdate: String ="", var image: String ="") :Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(gender)
        parcel.writeString(city)
        parcel.writeString(birthdate)
        parcel.writeString(image)

    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<UserDataGoogle> {
        override fun createFromParcel(parcel: Parcel): UserDataGoogle {
            return UserDataGoogle(parcel)
        }

        override fun newArray(size: Int): Array<UserDataGoogle?> {
            return arrayOfNulls(size)
        }
    }
}