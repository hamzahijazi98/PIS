package com.uni.pis.Data.UserData

import android.os.Parcel
import android.os.Parcelable

data class friendData( var first_name: String,var last_name:String,var email:String,var phoneNumber:String ,
                       var gender:String,var city:String,var birthdate:String,
                       var image: String,var UserID: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
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
        parcel.writeString(UserID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<friendData> {
        override fun createFromParcel(parcel: Parcel): friendData {
            return friendData(parcel)
        }

        override fun newArray(size: Int): Array<friendData?> {
            return arrayOfNulls(size)
        }
    }
}